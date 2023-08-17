package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.model.Role;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    List<UserDetailsProjection> result = this.userRepository.searchUserAndRolesByEmail(email);
    if (result.isEmpty()) {
      throw new UsernameNotFoundException("User notfound.....");
    }
    User user = new User(result.get(0).getUserName(), email, result.get(0).getPassword());
    for (UserDetailsProjection r : result) {
      user.addRole(new Role(r.getRoleId(), r.getAuthority()));
    }
    return user;
  }

  protected User authenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
    String username = jwtPrincipal.getClaim("username");
    Optional<User> result = userRepository.findByEmail(username);
    if (!result.isPresent()) {
      throw new UsernameNotFoundException("Email not found...");
    }
    return result.get();
  }

  @Transactional(readOnly = true)
  public UserDTO getMe() {
    User user = authenticated();
    return new UserDTO(user);
  }

}
