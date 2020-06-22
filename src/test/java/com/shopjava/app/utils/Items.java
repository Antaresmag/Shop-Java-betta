
package com.shopjava.app.utils;

import com.shopjava.app.models.ShopItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Items {
    private static final Random random = new Random();

    public static List<ShopItem> generateItemsList() {
        return generateItemsList(10);
    }

    public static List<ShopItem> generateItemsList(int count) {
        var list = new ArrayList<ShopItem>();

        for (int i = 0; i < count; i++) {
            var newItem = new ShopItem();
            newItem.setId(UUID.randomUUID());
            newItem.setTitle(String.format("title %d", i));
            newItem.setDescription(String.format("desc %d", i));
            newItem.setWeight(random.nextDouble());
            newItem.setPrice(random.nextDouble() * 100);

            list.add(newItem);
        }

        return list;
    }
}
