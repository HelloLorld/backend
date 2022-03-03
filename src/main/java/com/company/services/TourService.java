package com.company.services;

import com.company.models.City;
import com.company.models.Tour;

public class TourService {
    public boolean checkNeedUpdate(Tour oldTour, Tour newTour) {
        return !oldTour.equals(newTour);
    }
}
