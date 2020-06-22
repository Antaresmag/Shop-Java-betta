
package com.okunderenko.app.utils;

import com.okunderenko.app.models.Order;
import com.okunderenko.app.models.ShopItem;
import com.okunderenko.app.models.orders.OrderReceipt;

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
