package com.devsuperior.dscommerce.model;

import com.devsuperior.dscommerce.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String name;
  @Column(unique = true)
  private String email;
  @Column
  private String phone;
  @Column
  private LocalDate birthDate;
  @Column
  private String password;
  @OneToMany(mappedBy = "client")
  @Setter(AccessLevel.NONE)
  private List<Order> orders = new ArrayList<>();
  @ManyToMany
  @JoinTable(name = "tb_user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String name, String email, String password) {
    this.email = name;
    this.email = email;
    this.password = password;
  }

  public User(Long id, String name, String email, String phone, LocalDate birthDate, String password, Set<Role> roles) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.birthDate = birthDate;
    this.password = password;
    this.roles = roles;
  }

  public User(UserDTO u) {
    this.id = u.getId();
    this.name = u.getName();
    this.email = u.getEmail();
    this.phone = u.getPhone();
    this.birthDate = u.getBirthDate();
  }

  public void addRole(Role role) {
    this.roles.add(role);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

   public boolean hasRole(String role) {
     for (Role r : this.getRoles()) {
       if (r.getAuthority().equals(role)) {
         return true;
       }
     }
     return false;
   }

}
