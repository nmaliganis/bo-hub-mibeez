package com.eu.mibeez.command.inbound;

import com.eu.mibeez.command.inbound.HubCommandOperations;

public abstract class HubCommand implements HubCommandOperations
{
    private char _code;
    private String _name;

    public char getCode()
    {
        return _code;
    }

    public void setCode(char code)
    {
        _code = code;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

}
