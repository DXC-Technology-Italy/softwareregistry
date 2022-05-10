package com.cm.dev.dao;

import com.mongodb.MongoException;

import java.util.Collection;
import java.util.List;

/**
 * Exposes basic CRUD operations
 */

public interface GenericDAO<T> {
    T create(T entity) throws MongoException;

    Collection<T> createMany(List<T> entities) throws MongoException;

    void delete(T entity) throws MongoException;

    void deleteAll() throws MongoException;

    T save(T entity) throws MongoException;

    List<T> getAll() throws MongoException;

}
