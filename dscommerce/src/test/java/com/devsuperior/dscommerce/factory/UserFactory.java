package com.devsuperior.dscommerce.factory;

import com.devsuperior.dscommerce.model.Role;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;

import java.time.LocalDate;
import java.util.Set;

public class UserFactory {

  public static User createClientUser() {
    return new User(1L, "Maria",
            "maria@gmail.com",
            "988888888",
            LocalDate.parse("2001-07-25"),
            "$2a$10$nHv5/XkPWxC5wHUe2/IqGuNZyMlY1nJ2PkUfU6EbW09IRZCWzTTgu",
            Set.of(new Role(1L, "ROLE_CLIENT")));
  }

  public static User createAdminUser() {
    return new User(2L, "Alex",
            "alex@gmail.com",
            "977777777",
            LocalDate.parse("1987-12-13"),
            "$2a$10$nHv5/XkPWxC5wHUe2/IqGuNZyMlY1nJ2PkUfU6EbW09IRZCWzTTgu",
            Set.of(new Role(1L, "ROLE_CLIENT"), new Role(2L, "ROLE_ADMIN")));
  }

  public static UserDetailsProjection createClientProjection() {
    return new UserDetailsProjection() {
      @Override
      public String getUserName() {
        return "maria@gmail.com";
      }
      @Override
      public String getPassword() {
        return "$2a$10$nHv5/XkPWxC5wHUe2/IqGuNZyMlY1nJ2PkUfU6EbW09IRZCWzTTgu";
      }
      @Override
      public Long getRoleId() {
        return 1L;
      }
      @Override
      public String getAuthority() {
        return "ROLE_CLIENT";
      }
    };
  }
  public static UserDetailsProjection createAdminProjection() {
    return new UserDetailsProjection() {
      @Override
      public String getUserName() {
        return "alex@gmail.com";
      }
      @Override
      public String getPassword() {
        return "$2a$10$nHv5/XkPWxC5wHUe2/IqGuNZyMlY1nJ2PkUfU6EbW09IRZCWzTTgu";
      }
      @Override
      public Long getRoleId() {
        return 2L;
      }
      @Override
      public String getAuthority() {
        return "ROLE_ADMIN";
      }
    };
  }

}
