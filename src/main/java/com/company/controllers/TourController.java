package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Country;
import com.company.models.Tour;
import com.company.repository.CountryRepository;
import com.company.repository.TourRepository;
import com.company.services.CountryService;
import com.company.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TourController {
    @Autowired
    private TourRepository repository;
    private TourService service = new TourService();

    @GetMapping("/tours")
    public List<Tour> getTours() {
        List<Tour> list = repository.findAll();
        return list;
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<Object> getTour(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Tour> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Tour not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/tours")
    public Tour saveTour(@RequestBody Tour tour) {
        return repository.save(tour);
    }

    @ExceptionHandler(ResourceNotFound.class)
    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Object> deleteTour(@PathVariable(value = "id") Integer id) {
        Optional<Tour> tour = repository.findById(id);
        if (!tour.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Tour not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else repository.delete(tour.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/tours/{id}")
    public ResponseEntity<Object> putOfTour(@PathVariable(value = "id") Integer id, @RequestBody Tour tourChanged) {
        Optional<Tour> tour = repository.findById(id);
        if (!tour.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Tour not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);
        else {
            tourChanged.setId(tour.get().getId());
            repository.save(tourChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/tours/{id}")
    public ResponseEntity<Object> patchOfTour(@PathVariable(value = "id") Integer id, @RequestBody Tour tourChanged) {
        Optional<Tour> tour = repository.findById(id);
        boolean needChange;
        if (!tour.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Tour not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else {  
            tourChanged.setId(tour.get().getId());
            needChange = service.checkNeedUpdate(tour.get(), tourChanged);
            if (needChange) repository.save(tourChanged);
        }

        Map<String, Boolean> response = new HashMap<>();
        if (needChange) {
            response.put("changed", true);
        } else {
            response.put("changed", false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}