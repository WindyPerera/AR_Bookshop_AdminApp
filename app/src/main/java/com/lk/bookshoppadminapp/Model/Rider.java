package com.lk.bookshoppadminapp.Model;

public class Rider {
    String riderName;
    String riderAddress;
    String riderEmail;
    String riderMobileNo;
    String vehicleNo;
    String licenseNo;
    String riderImagePath;
    String vehicleNoImagePath;
    String licenseNonImagePath;

    public Rider() {
    }

    public Rider(String riderName, String riderAddress, String riderEmail, String riderMobileNo, String vehicleNo, String licenseNo, String riderImagePath, String vehicleNoImagePath, String licenseNonImagePath) {
        this.riderName = riderName;
        this.riderAddress = riderAddress;
        this.riderEmail = riderEmail;
        this.riderMobileNo = riderMobileNo;
        this.vehicleNo = vehicleNo;
        this.licenseNo = licenseNo;
        this.riderImagePath = riderImagePath;
        this.vehicleNoImagePath = vehicleNoImagePath;
        this.licenseNonImagePath = licenseNonImagePath;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderAddress() {
        return riderAddress;
    }

    public void setRiderAddress(String riderAddress) {
        this.riderAddress = riderAddress;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    public String getRiderMobileNo() {
        return riderMobileNo;
    }

    public void setRiderMobileNo(String riderMobileNo) {
        this.riderMobileNo = riderMobileNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getRiderImagePath() {
        return riderImagePath;
    }

    public void setRiderImagePath(String riderImagePath) {
        this.riderImagePath = riderImagePath;
    }

    public String getVehicleNoImagePath() {
        return vehicleNoImagePath;
    }

    public void setVehicleNoImagePath(String vehicleNoImagePath) {
        this.vehicleNoImagePath = vehicleNoImagePath;
    }

    public String getLicenseNonImagePath() {
        return licenseNonImagePath;
    }

    public void setLicenseNonImagePath(String licenseNonImagePath) {
        this.licenseNonImagePath = licenseNonImagePath;
    }
}
