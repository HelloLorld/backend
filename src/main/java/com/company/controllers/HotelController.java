package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Hotel;
import com.company.repository.HotelRepository;
import com.company.services.HotelService;
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
public class HotelController {
    @Autowired
    private HotelRepository repository;
    private HotelService service = new HotelService();

    @GetMapping("/hotels")
    public List<Hotel> getHotels() {
        List<Hotel> list = repository.findAll();
        return list;
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Object> getHotel(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Hotel> hotel = repository.findById(id);
        if (!hotel.isPresent()) throw new ResourceNotFound("Hotel not found for id: " + id);
        else return new ResponseEntity<>(hotel.get(), HttpStatus.OK);
    }

    @PostMapping("/hotels")
    public Hotel saveHotel(@RequestBody Hotel hotel) {
        return repository.save(hotel);
    }

    @ExceptionHandler(ResourceNotFound.class)
    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Object> deleteHotel(@PathVariable(value = "id") Integer id) {
        Optional<Hotel> hotel = repository.findById(id);
        if (!hotel.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Hotel not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else repository.delete(hotel.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<Object> putOfHotel(@PathVariable(value = "id") Integer id, @RequestBody Hotel hotelChanged) {
        Optional<Hotel> hotel = repository.findById(id);
        if (!hotel.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Hotel not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);
        else {
            hotelChanged.setId(hotel.get().getId());
            repository.save(hotelChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/hotels/{id}")
    public ResponseEntity<Object> patchOfHotel(@PathVariable(value = "id") Integer id, @RequestBody Hotel hotelChanged) {
        Optional<Hotel> hotel = repository.findById(id);
        boolean needChange;
        if (!hotel.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Hotel not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else {
            hotelChanged.setId(hotel.get().getId());
            needChange = service.checkNeedUpdate(hotel.get(), hotelChanged);
            if (needChange) repository.save(hotelChanged);
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
