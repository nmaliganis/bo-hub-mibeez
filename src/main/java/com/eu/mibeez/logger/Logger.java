package com.eu.mibeez.logger;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Logger implements CommandLineRunner {

    private static final org.apache.log4j.Logger log4jLog =
            org.apache.log4j.Logger.getLogger(Logger.class);

    @PostConstruct
    private void logInit() {
        log4jLog.info("LOG4J initialized");
    }

    public void info(String message){
        log4jLog.info(message);
    }

    public void error(String message){
        log4jLog.error(message);
    }

    public void debug(String message){
        log4jLog.debug(message);
    }

    public void trace(String message){
        log4jLog.trace(message);
    }

    public void fatal(String message){
        log4jLog.fatal(message);
    }

    @Override
    public void run(String... strings) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String option : strings) {
            sb.append(" ").append(option);
        }
        sb = sb.length() == 0 ? sb.append("No Options Specified") : sb;
        log4jLog.info(String.format("JAR launched with following options: %s", sb.toString()));
    }
}
