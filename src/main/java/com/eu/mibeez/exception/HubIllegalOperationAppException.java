package com.eu.mibeez.exception;

import com.eu.mibeez.exception.OperationAppException;

public class HubIllegalOperationAppException extends OperationAppException {
    public HubIllegalOperationAppException(String message) {
        super(message);
    }
}
