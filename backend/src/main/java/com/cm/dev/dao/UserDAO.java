package com.cm.dev.dao;

import com.cm.dev.domain.User;
import com.mongodb.MongoException;

/**
 * Interface for User Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface UserDAO extends GenericDAO<User> {

    User get(String username) throws MongoException;

    User updateUserRole(String username, String role) throws MongoException;
}
