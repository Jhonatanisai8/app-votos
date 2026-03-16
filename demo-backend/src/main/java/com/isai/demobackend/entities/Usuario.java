package com.isai.demobackend.entities;

import com.isai.demobackend.dtos.UsuarioDTO;
import com.isai.demobackend.enums.UsuarioRol;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String nombres;
  private String apellidos;
  private UsuarioRol rol;

  public UsuarioDTO getUsuarioDTO() {
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    usuarioDTO.setId(id);
    usuarioDTO.setEmail(email);
    usuarioDTO.setNombres(nombres);
    usuarioDTO.setApellidos(apellidos);
    usuarioDTO.setUsuarioRol(rol);
    return usuarioDTO;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(rol.name()));
  }

  @Override
  public String getUsername() {
    return email;
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
}
