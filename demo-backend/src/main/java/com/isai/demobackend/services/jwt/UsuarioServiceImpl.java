package com.isai.demobackend.services.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
  @Override
  public UserDetailsService userDetailsService() {
    return null;
  }
}
