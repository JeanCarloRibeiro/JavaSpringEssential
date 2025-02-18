package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  UserService userService;

  public void validateSelfOrAdmin(Long userId) {
    User user = this.userService.authenticated();
    if (user.hasRole("ROLE_ADMIN")) {
      return;
    }
    if (!user.getId().equals(userId)) {
      throw new ForbiddenException("Access Denied. Should be self or admin");
    }

  }
}
