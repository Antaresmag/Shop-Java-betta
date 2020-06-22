
package com.okunderenko.app.models.orders;

import java.util.List;
import java.util.UUID;

public class OrderInputDto {
    private UUID userId;

    private List<UUID> itemIds;

    private String comment;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<UUID> itemIds) {
        this.itemIds = itemIds;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
