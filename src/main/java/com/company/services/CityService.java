package com.company.services;

import com.company.models.City;

public class CityService {
    public boolean checkNeedUpdate(City oldCity, City newCity) {
        return !oldCity.equals(newCity);
    }
}
