package com.cm.dev.service;

import com.cm.dev.domain.User;
import com.cm.dev.exception.ServiceException;

import java.util.List;

/**
 * Interface that exposes methods to handle Users business logic
 */
public interface UserService {

    User executeLogin(String username) throws ServiceException;

    void updateUserTheme(String username, String theme) throws ServiceException;

    void updateUserRole(String username, String role) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
}
