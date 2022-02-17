package com.company.services;

import com.company.models.City;

public class CityService {
    public boolean checkNeedUpdate(City oldCity, City newCity) {
        boolean needChange = false;
        if (!oldCity.getName().equals(newCity.getName())) {
            needChange = true;
        }
        if (!oldCity.getCountry().equals(newCity.getCountry())) {
            needChange = true;
        }
        return needChange;
    }
}
