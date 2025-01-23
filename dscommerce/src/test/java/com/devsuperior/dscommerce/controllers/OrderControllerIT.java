package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.enums.OrderStatus;
import com.devsuperior.dscommerce.factory.ProductFactory;
import com.devsuperior.dscommerce.factory.UserFactory;
import com.devsuperior.dscommerce.model.Order;
import com.devsuperior.dscommerce.model.OrderItem;
import com.devsuperior.dscommerce.model.OrderItemPk;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TokenUtil tokenUtil;

  @Autowired
  private ObjectMapper mapper;

  Product product;
  private Order order;
  private OrderDTO orderDTO;

  private String adminToken, clientToken, invalidToken;
  private Long existingOrderId, nonExistingOrderId, dependentOrderId;

  @BeforeEach
  void setUp() throws Exception {
    mapper = new ObjectMapper();

    adminToken = tokenUtil.obtainAccessToken(mockMvc, "alex@gmail.com", "123456");
    clientToken = tokenUtil.obtainAccessToken(mockMvc, "maria@gmail.com", "123456");
    invalidToken = adminToken + "xpto";

    User adminUser = UserFactory.createAdminUser();
    order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, adminUser, null);

    product = ProductFactory.createProduct();
    OrderItem orderItem = new OrderItem(new OrderItemPk(order, product), 2, 200.00);

    existingOrderId = 1L;
    nonExistingOrderId = 100L;
    dependentOrderId = 3L;

  }

  @Test
  void findAllShouldReturnOrderDTOWhenIdExistsAndAdminLogged() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", existingOrderId)
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.id").value(existingOrderId));
  }

  @Test
  void findAllShouldReturnOrderDTOWhenIdExistsAndClientLogged() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", existingOrderId)
                    .header("Authorization", "Bearer " + clientToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
  }

  @Test
  void findAllShouldReturnForbiddenWhenIdExistsAndClientLoggedAndOrderDoesNotBelongUser() throws Exception {
    Long otherOtherId = 2L;

    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", otherOtherId)
                    .header("Authorization", "Bearer " + clientToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isForbidden());
  }

  @Test
  void findAllShouldReturnNotFoundWhenIdNotExistsAdminLogged() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", nonExistingOrderId)
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isNotFound());
  }

  @Test
  void findAllShouldReturnNotFoundWhenIdNotExistsClientLogged() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", nonExistingOrderId)
                    .header("Authorization", "Bearer " + clientToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isNotFound());
  }

  @Test
  void findAllShouldReturnUnauthorizedWhenAdminOrClientNotLogged() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/orders/{id}", nonExistingOrderId)
                    .header("Authorization", "Bearer " + invalidToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnauthorized());
  }


}
