package com.company.services;

import com.company.models.User;

public class UserService {
    public boolean checkNeedUpdate(User oldUser, User newUser) {
        boolean needChange = false;
        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            needChange = true;
        }
        if (!oldUser.getFirstName().equals(newUser.getFirstName())) {
            needChange = true;
        }
        if (!oldUser.getLastName().equals(newUser.getLastName())) {
            needChange = true;
        }
        if (!oldUser.getTours().equals(newUser.getTours())) {
            needChange = true;
        }
        return needChange;
    }
}
