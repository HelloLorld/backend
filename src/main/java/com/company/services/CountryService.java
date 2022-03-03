package com.company.services;

import com.company.models.City;
import com.company.models.Country;

public class CountryService {
    public boolean checkNeedUpdate(Country oldCountry, Country newCountry) {
        return !oldCountry.equals(newCountry);
    }
}
