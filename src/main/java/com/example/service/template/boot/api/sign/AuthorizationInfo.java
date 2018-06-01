package com.example.service.template.boot.api.sign;

public class AuthorizationInfo {
    private String deviceId;
    private long signTimestamp;
    private String signVersion;
    private String sign;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSignVersion() {
        return signVersion;
    }

    public void setSignVersion(String signVersion) {
        this.signVersion = signVersion;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getSignTimestamp() {
        return signTimestamp;
    }

    public void setSignTimestamp(long signTimestamp) {
        this.signTimestamp = signTimestamp;
    }
}
