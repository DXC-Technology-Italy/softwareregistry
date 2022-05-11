package com.cm.dev.service.impl;

import com.cm.dev.domain.UserAccessLog;
import com.cm.dev.dao.UserAccessLogDAO;
import com.cm.dev.dao.UserDAO;
import com.cm.dev.domain.User;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Business Logic for User Objects
 * 
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAccessLogDAO userAccessLogDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${environment}")
    private String environment;

    
    /** 
     * @param username
     * @return User
     * @throws ServiceException
     */
    @Override
    public User executeLogin(String username) throws ServiceException {
        User user = new User();
        Date now = new Date();
        UserAccessLog log = new UserAccessLog(username, environment);
        userAccessLogDAO.create(log);
        User existingUser = userDAO.get(username);
        if (existingUser != null) {
            existingUser.setLastLogin(now);
            existingUser.setAuthenticated(true);
            user = userDAO.save(existingUser);
        } else {
            user.setUsername(username);
            user.setTheme("light");
            user.setAccessGranted(true);
            user.setAuthorizedPages(new ArrayList<>());
            user.setRole("USER");
            user.setLastLogin(now);
            user.setAuthenticated(true);
            user = userDAO.create(user);
        }

        return user;
    }

    
    /** 
     * @param username
     * @param theme
     * @throws ServiceException
     */
    @Override
    public void updateUserTheme(String username, String theme) throws ServiceException {
        User user;
        user = userDAO.get(username);
        user.setTheme(theme);
        userDAO.save(user);

    }

    
    /** 
     * @return List<User>
     * @throws ServiceException
     */
    @Override
    public List<User> getAllUsers() throws ServiceException {
        return userDAO.getAll();
    }

    
    /** 
     * @param username
     * @param role
     * @throws ServiceException
     */
    @Override
    public void updateUserRole(String username, String role) throws ServiceException {
        userDAO.updateUserRole(username, role);
    }
}
