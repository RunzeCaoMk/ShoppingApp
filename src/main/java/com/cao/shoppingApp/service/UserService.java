package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.UserDAO;
import com.cao.shoppingApp.domain.entity.User;
import com.cao.shoppingApp.domain.request.RegistrationRequest;
import com.cao.shoppingApp.exception.ConstraintViolationException;
import com.cao.shoppingApp.exception.EmailExistedException;
import com.cao.shoppingApp.exception.UsernameExistedException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public void setContentDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createNewUser(RegistrationRequest request) throws EmailExistedException, UsernameExistedException, ConstraintViolationException, ZeroOrManyException {
        // check duplicate username
        List<User> usersWithSameInfo = userDAO.getUserByUsername(request.getUsername());
        if (usersWithSameInfo != null && usersWithSameInfo.size() > 0) {
            throw new UsernameExistedException("Username existed");
        }

        // check duplicate email
        usersWithSameInfo = userDAO.getUserByEmail(request.getEmail());
        if (usersWithSameInfo != null && usersWithSameInfo.size() > 0) {
            throw new EmailExistedException("Email existed");
        }

        // check null
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        if (username == null || username.equals("") || password == null || password.equals("") || email == null || email.equals("")) {
            throw new ConstraintViolationException("Username, password, and email must be NOT NULL");
        }

        // create new user
        userDAO.createNewUser(request.getId(), request.getUsername(), request.getPassword(), request.getEmail());
    }

    public String getCurrentUsername() {
        return userDAO.getCurrentUsername();
    }

    public User getUserByUsername(String username) throws ZeroOrManyException {
        List<User> possibleUsers = userDAO.getUserByUsername(username);
        if (possibleUsers != null && possibleUsers.size() == 1) {
            return possibleUsers.get(0);
        } else {
            throw new ZeroOrManyException("Zero or more than 1 user returned.");
        }
    }

    public User getUserById(Integer id) throws ZeroOrManyException {
        List<User> possibleUsers = userDAO.getUserById(id);
        if (possibleUsers != null && possibleUsers.size() == 1) {
            return possibleUsers.get(0);
        } else {
            throw new ZeroOrManyException("Zero or more than 1 user returned.");
        }
    }
}
