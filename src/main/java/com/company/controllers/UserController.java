package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.User;
import com.company.repository.UserRepository;
import com.company.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserRepository repository;
    private UserService service = new UserService();

    @GetMapping("/users")
    public List<User> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) throw new ResourceNotFound("User not found for id: " + id); /*return
                new ResponseEntity<>(new ResourceNotFound("User not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);*/
        else return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        Optional<User> foundUser = repository.findByEmail(user.getEmail());
        if (!foundUser.isPresent()) {
            return repository.save(user);
        }
        else return null;
    }

    @PostMapping("/login")
    public Map<String, Integer> auth(@RequestBody Map<String, String> loginInfo) {
        Map<String, Integer> respInfo = new HashMap<>();
        Optional<User> user = repository.findByEmail(loginInfo.get("email"));
        if (user.isPresent()) {
            if (user.get().getPassword().equals(loginInfo.get("password"))) {
                respInfo.put("password", 1);
                respInfo.put("userId", user.get().getId());
            }
            else respInfo.put("password", 0);
            respInfo.put("email", 1);
        }
        else {
            respInfo.put("password", 0);
            respInfo.put("email", 0);
        }
        return respInfo;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) throw new ResourceNotFound("User not found for id: " + id);
        else repository.delete(user.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> putOfUser(@PathVariable(value = "id") Integer id, @RequestBody User userChanged) throws ResourceNotFound {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) throw new ResourceNotFound("User not found for id: " + id);
        else {
            userChanged.setId(user.get().getId());
            repository.save(userChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> patchOfUser(@PathVariable(value = "id") Integer id, @RequestBody User userChanged) throws ResourceNotFound {
        Optional<User> user = repository.findById(id);
        boolean needChange;
        if (!user.isPresent()) throw new ResourceNotFound("User not found for id: " + id);
        else {
            userChanged.setId(user.get().getId());
            needChange = service.checkNeedUpdate(user.get(), userChanged);
            if (needChange) repository.save(userChanged);
        }

        if (needChange) {
            return new ResponseEntity<>(userChanged, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
