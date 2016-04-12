package com.eu.mibeez.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.eu.mibeez.event.SensorValueEventArg;
import com.eu.mibeez.event.SensorValueRequested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;


@Path("/mibeez")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
//@Transactional
public class Endpoint {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GET
    public String message() {
        SensorValueEventArg sensorValueEventArg = new SensorValueEventArg("mnikolaos");
        SensorValueRequested sensorValueRequested = new SensorValueRequested(this, sensorValueEventArg);
        eventPublisher.publishEvent(sensorValueRequested);
        return "/greet";
    }
}
