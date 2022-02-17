package com.company.services;

import com.company.models.Hotel;

public class HotelService {
    public boolean checkNeedUpdate(Hotel oldHotel, Hotel newHotel) {
        boolean needChange = false;
        if (!oldHotel.getCity().equals(newHotel.getCity())) {
            needChange = true;
        }
        if (!oldHotel.getName().equals(newHotel.getName())) {
            needChange = true;
        }
        return needChange;
    }
}
