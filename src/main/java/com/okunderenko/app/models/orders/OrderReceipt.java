
package com.okunderenko.app.models.orders;

import com.okunderenko.app.models.ShopItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderReceipt {
    private UUID orderId;

    private LocalDateTime createdAt;

    private List<ShopItem> items;

    private int quantity;

    private double totalPrice;

    private String comment;

    public OrderReceipt(
        UUID orderId,
        LocalDateTime createdAt,
        List<ShopItem> items,
        int quantity,
        double totalPrice,
        String comment
    ) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.items = items;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.comment = comment;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getComment() {
        return comment;
    }

    public static class Builder {
        private UUID orderId;

        private LocalDateTime createdAt;

        private List<ShopItem> items;

        private int quantity;

        private double totalPrice;

        private String comment;

        public Builder setOrderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setItems(List<ShopItem> items) {
            this.items = items;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public OrderReceipt build() {
            return new OrderReceipt(orderId, createdAt, items, quantity, totalPrice, comment);
        }
    }
}
