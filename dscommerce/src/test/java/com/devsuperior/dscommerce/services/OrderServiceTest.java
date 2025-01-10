package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.factory.OrderFactory;
import com.devsuperior.dscommerce.factory.ProductFactory;
import com.devsuperior.dscommerce.factory.UserFactory;
import com.devsuperior.dscommerce.model.Order;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.respositories.OrderItemRepository;
import com.devsuperior.dscommerce.respositories.OrderRepository;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

  @InjectMocks
  OrderService service;

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private AuthService authService;
  @Mock
  private UserService userService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private OrderItemRepository orderItemRepository;

  private Order order;
  private Long existingOrderId;
  private Long nonExistingOrderId;
  private User adminUser;
  private User clientUser;
  private Product product;
  private Long existingProductId;
  private Long nonExistingProductId;

  @BeforeEach
  void setUp() throws Exception {
    adminUser = UserFactory.createAdminUser();
    clientUser = UserFactory.createClientUser();

    order = OrderFactory.createOrder(adminUser);
    existingOrderId = 1L;
    nonExistingOrderId = 2L;

    existingProductId = 1L;
    nonExistingProductId = 2L;

    product = ProductFactory.createProduct();

    Mockito.when(orderRepository.findById(existingOrderId)).thenReturn(Optional.of(order));
    Mockito.when(orderRepository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

    Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
    Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);

    Mockito.when(orderRepository.save(any())).thenReturn(order);
    Mockito.when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>(order.getItems()));
  }

  @Test
  void findByIdShouldReturnOrderDTOWhenOrderIdExistsAndAdminLogged() {
    Mockito.doNothing().when(authService).validateSelfOrAdmin(anyLong());

    OrderDTO result = service.findById(existingOrderId);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), existingOrderId);
  }

  @Test
  void findByIdShouldReturnOrderDTOWhenOrderIdExistsAndSelfClientLogged() {
    Mockito.doNothing().when(authService).validateSelfOrAdmin(anyLong());

    OrderDTO result = service.findById(existingOrderId);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), existingOrderId);
  }

  @Test
  void findByIdShouldThrowsForbiddenExceptionWhenOrderIdExistsAndOtherClientLogged() {
    Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(anyLong());
    Assertions.assertThrows(ForbiddenException.class, ()-> service.findById(existingOrderId));
  }

  @Test
  void findByIdShouldThrowsResourceNotFoundExceptionWhenOrderIdDoesNotExist() {
    Mockito.doNothing().when(authService).validateSelfOrAdmin(anyLong());
    Assertions.assertThrows(ResourceNotFoundException.class, ()-> service.findById(nonExistingOrderId));
  }

  @Test
  void saveOrderShouldOrderDTOWhenUserLogged() {
    Mockito.when(userService.authenticated()).thenReturn(adminUser);

    OrderDTO result = service.saveOrder(new OrderDTO(order));
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), existingOrderId);
  }

  @Test
  void saveOrderShouldThrowUsernameNotFoundExceptionWhenUserNotLogged() {
    Mockito.doThrow(UsernameNotFoundException.class).when(userService).authenticated();

    order.setClient(new User());
    OrderDTO orderDTO = new OrderDTO(order);

    Assertions.assertThrows(UsernameNotFoundException.class, ()-> service.saveOrder(orderDTO));
  }

}
