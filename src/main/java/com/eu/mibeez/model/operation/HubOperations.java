package com.eu.mibeez.model.operation;

import com.eu.mibeez.exception.HubIllegalOperationAppException;

public interface HubOperations {

    void init() throws HubIllegalOperationAppException;
}
