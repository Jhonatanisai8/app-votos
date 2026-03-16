package com.isai.demobackend.services.auth;

import com.isai.demobackend.dtos.SignupRequest;
import com.isai.demobackend.dtos.UsuarioDTO;

public interface AuthService {
  Boolean hasUserWithEmail(String email);

  UsuarioDTO crearUsuario(SignupRequest signupRequest);
}
