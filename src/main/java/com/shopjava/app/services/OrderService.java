
package com.shopjava.app.services;

import com.shopjava.app.exceptions.ItemNotFoundException;
import com.shopjava.app.exceptions.OrderNotFoundException;
import com.shopjava.app.exceptions.UserNotFoundException;
import com.shopjava.app.models.Order;
import com.shopjava.app.models.orders.OrderInputDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderInputDto newOrder)
        throws ItemNotFoundException, UserNotFoundException;

    List<Order> getAllOrders();

    Order getOrderById(UUID orderId)
        throws OrderNotFoundException;

    Order updateOrder(UUID orderId, OrderInputDto updatedEntity)
        throws OrderNotFoundException, ItemNotFoundException, UserNotFoundException;

    void deleteOrder(UUID orderId)
        throws OrderNotFoundException;
}
