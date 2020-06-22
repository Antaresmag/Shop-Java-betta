
package com.shopjava.app.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends NotFoundException {
    private UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super(String.format("Order %s not found", orderId));
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
