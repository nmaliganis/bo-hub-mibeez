package com.eu.mibeez.command.inbound.builder.json;

import com.eu.mibeez.command.inbound.builder.type.TamperState;
import com.google.gson.Gson;

public class MibeezData {
    public String hubSn;
    public String lanAddress;
    public String wanAddress;
    public String tilt;
    public String externalTemp;
    public String broodTemp;
    public String internalTemp;
    public String batteryValue;
    public String humidity;
    public TamperState tamper;
    public Double latitude;
    public Double longitude;

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
