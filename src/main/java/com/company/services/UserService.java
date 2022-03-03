package com.company.services;

import com.company.models.Tour;
import com.company.models.User;

import java.util.Collections;
import java.util.List;

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
        if (checkTours(oldUser.getTours(),newUser.getTours())) {
            needChange = true;
        }
        return needChange;
    }

    private boolean checkTours(List<Tour> toursOfOldUser, List<Tour> toursOfNewUser) {
        if (toursOfOldUser.size() == toursOfNewUser.size()) {
            Collections.sort(toursOfOldUser);
            Collections.sort(toursOfNewUser);
            for (int i = 0; i < toursOfOldUser.size(); i++) {
                if (!toursOfOldUser.get(i).equals(toursOfNewUser.get(i))) return true;
            }
            return false;
        }
        else return true;
    }
}
