package com.eu.mibeez.model;

import com.eu.mibeez.exception.HubIllegalOperationAppException;
import com.eu.mibeez.exception.IllegalOperationAppException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@JsonPropertyOrder({"lanAddress"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "hub")
public class Hub implements Serializable {

    private HubStatus status;
    private Instant statusUpdate;
    @NotNull(message="lanAddress is a required field")
    private String lanAddress;
    @NotNull(message="wanAddress is a required field")
    private String wanInterface;
    private String adminInterface;
    private Date published;
    @NotNull(message="hubSn is a required field")
    private String hubSn;
    private URI centralSystemURI;
    @NotNull(message="version is a required field")
    private String version;
    private String name;

    private HashMap<String,Object> extras = new HashMap<String,Object>();


    public HubStatus getStatus() {
        return status;
    }

    public void setStatus(HubStatus status) {
        this.status = status;
    }

    public Instant getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(Instant statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    public String getLanAddress() {
        return lanAddress;
    }

    public void setLanAddress(String lanAddress) {
        this.lanAddress = lanAddress;
    }

    public String getWanInterface() {
        return wanInterface;
    }

    public void setWanInterface(String wanInterface) {
        this.wanInterface = wanInterface;
    }

    public String getAdminInterface() {
        return adminInterface;
    }

    public void setAdminInterface(String adminInterface) {
        this.adminInterface = adminInterface;
    }

    public String getHubSn() {
        return hubSn;
    }

    public void setHubSn(String hubSn) {
        this.hubSn = hubSn;
    }

    public URI getCentralSystemURI() {
        return centralSystemURI;
    }

    public void setCentralSystemURI(URI centralSystemURI) {
        this.centralSystemURI = centralSystemURI;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Hub(HubStatus status,
               Instant statusUpdate,
               String lanAddress,
               String wanInterface,
               String adminInterface,
               String hubSn,
               URI centralSystemURI,
               String version,
               String name){
        this.status = status;
        this.statusUpdate = statusUpdate;
        this.lanAddress = lanAddress;
        this.wanInterface = wanInterface;
        this.adminInterface = adminInterface;
        this.hubSn = hubSn;
        this.centralSystemURI = centralSystemURI;
        this.version = version;
        this.name = name;
    }

    @JsonAnyGetter
    public HashMap<String, Object> getExtras() {
        return extras;
    }

    @JsonAnySetter
    public void set(String key, Object value) {
        this.extras.put(key, value);
    }
}
