package com.eu.mibeez.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Service {

    @Value("${message:World}")
    private String msg;

    public String message() {
        return this.msg;
    }
}