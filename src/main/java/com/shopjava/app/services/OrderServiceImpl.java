
package com.shopjava.app.services;

import com.shopjava.app.exceptions.ItemNotFoundException;
import com.shopjava.app.exceptions.OrderNotFoundException;
import com.shopjava.app.exceptions.UserNotFoundException;
import com.shopjava.app.models.Order;
import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.User;
import com.shopjava.app.models.repositories.OrdersRepository;
import com.shopjava.app.models.orders.OrderInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    ShopItemService itemService;

    @Override
    public Order createOrder(OrderInputDto newOrder) throws ItemNotFoundException, UserNotFoundException {
        var order = new Order();

        // id, date -> auto

        // items
        for (UUID itemId : newOrder.getItemIds()) {
            Optional<ShopItem> shopItem =
                itemService.getItemById(itemId);

            if (shopItem.isPresent()) {
                order.addItem(shopItem.get());
            }
            else {
                throw new ItemNotFoundException(itemId);
            }
        }

        // user
        UUID inputUserId = newOrder.getUserId();
        Optional<User> user = userService.getUserById(inputUserId);
        if (user.isPresent()) {
            order.setUser(user.get());
        }
        else {
            throw new UserNotFoundException(inputUserId);
        }

        // comment
        order.setComment(newOrder.getComment());

        return repository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public Order getOrderById(UUID orderId) throws OrderNotFoundException {
        Optional<Order> order = repository.findById(orderId);

        if (order.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        return order.get();
    }

    @Override
    public Order updateOrder(UUID orderId, OrderInputDto updatedEntity) throws OrderNotFoundException, UserNotFoundException, ItemNotFoundException {
        Order entityToUpdate = getOrderById(orderId);

        // check: user id: passed, exists
        UUID userId = updatedEntity.getUserId();
        if (userId == null || !userService.isUserExists(userId)) {
            throw new UserNotFoundException(updatedEntity.getUserId());
        }

        // TODO: check: item ids: exists, updated
        // TODO: implement updates array instead of list replace
        List<UUID> newItemIds = updatedEntity.getItemIds();
        if (newItemIds != null) {
            List<ShopItem> newItemsList = new ArrayList<>();
            for (UUID itemId : newItemIds) {
                Optional<ShopItem> item = itemService.getItemById(itemId);
                if (item.isPresent()) {
                    newItemsList.add(item.get());
                }
                else {
                    throw new ItemNotFoundException(itemId);
                }
            }
            entityToUpdate.setItems(newItemsList);
        }

        // check: comment: updated
        String newComment = updatedEntity.getComment();
        if (newComment != null) {
            entityToUpdate.setComment(newComment);
        }

        // TODO: update createdAt

        return repository.save(entityToUpdate);
    }

    @Override
    public void deleteOrder(UUID orderId) throws OrderNotFoundException {
        if (repository.existsById(orderId)) {
            repository.deleteById(orderId);
        }
        else {
            throw new OrderNotFoundException(orderId);
        }
    }
}
