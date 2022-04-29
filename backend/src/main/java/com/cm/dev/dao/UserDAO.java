package com.cm.dev.dao;

import com.cm.dev.domain.User;

public interface UserDAO extends GenericDAO<User> {

    public User get(String username) throws Exception;

    public User updateUserRole(String username, String role) throws Exception;
}
