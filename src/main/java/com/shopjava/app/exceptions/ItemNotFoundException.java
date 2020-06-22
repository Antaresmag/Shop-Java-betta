
package com.shopjava.app.exceptions;

import java.util.UUID;

public class ItemNotFoundException extends NotFoundException {
    private UUID itemId;

    public ItemNotFoundException(UUID itemId) {
        super(String.format("Item %s not found", itemId));
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }
}
