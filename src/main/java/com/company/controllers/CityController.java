package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.City;
import com.company.repository.CityRepository;
import com.company.services.CityService;
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
public class CityController {
    @Autowired
    private CityRepository repository;
    private CityService service = new CityService();

    @GetMapping("/cities")
    public List<City> getCities() {
        List<City> list = repository.findAll();
        return list;
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<Object> getCity(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<City> city = repository.findById(id);
        if (!city.isPresent()) throw new ResourceNotFound("City not found for id: " + id);
        else return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }

    @PostMapping("/cities")
    public City saveCity(@RequestBody City city) {
        return repository.save(city);
    }

    @ExceptionHandler(ResourceNotFound.class)
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable(value = "id") Integer id) {
        Optional<City> city = repository.findById(id);
        if (!city.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("City not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else repository.delete(city.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<Object> putOfCity(@PathVariable(value = "id") Integer id, @RequestBody City cityChanged) {
        Optional<City> city = repository.findById(id);
        if (!city.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("City not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);
        else {
            cityChanged.setId(city.get().getId());
            repository.save(cityChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/cities/{id}")
    public ResponseEntity<Object> patchOfCity(@PathVariable(value = "id") Integer id, @RequestBody City cityChanged) {
        Optional<City> city = repository.findById(id);
        boolean needChange;
        if (!city.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("City not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else {
            cityChanged.setId(city.get().getId());
            needChange = service.checkNeedUpdate(city.get(), cityChanged);
            if (needChange) repository.save(cityChanged);
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
