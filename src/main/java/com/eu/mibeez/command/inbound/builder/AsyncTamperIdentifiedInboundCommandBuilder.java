package com.eu.mibeez.command.inbound.builder;

import com.eu.mibeez.command.inbound.builder.message.MessageStatus;

public class AsyncTamperIdentifiedInboundCommandBuilder extends HubInboundCommandBuilder
        implements HubInboundCommandBuilderOperations {

    @Override
    public void build(byte[] hubPackage) {
        buildInboundMessage(hubPackage);
        Thread t0 = new Thread(this);
        t0.start();
    }

    @Override
    protected void buildOutMessage(MessageStatus status, String message) {
        switch (status) {
            case MESSAGE_DEBUG: {
                logger.debug("-----------------------------------------");
                logger.debug(String.format(" On Async Tamper Message build:%s", message));
                logger.debug("-----------------------------------------");
            }break;
            case MESSAGE_ERROR: {
                logger.debug("-----------------------------------------");
                logger.debug(String.format(" On Error on Async Tamper Message:%s", message));
                logger.debug("-----------------------------------------");
            }break;
        }
    }
}
