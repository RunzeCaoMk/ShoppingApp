package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.UserDAO;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.request.RegistrationRequest;
import com.cao.shoppingApp.exception.ConstraintViolationException;
import com.cao.shoppingApp.exception.EmailExistedException;
import com.cao.shoppingApp.exception.UsernameExistedException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public void setContentDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createNewUser(RegistrationRequest request) throws EmailExistedException, UsernameExistedException, ConstraintViolationException, ZeroOrManyException {
        // check duplicate username
        User usersWithSameInfo = userDAO.getUserByUsername(request.getUsername());
        if (usersWithSameInfo != null) {
            throw new UsernameExistedException("Username existed");
        }

        // check duplicate email
        usersWithSameInfo = userDAO.getUserByEmail(request.getEmail());
        if (usersWithSameInfo != null) {
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
        userDAO.createNewUser(request.getId(), request.getUsername(), request.getPassword(), request.getEmail(), request.is_admin());
    }

    public User getUserByUsername(String username) throws ZeroOrManyException {
        return userDAO.getUserByUsername(username);
    }
}
