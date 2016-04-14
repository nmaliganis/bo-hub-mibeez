package com.eu.mibeez.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("azure")
public final class AzureProperties {

  private static String hubSn;
  private static String wanAddress;
  private static String iotHub;

  private static AzureProperties uniqueInstance;
  
  private AzureProperties() {
  }

  public static synchronized AzureProperties getProperties() {
        if (uniqueInstance == null) {
            uniqueInstance = new AzureProperties();
        }

    return uniqueInstance;
    }
  
    public String getHubSn() {
        return hubSn;
    }

    public String getWanAddress() {
        return wanAddress;
    }

    public String getIotHub() {
        return iotHub;
    }

    public void setHubSn(String hubSn) {
        this.hubSn = hubSn;
    }

    public void setWanAddress(String wanAddress) {
        this.wanAddress = wanAddress;
    }

    public void setIotHub(String iotHub) {
        this.iotHub = iotHub;
    }
}
