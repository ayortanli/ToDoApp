package com.ay.todo.controller;

import java.util.List;

public class UserInfoDTO{
    private String username;
    private List<String> roles;

    public UserInfoDTO(String username, List<String> roles){
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
