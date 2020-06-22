
package com.shopjava.app.controllers;

import com.shopjava.app.models.User;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getAllItems() {
        List<User> users = service.getAllUsers();
        ApiResponse<List<User>> response =
            new ApiResponse.Builder<List<User>>()
                .setSuccess(true).setResponse(users).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        Optional<User> user = service.getUserById(UUID.fromString(id));

        var responseBuilder = new ApiResponse.Builder<User>();
        responseBuilder.setSuccess(user.isPresent());

        if (user.isPresent()) {
            responseBuilder.setResponse(user.get());
        }
        else {
            responseBuilder.setErrorMessage("Not found");
        }

        return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Boolean>> createUser(
        @RequestBody User requestEntity
    ) {
        service.createUser(requestEntity);
        var response = new ApiResponse.Builder<Boolean>()
            .setSuccess(true).setResponse(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateUser(
        @PathVariable String id,
        @RequestBody User requestEntity
    ) {
        var userId = UUID.fromString(id);
        boolean updated = service.updateUser(userId, requestEntity);

        var responseBuilder = new ApiResponse.Builder<Boolean>()
            .setSuccess(updated).setResponse(updated);
        if (!updated) {
            responseBuilder.setErrorMessage("User not found");
        }
        return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteItem(@PathVariable String id) {
        boolean deleted = service.deleteUser(UUID.fromString(id));

        var responseBuilder = new ApiResponse.Builder<Boolean>()
            .setSuccess(deleted).setResponse(deleted);
        if (!deleted) {
            responseBuilder.setErrorMessage("User not found");
        }
        return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);
    }
}
