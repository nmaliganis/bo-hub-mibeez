package com.eu.mibeez.command;

import com.eu.mibeez.comm.repository.HubPackageRepository;
import com.eu.mibeez.command.inbound.builder.*;
import com.eu.mibeez.exception.HubInboundCommandBuilderRepositoryValueException;

import java.util.HashMap;
import java.util.Map;

public final class HubInboundCommandBuilderRepository
{
    private static final HubInboundCommandBuilderRepository instance = new HubInboundCommandBuilderRepository();

    private Map<Byte,HubInboundCommandBuilderOperations> cmdBuilders = new HashMap<Byte,HubInboundCommandBuilderOperations>();

    private HubInboundCommandBuilderRepository()
    {
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAckCode(), new AckInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getNackCode(), new NackInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncBatteryLevelEventCode(), new AsyncBatteryInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncTamperIdentifiedCode(), new AsyncTamperIdentifiedInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncExternalTempEventCode(), new AsyncExternalTempInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncInternalTempEventCode(), new AsyncInternalTempInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncGpsEventCode(), new AsyncGpsInboundCommandBuilder());
        cmdBuilders.put((byte)HubPackageRepository.getInstance().getAsyncTiltEventCode() , new AsyncTiltInboundCommandBuilder());
    }

    public static HubInboundCommandBuilderRepository getBuilder()
    {
        return instance;
    }

    public HubInboundCommandBuilderOperations get(Byte index) throws HubInboundCommandBuilderRepositoryValueException
    {
        if( cmdBuilders.containsKey(index))
            return cmdBuilders.get(index);
        else throw new HubInboundCommandBuilderRepositoryValueException(index, String.format("Index error on {0}"));
    }

}
