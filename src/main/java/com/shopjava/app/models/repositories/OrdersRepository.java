
package com.shopjava.app.models.repositories;

import com.shopjava.app.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Order, UUID> {

}
