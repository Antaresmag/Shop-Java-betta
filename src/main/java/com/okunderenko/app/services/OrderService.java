
package com.okunderenko.app.services;

import com.okunderenko.app.exceptions.ItemNotFoundException;
import com.okunderenko.app.exceptions.OrderNotFoundException;
import com.okunderenko.app.exceptions.UserNotFoundException;
import com.okunderenko.app.models.Order;
import com.okunderenko.app.models.orders.OrderInputDto;

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
