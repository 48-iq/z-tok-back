package dev.ztok.back.security;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtClaims {

  private final List<String> authorities;

  private final List<String> roles;

  private final String username;

  private final String userId;
}

