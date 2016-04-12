package com.eu.mibeez.comm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eu.mibeez.comm.checker.HubPackageChecker;
import com.eu.mibeez.comm.repository.HubPackageRepository;
import com.eu.mibeez.command.HubInboundCommandBuilderRepository;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.LoggerFactory;

public final class PortHandler implements PortHandlerOperations, SerialPortEventListener
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PortHandler.class);

    private static PortHandler uniqueInstance;

    private static final String defaultComPort = "COM1";
    private static SerialPort comPort;
    private static ArrayList input;
    private static boolean portOpened = false;
    private static final int baud = 28800, databits = 8, parity = 0, stopbits = 1;

    private PortHandler() {

    }

    public static synchronized PortHandler getHandler() {
        if (uniqueInstance == null) {
            uniqueInstance = new PortHandler();
        }
        InitPortHandlerValues();
        return uniqueInstance;
    }

    private static void InitPortHandlerValues()
    {
        try {
            comPort = new SerialPort(defaultComPort);
            comPort.addEventListener(uniqueInstance);
        } catch (SerialPortException ex) {
            logger.error(String.valueOf(ex));
        }
        input = new ArrayList();
    }

    @Override
    public boolean getIsComPortOpen() {
        return portOpened;
    }

    @Override
    public void toggleComPort() {
        if(portOpened){
            try {
                comPort.closePort();
            } catch (SerialPortException ex) {
                logger.error(String.valueOf(ex));
            }
            if(input != null)
                input.clear();
        }
        else{
            try {
                comPort.setParams(baud, databits, stopbits, parity);
                comPort.openPort();
            } catch (SerialPortException ex) {
                logger.error(String.valueOf(ex));
            }
        }
    }

    @Override
    public void sendPackage(byte[] dataPackage) {
        try {
            comPort.writeBytes(dataPackage);
        } catch (SerialPortException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {

        if(SerialPortEvent.RXCHAR == spe.getEventType()){
            int bytes = 0;
            try {
                bytes = comPort.getInputBufferBytesCount();
            } catch (SerialPortException ex) {
                logger.error(ex.getMessage());
            }

            byte[] buffer = new byte[bytes];

            try {
                buffer = comPort.readBytes(bytes);
            } catch (SerialPortException ex) {
                logger.error(ex.getMessage());
            }

            for(byte b : buffer) {
                input.add(b);
            }

            if (HubPackageChecker.getChecker().isEndOfReceivedPackage(buffer))
            {
                input.clear();

                if (buffer.length < HubPackageRepository.getInstance().getPackageLen() -1)
                    return;

                byte[] hubPackage = new byte[HubPackageRepository.getInstance().getPackageLen()];

                HubPackageChecker.getChecker().check(hubPackage, hubPackage[HubPackageRepository.getInstance().getCommandOffset()], true);

                try {
                    HubInboundCommandBuilderRepository
                            .getBuilder()
                            .get((byte) hubPackage[HubPackageRepository.getInstance().getCommandOffset()])
                            .build(hubPackage);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            input.clear();
        }
    }

    private void appendComPortData() throws SerialPortException {
        int bytes = comPort.getInputBufferBytesCount();
        byte[] buffer = new byte[bytes];

        buffer = comPort.readBytes(bytes);

        for(byte b : buffer) {
            input.add(b);
        }
    }
}
