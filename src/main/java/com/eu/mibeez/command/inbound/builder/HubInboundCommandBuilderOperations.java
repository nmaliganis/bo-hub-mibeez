package com.eu.mibeez.command.inbound.builder;

import com.eu.mibeez.command.inbound.HubInboundCommand;
import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;

public interface HubInboundCommandBuilderOperations
{
    void build(byte[] hubPackage) throws IOException;
}
