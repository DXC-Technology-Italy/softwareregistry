package com.cm.dev.dao;

import java.util.Collection;
import java.util.List;

public interface GenericDAO<T> {
    public T create(T entity) throws Exception;
    public Collection<T> createMany(List<T> entities) throws Exception;
    public void delete(T entity) throws Exception;
    public void deleteAll() throws Exception;

    public T save(T entity) throws Exception;
    public List<T> getAll() throws Exception;

}
