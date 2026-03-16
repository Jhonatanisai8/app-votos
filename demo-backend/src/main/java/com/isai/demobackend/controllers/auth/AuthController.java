package com.isai.demobackend.controllers.auth;

import com.isai.demobackend.dtos.AuthenticationResponse;
import com.isai.demobackend.dtos.SignupRequest;
import com.isai.demobackend.dtos.UsuarioDTO;
import com.isai.demobackend.services.auth.AuthService;
import com.isai.demobackend.services.jwt.UsuarioService;
import com.isai.demobackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private final UsuarioService usuarioService;

  private final JWTUtil jwtUtil;

  @RequestMapping(method = RequestMethod.POST, path = "/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
    try {
      if (authService.hasUserWithEmail(signupRequest.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Collections.singletonMap("error", "Ya existe dicho Usuario"));
      }
      UsuarioDTO usuarioCreado = authService.crearUsuario(signupRequest);
      if (usuarioCreado == null) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "Registro de usuario fallida"));
      }
      UserDetails userDetails = usuarioService.userDetailsService()
          .loadUserByUsername(usuarioCreado.getEmail());
      String jwt = jwtUtil.generateToken(userDetails, usuarioCreado.getId());
      AuthenticationResponse authenticationResponse = new AuthenticationResponse();
      authenticationResponse.setJwtToken(jwt);
      authenticationResponse.setUsername(usuarioCreado.getNombres() + " " + usuarioCreado.getApellidos());
      return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Collections.singletonMap("error", e.getMessage()));
    }
  }
}
