
package com.okunderenko.app.services;

import com.okunderenko.app.models.ShopItem;
import com.okunderenko.app.models.repositories.ShopItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopItemServiceImpl implements ShopItemService {
    @Autowired
    ShopItemRepository repository;

    @Override
    public ShopItem createItem(ShopItem newItem) {
        return repository.save(newItem);
    }

    @Override
    public List<ShopItem> getAllItems() {
        return repository.findAll();
    }

    @Override
    public Optional<ShopItem> getItemById(UUID itemId) {
        return repository.findById(itemId);
    }

    @Override
    public boolean updateItem(UUID itemId, ShopItem updatedEntity) {
        Optional<ShopItem> targetItem = getItemById(itemId);

        if (targetItem.isPresent()) {
            ShopItem entityToUpdate = targetItem.get();

            if (updatedEntity.getTitle() != null) {
                entityToUpdate.setTitle(updatedEntity.getTitle());
            }
            if (updatedEntity.getDescription() != null) {
                entityToUpdate.setDescription(updatedEntity.getDescription());
            }
            if (updatedEntity.getWeight() != null) {
                entityToUpdate.setWeight(updatedEntity.getWeight());
            }
            if (updatedEntity.getPrice() != null) {
                entityToUpdate.setPrice(updatedEntity.getPrice());
            }

            repository.save(entityToUpdate);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteItem(UUID itemId) {
        Optional<ShopItem> targetItem = getItemById(itemId);

        if (targetItem.isPresent()) {
            repository.deleteById(itemId);
            return true;
        }
        else {
            return false;
        }
    }
}
