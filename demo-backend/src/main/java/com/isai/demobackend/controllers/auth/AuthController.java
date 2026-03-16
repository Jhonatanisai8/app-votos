package com.isai.demobackend.controllers.auth;

import com.isai.demobackend.dtos.AuthenticacionRequest;
import com.isai.demobackend.dtos.AuthenticationResponse;
import com.isai.demobackend.dtos.SignupRequest;
import com.isai.demobackend.dtos.UsuarioDTO;
import com.isai.demobackend.entities.Usuario;
import com.isai.demobackend.repositories.UsuarioRepository;
import com.isai.demobackend.services.auth.AuthService;
import com.isai.demobackend.services.jwt.UsuarioService;
import com.isai.demobackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

  private final AuthService authService;

  private final UsuarioService usuarioService;

  private final JWTUtil jwtUtil;

  private final AuthenticationManager authenticationManager;

  private final UsuarioRepository usuarioRepository;


  @RequestMapping(method = RequestMethod.POST, path = "/signup")
  public ResponseEntity<?> signupUsuario(@RequestBody SignupRequest signupRequest) {
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

  @RequestMapping(method = RequestMethod.POST, path = "/login")
  public ResponseEntity<?> loginUsuario(@RequestBody AuthenticacionRequest authenticacionRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticacionRequest.getEmail(), authenticacionRequest.getPassword())
      );

      UserDetails userDetails = usuarioService.userDetailsService().
          loadUserByUsername(authenticacionRequest.getEmail());
      Optional<Usuario> optionalUsuario = usuarioRepository.findFirstByEmail(userDetails.getUsername());
      if (optionalUsuario.isPresent()) {
        Usuario usuario = optionalUsuario.get();
        String jwt = jwtUtil.generateToken(userDetails, usuario.getId());

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwtToken(jwt);
        authenticationResponse.setUsername(usuario.getNombres() + " " + usuario.getApellidos());
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonMap("error", "Usuario no encontrado"));
    } catch (org.springframework.security.authentication.BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Correo o contraseña incorrectos"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Collections.singletonMap("error","Error interno de servidor "+ e.getMessage()));
    }
  }
}
