package com.eu.mibeez.config;

import com.eu.mibeez.endpoint.Endpoint;
import com.eu.mibeez.endpoint.HubEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class JerseyInitialization extends ResourceConfig {

    public JerseyInitialization() {
        this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        this.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        this.packages(true, "com.eu.mibeez.endpoint");
        this.register(Endpoint.class);
        this.register(HubEndpoint.class);
    }
}