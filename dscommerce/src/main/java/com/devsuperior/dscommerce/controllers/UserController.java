package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
  @GetMapping("/me")
  public ResponseEntity<UserDTO> getMe() {
    UserDTO userDTO = this.userService.getMe();

    return ResponseEntity.ok(userDTO);
  }

}
