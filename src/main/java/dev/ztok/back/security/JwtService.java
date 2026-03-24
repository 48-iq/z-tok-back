package dev.ztok.back.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.ztok.back.exceptions.AppException;
import dev.ztok.back.exceptions.AppExceptionType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final RsaKeyHolder rsaKeyHolder;

  @Value("${app.jwt.issuer}")
  private String issuer;

  @Value("${app.jwt.audience}")
  private String audience;

  @Value("${app.jwt.subject}")
  private String subject;

  public String generate(JwtClaims claims) {

    try {
      Algorithm algorithm = Algorithm.RSA256(
          rsaKeyHolder.getPublicKey(),
          rsaKeyHolder.getPrivateKey());

      String token = JWT.create()
          .withClaim("authorities", claims.getAuthorities())
          .withClaim("roles", claims.getRoles())
          .withClaim("userId", claims.getUserId())
          .withClaim("username", claims.getUsername())

          .withIssuer(issuer)
          .withAudience(audience)
          .withSubject(subject)

          .sign(algorithm);

      return token;
    } catch (JWTCreationException e) {
      throw new AppException(AppExceptionType.JWT_GENERATION_ERROR);
    }
  }

  public void verify(String token) {

    Algorithm algorithm = Algorithm.RSA256(
        rsaKeyHolder.getPublicKey(),
        rsaKeyHolder.getPrivateKey());

    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .withSubject(subject)
        .withAudience(audience)
        .withClaimPresence("authorities")
        .withClaimPresence("roles")
        .withClaimPresence("userId")
        .withClaimPresence("username")
        .build();

    verifier.verify(token);
  }

  public JwtClaims parse(String token) {

    DecodedJWT decodedJWT = JWT.decode(token);

    return JwtClaims.builder()
        .authorities(decodedJWT.getClaim("authorities").asList(String.class))
        .roles(decodedJWT.getClaim("roles").asList(String.class))
        .username(decodedJWT.getClaim("username").asString())
        .userId(decodedJWT.getClaim("userId").asString())
        .build();
  }
}
