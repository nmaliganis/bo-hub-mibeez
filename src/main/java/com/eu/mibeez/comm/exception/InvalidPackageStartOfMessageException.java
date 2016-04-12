package com.eu.mibeez.comm.exception;

public class InvalidPackageStartOfMessageException extends CommAppException {

    public InvalidPackageStartOfMessageException(String message) {
        this(message, null);
    }

    public InvalidPackageStartOfMessageException(String message, byte[] buffer) {
        super(message);
    }
}
