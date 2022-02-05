package com.lk.bookshoppadminapp.Model;

public class Admin {
    String adminName;
    String adminEmail;
    String adminMobile;
    String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Admin() {
    }

    public Admin(String adminName, String adminEmail, String adminMobile,String imagepath) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminMobile = adminMobile;
        this.imagePath = imagepath;

    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }


}
