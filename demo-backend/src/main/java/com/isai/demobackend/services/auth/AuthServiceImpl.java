package com.isai.demobackend.services.auth;

import com.isai.demobackend.dtos.SignupRequest;
import com.isai.demobackend.dtos.UsuarioDTO;
import com.isai.demobackend.entities.Usuario;
import com.isai.demobackend.enums.UsuarioRol;
import com.isai.demobackend.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  @Override
  public UsuarioDTO crearUsuario(SignupRequest signupRequest) {
    Usuario usuario = new Usuario();
    usuario.setEmail(signupRequest.getEmail());
    usuario.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
    usuario.setNombres(signupRequest.getNombres());
    usuario.setApellidos(signupRequest.getApellidos());
    usuario.setRol(UsuarioRol.USER_ROLE);
    Usuario usuarioCreado = usuarioRepository.save(usuario);
    return usuarioCreado.getUsuarioDTO();
  }
}
