package com.eu.mibeez.command.inbound.builder;

import com.eu.mibeez.comm.repository.HubPackageRepository;
import com.eu.mibeez.command.inbound.builder.callbaks.EventCallback;
import com.eu.mibeez.command.inbound.builder.json.MibeezData;
import com.eu.mibeez.command.inbound.builder.message.MessageStatus;
import com.eu.mibeez.command.inbound.builder.type.TamperState;
import com.eu.mibeez.command.inbound.builder.type.TempType;
import com.eu.mibeez.config.AzureProperties;
import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.IotHubClientProtocol;
import com.microsoft.azure.iothub.Message;
import org.slf4j.LoggerFactory;

import static com.eu.mibeez.command.inbound.builder.type.TamperState.*;

public abstract class HubInboundCommandBuilder implements Runnable{

    protected MibeezData mBeezData = new MibeezData();
    protected static IotHubClientProtocol protocol = IotHubClientProtocol.AMQPS;
    private byte[] batteryValue;
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(AsyncTiltInboundCommandBuilder.class);

    protected void buildInboundMessage(byte[] hubPackage) {
        mBeezData.hubSn = AzureProperties.getProperties().getHubSn();
        mBeezData.lanAddress = extractLanAddress(hubPackage); //"0001";
        mBeezData.wanAddress = AzureProperties.getProperties().getWanAddress();
        mBeezData.externalTemp = extractTempValue(TempType.EXTERNAL, hubPackage); //"29.31";
        mBeezData.broodTemp = extractTempValue(TempType.BROOD, hubPackage); // "28.75";
        mBeezData.internalTemp = extractTempValue(TempType.INTERNAL, hubPackage); //"28.31";
        mBeezData.tilt = extractTiltValue(hubPackage); //"X.000.00.Y.000.00.Z.090.00";
        mBeezData.humidity = extractHumidityValue(hubPackage); //"75%RH";
        mBeezData.batteryValue = extractBatteryValue(hubPackage); //"7/10";
        mBeezData.tamper = extractTamperAction(hubPackage);
        mBeezData.latitude = 37.96774;
        mBeezData.longitude = 23.73616;
    }

    private TamperState extractTamperAction(byte[] hubPackage) {
        if(hubPackage[HubPackageRepository.getInstance().getTamperActionOffset()] - 0x30 == 0)
            return TamperState.NORMAL;
        else
            return TamperState.ALARM;
    }

    private String extractLanAddress(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < HubPackageRepository.getInstance().getLanAddressShellLen(); i++)
        {
            sb.append((hubPackage[HubPackageRepository.getInstance().getLanAddressShellOffset() + i]-0x30));
        }

        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("LanAddress:%s", sb.toString()));
        return sb.toString();
    }

    private String extractTiltValue(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("X.");
        switch ((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset()] - 0x30)){
            case 0: {
                sb.append("+");
            }break;
            case 1: {
                sb.append("-");
            }break;
            }

        for (int i = 0; i < HubPackageRepository.getInstance().getTiltDataLen(); i++)
        {
            sb.append((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset() + i + 1] - 0x30));
        }

        sb.append("Y.");
        switch ((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset() +
                                                    1*HubPackageRepository.getInstance().getTiltDataLen() + 1] - 0x30)){
            case 0: {
                sb.append("+");
            }break;
            case 1: {
                sb.append("-");
            }break;
        }
        //Todo: Check for Errors in Y
        for (int i = 0; i < HubPackageRepository.getInstance().getTiltDataLen(); i++)
        {
            sb.append((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset() + i +
                                                                    2*HubPackageRepository.getInstance().getTiltDataLen() + 1] - 0x30));
        }
        sb.append("Z.");
        switch ((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset() +
                                                                    3*HubPackageRepository.getInstance().getTiltDataLen()] - 0x30)){
            case 0: {
                sb.append("+");
            }break;
            case 1: {
                sb.append("-");
            }break;
        }

        for (int i = 0; i < HubPackageRepository.getInstance().getTiltDataLen(); i++)
        {
            sb.append((hubPackage[HubPackageRepository.getInstance().getTiltValueOffset() + i +
                    3*HubPackageRepository.getInstance().getTiltDataLen() + 1] - 0x30));
        }

        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("Tilt Value:%s", sb.toString()));
        //return sb.toString();
        if(sb.toString().contains("90"))
            return "true";
        else
            return "false";
    }

    private String extractTempValue(TempType type, byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        switch (type){
            case EXTERNAL: {
                for (int i = 0; i < HubPackageRepository.getInstance().getExternalTempValueLen(); i++) {
                    sb.append(hubPackage[HubPackageRepository.getInstance().getExternalTempValueOffset() + i] - 0x30);
                    if ( i == 1 )sb.append(".");
                }
            }break;
            case BROOD: {
                for (int i = 0; i < HubPackageRepository.getInstance().getBroodTempValueLen(); i++) {
                    sb.append(hubPackage[HubPackageRepository.getInstance().getBroodTempValueOffset() + i] - 0x30);
                    if ( i == 1 )sb.append(".");
                }
            }break;
            case INTERNAL: {
                for (int i = 0; i < HubPackageRepository.getInstance().getInternalTempValueLen(); i++) {
                    sb.append(hubPackage[HubPackageRepository.getInstance().getInternalTempValueOffset() + i] - 0x30);
                    if ( i == 1 )sb.append(".");
                }
            }break;
        }
        sb.append("°C");
        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("Temp Value for %s :%s", type.toString(), sb.toString()));
        return sb.toString();
    }

    private String extractBatteryValue(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < HubPackageRepository.getInstance().getBatteryValueLen(); i++)
        {
            sb.append(hubPackage[HubPackageRepository.getInstance().getBatteryValueOffset() + i] - 0x30);
        }
        sb.append("/10");
        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("Battery Value:%s", sb.toString()));
        return sb.toString();
    }

    private String extractHumidityValue(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < HubPackageRepository.getInstance().getHumidityValueLen(); i++)
        {
            sb.append(hubPackage[HubPackageRepository.getInstance().getHumidityValueOffset() + i] - 0x30);
        }
        sb.append("%RH");
        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("Humidity Value:%s", sb.toString()));
        return sb.toString();
    }

    protected void buildAndSendAsync(byte[] hubPackage) {
        buildInboundMessage(hubPackage);
        Thread t0 = new Thread(this);
        t0.start();
    }

    protected abstract void buildOutMessage(MessageStatus status, String message);

    @Override
    public void run() {
        try {
            DeviceClient client;
            client = new DeviceClient(AzureProperties.getProperties().getIotHub(), protocol);
            client.open();

            String msgStr = mBeezData.serialize();
            Message msg = new Message(msgStr);
            System.out.println(msgStr);

            Object lockobj = new Object();
            EventCallback callback = new EventCallback();
            client.sendEventAsync(msg, callback, lockobj);

            buildOutMessage(MessageStatus.MESSAGE_DEBUG, msgStr);

            synchronized (lockobj) {
                lockobj.wait();
            }
            client.close();
        } catch (Exception e) {
            buildOutMessage(MessageStatus.MESSAGE_ERROR, e.getMessage());
        }
    }
}
