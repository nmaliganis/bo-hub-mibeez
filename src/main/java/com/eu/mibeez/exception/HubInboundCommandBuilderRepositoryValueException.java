package com.eu.mibeez.exception;

public class HubInboundCommandBuilderRepositoryValueException extends OperationAppException {
    private static Byte Index = 0x00;
    public HubInboundCommandBuilderRepositoryValueException(Byte index, String message) {
        super(message);
        Index = index;
    }
    @Override
    public String getMessage() {
        return BuildMessage();
    }

    @Override
    public String toString() {
        return BuildMessage();
    }

    private String BuildMessage(){
        return String.format("The command with code: '%1$s' doesn't exist on Hub command repository. ", Index.toString());
    }
}
