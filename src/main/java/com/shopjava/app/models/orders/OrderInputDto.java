
package com.shopjava.app.models.orders;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof OrderInputDto)) return false;

        OrderInputDto another = (OrderInputDto) obj;

        if (this.getUserId() != null && another.getUserId() != null) {
            if (!this.getUserId().equals(another.getUserId())) {
                return false;
            }
        }

        if (this.getItemIds() != null && another.getItemIds() != null) {
            if (!this.getItemIds().equals(another.getItemIds())) {
                return false;
            }
        }

        if (this.getComment() != null && another.getComment() != null) {
            return this.getComment().equals(another.getComment());
        }

        return true;
    }
}
