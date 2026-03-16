package com.isai.demobackend.services.auth;

import com.isai.demobackend.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
    implements AuthService {
  private final UsuarioRepository usuarioRepository;

  @Override
  public Boolean hasUserWithEmail(String email) {
    return usuarioRepository.findFirstByEmail(email).isPresent();
  }
}
