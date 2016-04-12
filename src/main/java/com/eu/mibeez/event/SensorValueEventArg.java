package com.eu.mibeez.event;

import org.springframework.context.ApplicationEvent;

public class SensorValueEventArg{
    private String name;

    public SensorValueEventArg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
