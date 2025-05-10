package com.group.oodjAssignment.manager;

//Manager.java
public class Manager {

    private String username;
    private String password;

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return inputPassword.equals(password);
    }
}
