package com.honeywell.rtcmobile.api.model;

public class User {

    private String name,
                   email,
                   password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user:"+getName()+"\n"+
               "email:"+getEmail()+"\n"+
               "pass:"+getPassword();
    }
}
