package com.eu.mibeez.jersey;

import com.eu.mibeez.endpoint.Endpoint;
import com.eu.mibeez.endpoint.HubEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(Endpoint.class);
        register(HubEndpoint.class);
    }
}
