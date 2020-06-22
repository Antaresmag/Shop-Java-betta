
package com.shopjava.app.services;

import com.shopjava.app.models.User;
import com.shopjava.app.models.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersRepository repository;

    @Override
    public User createUser(User newUser) {
        return repository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public boolean isUserExists(UUID userId) {
        return repository.existsById(userId);
    }

    @Override
    public boolean updateUser(UUID userId, User updatedEntity) {
        Optional<User> targetUser = getUserById(userId);

        if (targetUser.isPresent()) {
            User entityToUpdate = targetUser.get();

            if (updatedEntity.getFirstName() != null) {
                entityToUpdate.setFirstName(updatedEntity.getFirstName());
            }
            if (updatedEntity.getLastName() != null) {
                entityToUpdate.setLastName(updatedEntity.getLastName());
            }
            if (updatedEntity.getEmail() != null) {
                entityToUpdate.setEmail(updatedEntity.getEmail());
            }
            if (updatedEntity.getPasshash() != null) {
                entityToUpdate.setPasshash(updatedEntity.getPasshash());
            }

            repository.save(entityToUpdate);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(UUID userId) {
        Optional<User> targetUser = getUserById(userId);

        if (targetUser.isPresent()) {
            repository.deleteById(userId);
            return true;
        }
        else {
            return false;
        }
    }
}
