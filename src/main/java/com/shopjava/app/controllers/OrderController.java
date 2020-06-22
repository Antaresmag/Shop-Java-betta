
package com.shopjava.app.controllers;

import com.shopjava.app.exceptions.NotFoundException;
import com.shopjava.app.exceptions.OrderNotFoundException;
import com.shopjava.app.models.Order;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.models.orders.OrderInputDto;
import com.shopjava.app.models.orders.OrderReceipt;
import com.shopjava.app.services.OrderService;
import com.shopjava.app.utils.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = service.getAllOrders();
        ApiResponse<List<Order>> response =
            new ApiResponse.Builder<List<Order>>()
                .setSuccess(true).setResponse(orders).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String id) {
        var status = HttpStatus.OK;
        var responseBuilder = new ApiResponse.Builder<Order>();

        try {
            Order order = service.getOrderById(UUID.fromString(id));
            responseBuilder.setResponse(order);
        }
        catch (OrderNotFoundException ex) {
            responseBuilder.setErrorMessage(ex.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseBuilder.build(), status);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderReceipt>> createOrder(
        @RequestBody OrderInputDto orderInputData
    ) {
        var status = HttpStatus.OK;
        var responseBuilder = new ApiResponse.Builder<OrderReceipt>();

        try {
            Order order = service.createOrder(orderInputData);
            OrderReceipt receipt = Orders.createReceiptFrom(order);
            responseBuilder.setResponse(receipt);
        }
        catch (NotFoundException ex) {
            responseBuilder.setErrorMessage(ex.getMessage());
            status = HttpStatus.NOT_FOUND;
            // TODO: divide User & ItemNotFoundExceptions
            // TODO: add multiple missing item IDs handling (APIError.related)
        }

        return new ResponseEntity<>(responseBuilder.build(), status);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderReceipt>> updateOrder(
        @PathVariable String id,
        @RequestBody OrderInputDto orderInputData
    ) {
        var status = HttpStatus.OK;
        var orderId = UUID.fromString(id);
        var responseBuilder = new ApiResponse.Builder<OrderReceipt>();

        try {
            Order updatedOrder = service.updateOrder(orderId, orderInputData);
            OrderReceipt updatedReceipt = Orders.createReceiptFrom(updatedOrder);
            responseBuilder.setResponse(updatedReceipt);
        }
        catch (NotFoundException ex) {
            responseBuilder.setErrorMessage(ex.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseBuilder.build(), status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> cancelOrder(@PathVariable String id) {
        var status = HttpStatus.OK;
        var orderId = UUID.fromString(id);
        var responseBuilder = new ApiResponse.Builder<Boolean>();

        try {
            service.deleteOrder(orderId);
            responseBuilder.setResponse(true);
        }
        catch (NotFoundException ex) {
            responseBuilder.setErrorMessage(ex.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseBuilder.build(), status);
    }
}
