
package com.okunderenko.app.services;

import com.okunderenko.app.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(UserServiceImpl.class)
public class DataUsersTest {
    @Autowired
    private UserService service;

    @Test
    public void createAndSave() {
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        var savedUser = service.createUser(newUser);

        UUID userId = savedUser.getId();
        Optional<User> dbUser = service.getUserById(userId);
        assertThat(dbUser.isPresent()).isTrue();
        assertThat(dbUser.get().getId()).isEqualTo(userId);
    }

    @Test
    public void getAllUsers() {
        service.createUser(new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        ));

        List<User> allUsers = service.getAllUsers();
        assertThat(allUsers.isEmpty()).isFalse();
    }

    @Test
    public void getUserById() {
        var newUser = new User(
                "Alex", "Tester",
                "alex.tester@gmail.com",
                "9bba5c53"
        );
        var savedUser = service.createUser(newUser);

        UUID targetId = savedUser.getId();
        Optional<User> targetUser = service.getUserById(targetId);
        assertThat(targetUser.isPresent()).isTrue();
    }

    @Test
    public void updateItem() {
        var newUser = new User(
                "Alex", "Tester",
                "alex.tester@gmail.com",
                "9bba5c53"
        );
        var savedUser = service.createUser(newUser);

        UUID targetId = savedUser.getId();
        Optional<User> targetUser = service.getUserById(targetId);
        assertThat(targetUser.isPresent()).isTrue();

        var updates = new User();
        var newLastName = "Brilliant Tester";
        updates.setLastName(newLastName);

        boolean updated = service.updateUser(targetId, updates);
        assertThat(updated).isTrue();

        Optional<User> userAfterUpdates = service.getUserById(targetId);
        assertThat(userAfterUpdates.isPresent()).isTrue();

        assertThat(userAfterUpdates.get().getLastName()).isEqualTo(newLastName);
    }

    @Test
    public void deleteUser() {
        var newUser = new User(
                "Alex", "Tester",
                "alex.tester@gmail.com",
                "9bba5c53"
        );
        var savedUser = service.createUser(newUser);

        UUID targetId = savedUser.getId();
        boolean deleted = service.deleteUser(targetId);
        assertThat(deleted).isTrue();

        var targetUser = service.getUserById(targetId);
        assertThat(targetUser.isPresent()).isFalse();
    }
}
