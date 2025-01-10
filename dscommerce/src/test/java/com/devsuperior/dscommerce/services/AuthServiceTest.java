package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.factory.UserFactory;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

  @InjectMocks
  private AuthService service;

  @Mock
  private UserService userService;

  private User userAdmin;
  private User userClient;

  @BeforeEach
  void setUp() {
    userAdmin = UserFactory.createAdminUser();
    userClient = UserFactory.createClientUser();
  }

  @Test
  void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
    Mockito.when(userService.authenticated()).thenReturn(userAdmin);
    Assertions.assertDoesNotThrow(()-> service.validateSelfOrAdmin(userAdmin.getId()));
  }

  @Test
  void validateSelfOrAdminShouldThrowForbiddenExceptionWhenOrderIsNotSelf() {
    Mockito.when(userService.authenticated()).thenReturn(userClient);
    Assertions.assertThrows(ForbiddenException.class, ()-> service.validateSelfOrAdmin(userAdmin.getId()));
  }

  @Test
  void validateSelfOrAdminShouldThrowForbiddenExceptionWhenOrderIsSelf() {
    Mockito.when(userService.authenticated()).thenReturn(userClient);
    Assertions.assertDoesNotThrow(()-> service.validateSelfOrAdmin(userClient.getId()));
  }
}
