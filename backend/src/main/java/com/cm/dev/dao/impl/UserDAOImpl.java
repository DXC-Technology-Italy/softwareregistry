package com.cm.dev.dao.impl;

import com.cm.dev.dao.UserDAO;
import com.cm.dev.domain.User;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Manages the persistence of User Objects
 * 
 */

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return User
     * @throws MongoException
     */
    @Override
    public User create(User entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }

    
    /** 
     * @param username
     * @return User
     * @throws MongoException
     */
    @Override
    public User get(String username) throws MongoException {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class);
    }

    
    /** 
     * @param entities
     * @return Collection<User>
     * @throws MongoException
     */
    @Override
    public Collection<User> createMany(List<User> entities) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(User entity) throws MongoException {
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
     * @return User
     * @throws MongoException
     */
    @Override
    public User save(User entity) throws MongoException {
        return mongoTemplate.save(entity);
    }

    
    /** 
     * @return List<User>
     * @throws MongoException
     */
    @Override
    public List<User> getAll() throws MongoException {
        return mongoTemplate.findAll(User.class);
    }

    
    /** 
     * @param username
     * @param role
     * @return User
     */
    @Override
    public User updateUserRole(String username, String role) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("role", role);

        return mongoTemplate.findAndModify(query, update, User.class);
    }
}
