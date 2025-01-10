package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.enums.OrderStatus;
import com.devsuperior.dscommerce.model.Order;
import com.devsuperior.dscommerce.model.OrderItem;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.respositories.OrderItemRepository;
import com.devsuperior.dscommerce.respositories.OrderRepository;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductService productService;
  @Autowired
  private OrderItemRepository orderItemRepository;
  @Autowired
  private AuthService authService;

  @Transactional(readOnly = true)
  public OrderDTO findById(Long id) {
    Order order = this.orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found"));

    authService.validateSelfOrAdmin(order.getClient().getId());
    return new OrderDTO(order);
  }

  public OrderDTO saveOrder(OrderDTO request) {
    Order order = new Order();
    order.setMoment(Instant.now());
    order.setStatus(OrderStatus.WAITING_PAYMENT);
    order.setClient(this.userService.authenticated());

    for (OrderItemDTO itemDTO : request.getItems()) {
      Product product = this.productRepository.getReferenceById(itemDTO.getProductId());
      OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
      order.getItems().add(item);
    }
    Order orderSaved = this.orderRepository.save(order);
    this.orderItemRepository.saveAll(order.getItems());

    return new OrderDTO(orderSaved);

  }
}
