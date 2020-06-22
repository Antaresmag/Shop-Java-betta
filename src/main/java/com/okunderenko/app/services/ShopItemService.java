
package com.okunderenko.app.services;

import com.okunderenko.app.models.ShopItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopItemService {
    ShopItem createItem(ShopItem newItem);

    List<ShopItem> getAllItems();

    Optional<ShopItem> getItemById(UUID itemId);

    boolean updateItem(UUID itemId, ShopItem updatedEntity);

    boolean deleteItem(UUID itemId);
}
