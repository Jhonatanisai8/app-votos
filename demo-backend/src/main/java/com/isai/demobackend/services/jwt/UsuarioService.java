package com.isai.demobackend.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {
  UserDetailsService userDetailsService();
}
