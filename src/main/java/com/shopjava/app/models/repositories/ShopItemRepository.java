
package com.shopjava.app.models.repositories;

import com.shopjava.app.models.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, UUID> {

}
