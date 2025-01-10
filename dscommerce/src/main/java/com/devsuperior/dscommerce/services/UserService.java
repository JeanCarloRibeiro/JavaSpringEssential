package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.model.Role;
import com.devsuperior.dscommerce.model.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.respositories.UserRepository;
import com.devsuperior.dscommerce.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthenticationUtils authenticationUtils;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    List<UserDetailsProjection> result = this.userRepository.searchUserAndRolesByEmail(email);
    if (result.isEmpty()) {
      throw new UsernameNotFoundException("Email not found");
    }
    User user = new User(result.get(0).getUserName(), email, result.get(0).getPassword());
    for (UserDetailsProjection r : result) {
      user.addRole(new Role(r.getRoleId(), r.getAuthority()));
    }
    return user;
  }

  protected User authenticated() {
    try {
      String username = authenticationUtils.getLoggedUserName();
      Optional<User> result = userRepository.findByEmail(username);
      if (result.isEmpty()) {
        throw new UsernameNotFoundException("Email not found...");
      }
      return result.get();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email not found...");
    }
  }

  @Transactional(readOnly = true)
  public UserDTO getMe() {
    User user = authenticated();
    return new UserDTO(user);
  }

}
