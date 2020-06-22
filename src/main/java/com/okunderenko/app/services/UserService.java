
package com.okunderenko.app.services;

import com.okunderenko.app.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User newUser);

    List<User> getAllUsers();

    Optional<User> getUserById(UUID userId);

    boolean isUserExists(UUID userId);

    boolean updateUser(UUID userId, User updatedEntity);

    boolean deleteUser(UUID userId);
}
