package com.eu.mibeez.event;

import org.springframework.context.ApplicationEvent;

public class SensorValueRequested extends ApplicationEvent {
    private final SensorValueEventArg sensorValueEventArg;

    public SensorValueRequested(Object source, SensorValueEventArg sensorValueEventArg) {
        super(source);
        this.sensorValueEventArg = sensorValueEventArg;
    }

    public SensorValueEventArg getSensorValues() {
        return sensorValueEventArg;
    }
}
