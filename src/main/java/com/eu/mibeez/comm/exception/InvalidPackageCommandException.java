package com.eu.mibeez.comm.exception;

import com.eu.mibeez.comm.exception.CommAppException;

public class InvalidPackageCommandException extends CommAppException {

    public InvalidPackageCommandException(String message) {
        this(message, 0x00);
    }

    public InvalidPackageCommandException(String message, Object command) {
        super(message);
    }
}
