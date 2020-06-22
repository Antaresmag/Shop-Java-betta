
package com.shopjava.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopjava.app.exceptions.OrderNotFoundException;
import com.shopjava.app.models.Order;
import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.User;
import com.shopjava.app.models.orders.OrderInputDto;
import com.shopjava.app.models.orders.OrderReceipt;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.services.OrderService;
import com.shopjava.app.services.ShopItemService;
import com.shopjava.app.services.UserService;
import com.shopjava.app.utils.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class ControllerOrdersTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShopItemService itemService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    private final ObjectMapper mapper = configureMapper();

    public ObjectMapper configureMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    public Order createTestOrder() throws OrderNotFoundException {
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        newUser.setId(UUID.randomUUID());

        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        newItem.setId(UUID.randomUUID());

        var newOrder = new Order();
        newOrder.setId(UUID.randomUUID());
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setItems(List.of(newItem));
        newOrder.setUser(newUser);
        newOrder.setComment("This is my first order");

        given(userService.getUserById(newUser.getId())).willReturn(Optional.of(newUser));
        given(itemService.getItemById(newItem.getId())).willReturn(Optional.of(newItem));
        given(orderService.getOrderById(newOrder.getId())).willReturn(newOrder);

        return newOrder;
    }

    public OrderInputDto createDtoFromOrder(Order order) {
        var orderInputDto = new OrderInputDto();
        orderInputDto.setUserId(order.getId());
        orderInputDto.setComment(order.getComment());

        List<UUID> itemIds = order.getItems().stream()
            .map(ShopItem::getId)
            .collect(Collectors.toList());
        orderInputDto.setItemIds(itemIds);

        return orderInputDto;
    }

    @Test
    public void getAllOrders() throws Exception {
        var newOrder = createTestOrder();
        var ordersList = List.of(newOrder, newOrder);

        var response = new ApiResponse<>(true, null, ordersList);
        String responseJson = mapper.writeValueAsString(response);

        given(orderService.getAllOrders()).willReturn(ordersList);

        mvc.perform(get("/orders/"))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void getOrderById() throws Exception {
        Order newOrder = createTestOrder();

        var response = new ApiResponse<>(true, null, newOrder);
        var responseJson = mapper.writeValueAsString(response);

        mvc.perform(get("/orders/" + newOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void createOrder() throws Exception {
        Order newOrder = createTestOrder();
        OrderReceipt receipt = Orders.createReceiptFrom(newOrder);
        ApiResponse<OrderReceipt> response =
            new ApiResponse<>(true, null, receipt);

        OrderInputDto orderInputDto = createDtoFromOrder(newOrder);

        String contentJson = mapper.writeValueAsString(orderInputDto);
        String responseJson = mapper.writeValueAsString(response);

        given(orderService.createOrder(orderInputDto)).willReturn(newOrder);

        mvc.perform(
            post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentJson))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void updateItem() throws Exception {
        Order newOrder = createTestOrder();
        OrderReceipt receipt = Orders.createReceiptFrom(newOrder);
        OrderInputDto orderInputDto = createDtoFromOrder(newOrder);

        var response = new ApiResponse<>(true, null, receipt);

        String contentJson = mapper.writeValueAsString(orderInputDto);
        String responseJson = mapper.writeValueAsString(response);

        given(orderService.updateOrder(newOrder.getId(), orderInputDto)).willReturn(newOrder);

        mvc.perform(
            post("/orders/" + newOrder.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentJson))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void deleteItem() throws Exception {
        var orderId = UUID.randomUUID();

        var response = new ApiResponse<>(true, null, true);
        String responseJson = mapper.writeValueAsString(response);

        mvc.perform(delete("/orders/" + orderId))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }
}
