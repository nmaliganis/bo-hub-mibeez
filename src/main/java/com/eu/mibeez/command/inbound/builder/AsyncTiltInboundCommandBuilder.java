package com.eu.mibeez.command.inbound.builder;

import com.eu.mibeez.command.inbound.builder.message.MessageStatus;

import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AsyncTiltInboundCommandBuilder extends HubInboundCommandBuilder
        implements HubInboundCommandBuilderOperations{


    @Override
    public void build(byte[] hubPackage) throws IOException {
        buildAndSendAsync(hubPackage);
    }

    @Override
    protected void buildOutMessage(MessageStatus status, String message ) {
        switch (status) {
            case MESSAGE_DEBUG: {
                logger.debug("-----------------------------------------");
                logger.debug(String.format(" On Async Tilt Message build:%s", message));
                logger.debug("-----------------------------------------");
            }break;
            case MESSAGE_ERROR: {
                logger.debug("-----------------------------------------");
                logger.debug(String.format(" On Error on Async Tilt Message:%s", message));
                logger.debug("-----------------------------------------");
            }break;
        }
    }
}
