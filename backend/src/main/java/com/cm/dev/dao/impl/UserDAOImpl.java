package com.cm.dev.dao.impl;

import com.cm.dev.dao.UserDAO;
import com.cm.dev.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User create(User entity) throws Exception {
        return mongoTemplate.insert(entity);
    }

    @Override
    public User get(String username) throws Exception {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class);
    }

    @Override
    public Collection<User> createMany(List<User> entities) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void delete(User entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void deleteAll() throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public User save(User entity) throws Exception {
        User savedUser = mongoTemplate.save(entity);
        return savedUser;
    }

    @Override
    public List<User> getAll() throws Exception {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User updateUserRole(String username, String role) throws Exception {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("role", role);

        return mongoTemplate.findAndModify(query, update, User.class);
    }
}
