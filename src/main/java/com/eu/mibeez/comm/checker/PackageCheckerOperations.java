package com.eu.mibeez.comm.checker;

public interface PackageCheckerOperations {
    void check(byte[] buffer, byte command, boolean checkCrc);
    boolean isEndOfReceivedPackage(byte[] buffer);
}
