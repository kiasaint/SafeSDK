package com.codersworld.safelib.beans;

import java.io.Serializable;

public class GenerateOTP implements Serializable {
    private String otp;
    private String rpwd;
    private String otpGenerateTime;
    private String contactId;
    private String deviceId;
    private String vehicalNo;
    private String type;
    private int Id;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRpwd() {
        return rpwd;
    }

    public void setRpwd(String rpwd) {
        this.rpwd = rpwd;
    }

    public String getOtpGenerateTime() {
        return otpGenerateTime;
    }

    public void setOtpGenerateTime(String otpGenerateTime) {
        this.otpGenerateTime = otpGenerateTime;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVehicalNo() {
        return vehicalNo;
    }

    public void setVehicalNo(String vehicalNo) {
        this.vehicalNo = vehicalNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
