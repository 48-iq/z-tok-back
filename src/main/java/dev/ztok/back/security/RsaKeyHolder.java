package dev.ztok.back.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// хранилище ключей для выпуска и валидации JWT токенов
public interface RsaKeyHolder {
  public RSAPublicKey getPublicKey();

  public RSAPrivateKey getPrivateKey();
}
