package com.eu.mibeez.command.inbound.builder;


import com.eu.mibeez.comm.repository.HubPackageRepository;
import com.eu.mibeez.command.inbound.builder.callbaks.EventCallback;
import com.eu.mibeez.command.inbound.builder.json.MibeezData;
import com.eu.mibeez.command.inbound.builder.json.TamperState;
import com.eu.mibeez.command.inbound.builder.message.MessageStatus;
import com.eu.mibeez.command.inbound.builder.type.TempType;
import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.IotHubClientProtocol;
import com.microsoft.azure.iothub.Message;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class HubInboundCommandBuilder implements Runnable{

    @Value("${azureIoTHubConnStr}")
    protected static String azureIoTHubConnStr;

    @Value("${hubSn}")
    protected static String hubSn;

    @Value("${wanAddress}")
    protected static String wanAddress;

    protected MibeezData mBeezData;
    protected static IotHubClientProtocol protocol = IotHubClientProtocol.AMQPS;

    private byte[] batteryValue;
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(AsyncTiltInboundCommandBuilder.class);


    protected void buildInboundMessage(byte[] hubPackage) {
        mBeezData.hubSn = hubSn;
        mBeezData.lanAddress = extractLanAddress(hubPackage); //"0001";
        mBeezData.wanAddress = wanAddress;
        mBeezData.externalTemp = extractTempValue(TempType.EXTERNAL, hubPackage); //"29.31";
        mBeezData.broodTemp = extractTempValue(TempType.BROOD, hubPackage); // "28.75";
        mBeezData.internalTemp = extractTempValue(TempType.INTERNAL, hubPackage); //"28.31";
        mBeezData.tilt = extractTiltValue(hubPackage); //"X.000.00.Y.000.00.Z.090.00";
        mBeezData.humidity = extractHumidityValue(hubPackage); //"75%RH";
        mBeezData.batteryValue = extractBatteryValue(hubPackage); //"7/10";
        mBeezData.tamper = TamperState.NORMAL;
        mBeezData.latitude = 37.96774;
        mBeezData.longitude = 23.73616;
    }

    private String extractLanAddress(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < HubPackageRepository.getInstance().getLanAddressShellLen(); i++)
        {
            sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getLanAddressShellOffset() + i]).getBytes());
        }

        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("LanAddress:%s", sb.toString()));
        return sb.toString();
    }

    private String extractTiltValue(byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("X.");
        sb.append("0");
        sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getTiltValueLen()]).getBytes());
        sb.append("Y.");
        sb.append("0");
        sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getTiltValueLen() + 1]).getBytes());
        sb.append("Z.");
        sb.append("0");
        sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getTiltValueLen() + 2]).getBytes());

        buildOutMessage(MessageStatus.MESSAGE_DEBUG, String.format("Tilt Value:%s", sb.toString()));
        return sb.toString();
    }

    private String extractTempValue(TempType type, byte[] hubPackage)
    {
        StringBuilder sb = new StringBuilder();

        switch (type){
            case EXTERNAL: {
                for (int i = 0; i < HubPackageRepository.getInstance().getExternalTempValueLen(); i++) {
                    sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getExternalTempValueOffset() + i]).getBytes());
                    sb.append(".");
                }
            }break;
            case BROOD: {
                for (int i = 0; i < HubPackageRepository.getInstance().getBroodTempValueLen(); i++) {
                    sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getBroodTempValueOffset() + i]).getBytes());
                    sb.append(".");
                }
            }break;
            case INTERNAL: {
                for (int i = 0; i < HubPackageRepository.getInstance().getInternalTempValueLen(); i++) {
                    sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getInternalTempValueOffset() + i]).getBytes());
                    sb.append(".");
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
            sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getBatteryValueOffset() + i]).getBytes());
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
            sb.append(String.valueOf(hubPackage[HubPackageRepository.getInstance().getHumidityValueOffset() + i]).getBytes());
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
            DeviceClient client = null;
            client = new DeviceClient(azureIoTHubConnStr, protocol);
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