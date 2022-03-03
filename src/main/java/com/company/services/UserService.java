package com.company.services;

import com.company.models.User;

public class UserService {
    public boolean checkNeedUpdate(User oldUser, User newUser) {
        return !oldUser.equals(newUser);
    }
}
