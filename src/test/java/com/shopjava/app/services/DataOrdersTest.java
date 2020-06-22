
package com.shopjava.app.services;

import com.shopjava.app.exceptions.ItemNotFoundException;
import com.shopjava.app.exceptions.OrderNotFoundException;
import com.shopjava.app.exceptions.UserNotFoundException;
import com.shopjava.app.models.Order;
import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.User;
import com.shopjava.app.models.orders.OrderInputDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({OrderServiceImpl.class, UserServiceImpl.class, ShopItemServiceImpl.class})
public class DataOrdersTest {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ShopItemService itemService;

    public Order createAndSaveTestOrder() throws UserNotFoundException, ItemNotFoundException {
        // create user
        User savedUser = userService.createUser(new User(
                "Alex", "Tester",
                "alex.tester@gmail.com",
                "9bba5c53"
        ));

        // create item
        ShopItem savedItem = itemService.createItem(new ShopItem(
                "test item",
                "test description",
                0.65, 196d
        ));

        var newOrder = new OrderInputDto();
        newOrder.setUserId(savedUser.getId());
        newOrder.setItemIds(List.of(savedItem.getId()));
        newOrder.setComment("This is my first order");

        return orderService.createOrder(newOrder);
    }

    @Test
    public void createAndSave() throws UserNotFoundException, ItemNotFoundException, OrderNotFoundException {
        var savedOrder = createAndSaveTestOrder();

        Order dbOrder = orderService.getOrderById(savedOrder.getId());
        assertThat(dbOrder.getId()).isEqualTo(savedOrder.getId());
    }

    @Test
    public void getAllItems() throws UserNotFoundException, ItemNotFoundException {
        createAndSaveTestOrder();

        List<Order> allOrders = orderService.getAllOrders();
        assertThat(allOrders.isEmpty()).isFalse();
    }

    @Test
    public void getOrderById() throws UserNotFoundException, ItemNotFoundException, OrderNotFoundException {
        var savedOrder = createAndSaveTestOrder();

        orderService.getOrderById(savedOrder.getId());
    }

    @Test
    public void updateOrder() throws UserNotFoundException, ItemNotFoundException, OrderNotFoundException {
        var savedOrder = createAndSaveTestOrder();

        // create item
        var additionalItem = itemService.createItem(new ShopItem(
            "one extra item",
            "test description 2",
            0.74, 200d
        ));

        var updatedEntity = new OrderInputDto();
        updatedEntity.setUserId(savedOrder.getUser().getId());
        var newComment = "My another comment";
        updatedEntity.setComment(newComment);

        var newItemIdsList = List.of(
            savedOrder.getItems().get(0).getId(),
            additionalItem.getId()
        );
        updatedEntity.setItemIds(newItemIdsList);

        Order updatedOrder =
            orderService.updateOrder(savedOrder.getId(), updatedEntity);

        assertThat(updatedOrder.getComment()).isEqualTo(newComment);

        List<ShopItem> orderItems = new ArrayList<>();
        newItemIdsList.forEach(itemId -> {
            Optional<ShopItem> item = itemService.getItemById(itemId);
            assertThat(item.isPresent()).isTrue();
            orderItems.add(item.get());
        });
        assertThat(updatedOrder.getItems()).isEqualTo(orderItems);
    }

    @Test(expected = OrderNotFoundException.class)
    public void deleteOrder() throws UserNotFoundException, ItemNotFoundException, OrderNotFoundException {
        var savedOrder = createAndSaveTestOrder();

        orderService.deleteOrder(savedOrder.getId());
        orderService.getOrderById(savedOrder.getId());
    }
}
