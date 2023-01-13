package com.home.gamespace.User;

public class User {
    private String userId;
    private String password;

    public User(){}

    public User(String userId,String password){
        this.setUserId(userId);
        this.setPassword(password);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
