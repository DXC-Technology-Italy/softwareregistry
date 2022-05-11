package com.cm.dev.dao.impl;

import com.cm.dev.domain.UserAccessLog;
import com.cm.dev.dao.UserAccessLogDAO;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Manages the persistence of User Access Logs Objects
 * 
 */

@Repository
public class UserAccessLogDAOImpl implements UserAccessLogDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return UserAccessLog
     * @throws MongoException
     */
    @Override
    public UserAccessLog create(UserAccessLog entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }


    
    /** 
     * @param entities
     * @return Collection<UserAccessLog>
     * @throws MongoException
     */
    @Override
    public Collection<UserAccessLog> createMany(List<UserAccessLog> entities) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(UserAccessLog entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @return UserAccessLog
     * @throws MongoException
     */
    @Override
    public UserAccessLog save(UserAccessLog entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @return List<UserAccessLog>
     * @throws MongoException
     */
    @Override
    public List<UserAccessLog> getAll() throws MongoException {
        throw new NotImplementedException();
    }
}
