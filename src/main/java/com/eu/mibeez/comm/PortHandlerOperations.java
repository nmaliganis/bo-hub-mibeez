package com.eu.mibeez.comm;

public interface PortHandlerOperations {
    boolean getIsComPortOpen();
    void toggleComPort();
    void sendPackage(byte[] dataPackage);
}

