
package com.shopjava.app.utils;

import com.shopjava.app.models.Order;
import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.orders.OrderReceipt;

public class Orders {
    public static OrderReceipt createReceiptFrom(Order order) {
        double totalPrice = order.getItems().stream().mapToDouble(ShopItem::getPrice).sum();
        return new OrderReceipt.Builder()
            .setOrderId(order.getId())
            .setCreatedAt(order.getCreatedAt())
            .setItems(order.getItems())
            .setQuantity(order.getItems().size())
            .setTotalPrice(totalPrice)
            .setComment(order.getComment()).build();
    }
}
