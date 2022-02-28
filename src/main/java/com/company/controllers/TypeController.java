package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Type;
import com.company.repository.TypeRepository;
import com.company.services.TypeService;
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
public class TypeController {
    @Autowired
    private TypeRepository repository;
    private TypeService service = new TypeService();

    @GetMapping("/types")
    public List<Type> getTypes() {
        List<Type> list = repository.findAll();
        return list;
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<Object> getType(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Type> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Type not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/types")
    public Type saveType(@RequestBody Type type) {
        return repository.save(type);
    }


    @DeleteMapping("/types/{id}")
    public ResponseEntity<Object> deleteType(@PathVariable(value = "id") Integer id) {
        Optional<Type> type = repository.findById(id);
        if (!type.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Type not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else repository.delete(type.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<Object> putOfType(@PathVariable(value = "id") Integer id, @RequestBody Type typeChanged) {
        Optional<Type> type = repository.findById(id);
        if (!type.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Type not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);
        else {
            typeChanged.setId(type.get().getId());
            repository.save(typeChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/types/{id}")
    public ResponseEntity<Object> patchOfType(@PathVariable(value = "id") Integer id, @RequestBody Type typeChanged) {
        Optional<Type> type = repository.findById(id);
        boolean needChange;
        if (!type.isPresent()) return
                new ResponseEntity<>(new ResourceNotFound("Type not found for id: " + id).getMessage()
                        , new HttpHeaders(), HttpStatus.NOT_FOUND);
        else {
            typeChanged.setId(type.get().getId());
            needChange = service.checkNeedUpdate(type.get(), typeChanged);
            if (needChange) repository.save(typeChanged);
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
