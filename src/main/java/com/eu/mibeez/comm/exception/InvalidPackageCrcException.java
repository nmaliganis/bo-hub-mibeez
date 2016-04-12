package com.eu.mibeez.comm.exception;

import com.eu.mibeez.comm.exception.CommAppException;

public class InvalidPackageCrcException extends CommAppException {

    public InvalidPackageCrcException(String message) {
        this(message, 0x00);
    }

    public InvalidPackageCrcException(String message, Object crc) {
        super(message);
    }
}
