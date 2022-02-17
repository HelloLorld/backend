package com.company.services;

import com.company.models.City;
import com.company.models.Tour;

public class TourService {
    public boolean checkNeedUpdate(Tour oldTour, Tour newTour) {
        boolean needChange = false;
        if (!oldTour.getDescription().equals(newTour.getDescription())) {
            needChange = true;
        }
        if (!oldTour.getHotel().equals(newTour.getHotel())) {
            needChange = true;
        }
        if (!oldTour.getType().equals(newTour.getType())) {
            needChange = true;
        }
        return needChange;
    }
}
