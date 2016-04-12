package com.eu.mibeez.exception;

import com.eu.mibeez.exception.OperationAppException;

public class IllegalOperationAppException extends OperationAppException {
    public IllegalOperationAppException(String message) {
        super(message);
    }
}
