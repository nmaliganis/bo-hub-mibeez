package com.eu.mibeez.event.listener;

import com.eu.mibeez.event.SensorValueRequested;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SynchronousSensorValueListener implements ApplicationListener<SensorValueRequested> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SynchronousSensorValueListener.class);


    @Override
    public void onApplicationEvent(SensorValueRequested sensorValueRequested) {
        String values = sensorValueRequested.getSensorValues().getName();
        values += String.format(", added at: %s", LocalDateTime.now());
        sensorValueRequested.getSensorValues().setName(values);

        logger.debug("Values:" + values);
    }
}
