package api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {
    private String device_id;
    private Integer charging;

    public Device() {
        // Jackson deserialization
    }

    public Device(String device_id, Integer charging) {
        this.device_id = device_id;
        this.charging = charging;
    }

    @JsonProperty
    public String getId() {
        return device_id;
    }

    @JsonProperty
    public Integer getCharging() {
        return charging;
    }
}
