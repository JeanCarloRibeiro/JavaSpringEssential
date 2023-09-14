package com.devsuperior.dscommerce.respositories;

import com.devsuperior.dscommerce.model.OrderItem;
import com.devsuperior.dscommerce.model.OrderItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {

}
