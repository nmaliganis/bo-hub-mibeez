package com.eu.mibeez.command.inbound.builder;
import com.eu.mibeez.command.inbound.builder.message.MessageStatus;

public class AckInboundCommandBuilder extends HubInboundCommandBuilder
        implements  HubInboundCommandBuilderOperations, Runnable {

    @Override
    public void build(byte[] hubPackage) {
        buildInboundMessage(hubPackage);
        Thread t0 = new Thread(this);
        t0.start();
    }

    @Override
    protected void buildOutMessage(MessageStatus status, String message ) {
        switch (status) {
            case MESSAGE_DEBUG: {
                logger.info("-----------------------------------------");
                logger.info(String.format(" On Async Ack Message build:%s", message));
                logger.info("-----------------------------------------");
            }break;
            case MESSAGE_ERROR: {
                logger.info("-----------------------------------------");
                logger.info(String.format(" On Error on Async Ack Message:%s", message));
                logger.info("-----------------------------------------");
            }break;
        }
    }

}
