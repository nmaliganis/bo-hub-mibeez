package com.eu.mibeez.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HubService {

    @Value("${message:World}")
    private String msg;

    public String message() {
        return this.msg;
    }
}