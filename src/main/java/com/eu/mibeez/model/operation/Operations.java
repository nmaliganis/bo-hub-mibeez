package com.eu.mibeez.model.operation;

import com.eu.mibeez.exception.IllegalOperationAppException;

public interface Operations {
    void commission() throws IllegalOperationAppException;

    void decommission() throws IllegalOperationAppException;

    void standby() throws IllegalOperationAppException;

    void ready() throws IllegalOperationAppException;
}
