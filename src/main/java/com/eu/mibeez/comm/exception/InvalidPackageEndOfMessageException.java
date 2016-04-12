package com.eu.mibeez.comm.exception;

import com.eu.mibeez.comm.exception.CommAppException;

public class InvalidPackageEndOfMessageException extends CommAppException {

    public InvalidPackageEndOfMessageException(String message) {
        this(message, null);
    }

    public InvalidPackageEndOfMessageException(String message, byte[] buffer) {
        super(message);
    }
}
