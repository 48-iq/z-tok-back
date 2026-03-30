package dev.ztok.back.security;

import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthentication implements Authentication {

  private Boolean authenticated;

  private JwtClaims claims;

  public JwtAuthentication(JwtClaims claims) {
    this.claims = claims;
    this.authenticated = true;
  }

  @Override
  public String getName() {
    return claims.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return claims
        .getAuthorities()
        .stream()
        .map(a -> new SimpleGrantedAuthority(a))
        .toList();
  }

  @Override
  public @Nullable Object getCredentials() {
    return null;
  }

  @Override
  public @Nullable Object getDetails() {
    return claims;
  }

  @Override
  public @Nullable Object getPrincipal() {
    return claims.getUserId();
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

}
