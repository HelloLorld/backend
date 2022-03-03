package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Country;
import com.company.repository.CountryRepository;
import com.company.services.CountryService;
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
public class CountryController {
    @Autowired
    private CountryRepository repository;
    private CountryService service = new CountryService();

    @GetMapping("/countries")
    public List<Country> getCountries() {
        List<Country> list = repository.findAll();
        return list;
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Object> getCountry(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Country> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Country not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/countries")
    public Country saveCountry(@RequestBody Country country) {
        return repository.save(country);
    }


    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Country> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Country not found for id: " + id);
        else repository.delete(country.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Object> putOfCountry(@PathVariable(value = "id") Integer id, @RequestBody Country countryChanged) throws ResourceNotFound {
        Optional<Country> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Country not found for id: " + id);
        else {
            countryChanged.setId(country.get().getId());
            repository.save(countryChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/countries/{id}")
    public ResponseEntity<Object> patchOfCountry(@PathVariable(value = "id") Integer id, @RequestBody Country countryChanged) throws ResourceNotFound {
        Optional<Country> country = repository.findById(id);
        boolean needChange;
        if (!country.isPresent()) throw new ResourceNotFound("Country not found for id: " + id);
        else {
            countryChanged.setId(country.get().getId());
            needChange = service.checkNeedUpdate(country.get(), countryChanged);
            if (needChange) repository.save(countryChanged);
        }

        if (needChange) {
            return new ResponseEntity<>(countryChanged, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
