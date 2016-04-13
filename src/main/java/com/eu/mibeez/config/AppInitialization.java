package com.eu.mibeez.config;

import com.eu.mibeez.comm.PortHandler;
import com.eu.mibeez.command.inbound.builder.AsyncTiltInboundCommandBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppInitialization implements CommandLineRunner {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AsyncTiltInboundCommandBuilder.class);

    @PostConstruct
    public void initilize(){
        logger.info("------------------------------------------------------------");
        logger.info("-------------{ Just Before initialize ComPort }-------------");
        if(!PortHandler.getHandler().getIsComPortOpen()){
            PortHandler.getHandler().toggleComPort();
        }
        logger.info("------------------------------------------------------------");
        logger.info("-------------{ Just After ComPort initialized }-------------");
    }

    @Override
    public void run(String... strings) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String option : strings) {
            sb.append(" ").append(option);
        }
        sb = sb.length() == 0 ? sb.append("No Options Specified") : sb;
        logger.info(String.format("JAR launched with following options: %s", sb.toString()));
    }
}
