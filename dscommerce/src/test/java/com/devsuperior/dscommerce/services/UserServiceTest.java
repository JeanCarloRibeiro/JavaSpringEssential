package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.factory.UserFactory;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.respositories.UserRepository;
import com.devsuperior.dscommerce.utils.AuthenticationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService service;
  @Mock
  private UserRepository repository;
  @Mock
  private AuthenticationUtils authenticationUtils;

  private String existingUsername;
  private String nonExistingUsername;

  private User user;
  private UserDetailsProjection userClient;
  private UserDetailsProjection userAdmin;

  @BeforeEach
  void setUp() {
    user = UserFactory.createAdminUser();
    userClient =  UserFactory.createClientProjection();
    userAdmin =  UserFactory.createAdminProjection();
    existingUsername = "alex@gmail.com";
    nonExistingUsername = "user@gmail";

    Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(List.of(userAdmin));
    Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());

    Mockito.when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
    Mockito.when(repository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
  }

  @Test
  void loadUserByUsernameShouldReturnUserWhenUserExists() {
    UserDetails result = service.loadUserByUsername(existingUsername);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getUsername(), existingUsername);
  }

  @Test
  void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenWhenUserDoesNotExist() {
    Assertions.assertThrows(UsernameNotFoundException.class, ()-> service.loadUserByUsername(nonExistingUsername));
  }

  @Test
  void authenticatedShouldReturnUserWhenUserExists() {
    Mockito.when(authenticationUtils.getLoggedUserName()).thenReturn(existingUsername);

    User authenticated = service.authenticated();
    Assertions.assertNotNull(authenticated);
    Assertions.assertEquals(authenticated.getUsername(), existingUsername);
  }

  @Test
  void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
    Mockito.when(authenticationUtils.getLoggedUserName()).thenReturn(nonExistingUsername);
    Assertions.assertThrows(UsernameNotFoundException.class, ()-> service.authenticated());
  }

  @Test
  void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExistWithClassCast() {
    Mockito.doThrow(ClassCastException.class).when(authenticationUtils).getLoggedUserName();
    Assertions.assertThrows(UsernameNotFoundException.class, ()-> service.authenticated());
  }

  @Test
  void getMeShouldReturnUserDTOWhenUserAuthenticated() {
    UserService spyUserService = Mockito.spy(UserService.class);
    Mockito.doReturn(user).when(spyUserService).authenticated();

    UserDTO result = spyUserService.getMe();
    Assertions.assertNotNull(result);
  }

  @Test
  void getMeShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExistWithClassCast() {
    UserService spyUserService = Mockito.spy(UserService.class);
    Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).getMe();
    Assertions.assertThrows(UsernameNotFoundException.class, ()-> service.authenticated());
  }

}
