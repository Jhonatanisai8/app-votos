package com.isai.demobackend.dtos;

import com.isai.demobackend.enums.UsuarioRol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
  private Long id;
  private String email;
  private String nombres;
  private String apellidos;
  private UsuarioRol usuarioRol;
}
