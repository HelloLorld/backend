package com.company.services;

import com.company.models.City;
import com.company.models.Country;

public class CountryService {
    public boolean checkNeedUpdate(Country oldCountry, Country newCountry) {
        boolean needChange = false;
        if (!oldCountry.getName().equals(newCountry.getName())) {
            needChange = true;
        }
        return needChange;
    }
}
