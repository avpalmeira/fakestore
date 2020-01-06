package com.fakestore.mobile.model;

public class User {

    private String name,
                   Username,
                   Password,
                   token;

    public User(String Username, String Password) {

        this.Username = Username;
        this.Password = Password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "user:"+getName()+"\n"+
               "username:"+getUsername()+"\n"+
               "password:"+getPassword();
    }
}
