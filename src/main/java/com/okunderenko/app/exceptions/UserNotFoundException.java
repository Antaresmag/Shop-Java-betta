
package com.okunderenko.app.exceptions;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    private UUID userId;

    public UserNotFoundException(UUID userId) {
        super(String.format("User %s not found", userId));
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
