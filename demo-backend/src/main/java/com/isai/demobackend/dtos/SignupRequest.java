package com.isai.demobackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  private String email;
  private String password;
  private String nombres;
  private String apellidos;

}
