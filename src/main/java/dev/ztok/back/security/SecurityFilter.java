package dev.ztok.back.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.ztok.back.exceptions.AppException;
import dev.ztok.back.exceptions.AppExceptionType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    
    String authHeader = request.getHeader("Authorization");
    
    if (authHeader == null || !authHeader.startsWith("Bearer "))
      throw new AppException(AppExceptionType.INVALID_ACCESS_TOKEN);
  
    String accessToken = authHeader.substring("Bearer ".length());

    try {
      jwtService.verify(accessToken);
      
      JwtClaims claims = jwtService.parse(accessToken);

      SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(claims));

    } catch (Exception e) {
      throw new AppException(AppExceptionType.INVALID_ACCESS_TOKEN);
    }

    filterChain.doFilter(request, response);
  }

}
