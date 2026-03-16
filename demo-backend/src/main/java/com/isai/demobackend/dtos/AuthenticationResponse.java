package com.isai.demobackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String jwtToken;
  private String username;
}
