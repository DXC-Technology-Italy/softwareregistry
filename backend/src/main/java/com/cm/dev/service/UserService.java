package com.cm.dev.service;

import com.cm.dev.domain.User;

import java.util.List;

public interface UserService {
    public User executeLogin(String username) throws Exception;

    public User updateUserTheme(String username, String theme) throws Exception;

    public User updateUserRole(String username, String role) throws Exception;

    public List<User> getAllUsers() throws Exception;
}
