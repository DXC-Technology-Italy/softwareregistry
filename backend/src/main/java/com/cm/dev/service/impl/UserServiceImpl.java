package com.cm.dev.service.impl;

import com.cm.dev.bean.UserAccessLog;
import com.cm.dev.dao.UserAccessLogDAO;
import com.cm.dev.dao.UserDAO;
import com.cm.dev.domain.User;
import com.cm.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAccessLogDAO userAccessLogDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${environment}")
    private String environment;

    @Override
    public User executeLogin(String username) {
        User user = new User();
        try {
            Date now = new Date();
            UserAccessLog log = new UserAccessLog(username, now, environment);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User updateUserTheme(String username, String theme) {
        User user = null;
        try {
            user = userDAO.get(username);
            user.setTheme(theme);
            userDAO.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userDAO.getAll();
    }

    @Override
    public User updateUserRole(String username, String role) throws Exception {
        return userDAO.updateUserRole(username, role);
    }
}
