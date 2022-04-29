package com.cm.dev.dao.impl;

import com.cm.dev.bean.UserAccessLog;
import com.cm.dev.dao.UserAccessLogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class UserAccessLogDAOImpl implements UserAccessLogDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserAccessLog create(UserAccessLog entity) throws Exception {
        return mongoTemplate.insert(entity);
    }


    @Override
    public Collection<UserAccessLog> createMany(List<UserAccessLog> entities) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void delete(UserAccessLog entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void deleteAll() throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public UserAccessLog save(UserAccessLog entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public List<UserAccessLog> getAll() throws Exception {
        throw new Exception("Not implemented");
    }
}
