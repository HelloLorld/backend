package com.company.services;

import com.company.models.Hotel;

public class HotelService {
    public boolean checkNeedUpdate(Hotel oldHotel, Hotel newHotel) {
        return !oldHotel.equals(newHotel);
    }
}
