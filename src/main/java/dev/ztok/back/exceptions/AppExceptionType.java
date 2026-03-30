package dev.ztok.back.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum AppExceptionType {

  // Ошибки связанные с безопасностью
  PERMISSION_DENIED("Недостаточно прав", HttpStatus.FORBIDDEN.value()),
  INVALID_CREDENTIALS("Неверные учетные данные", HttpStatus.UNAUTHORIZED.value()),
  INVALID_ACCESS_TOKEN("Неверный access токен", HttpStatus.UNAUTHORIZED.value()),
  INVALID_REFRESH_TOKEN("Неверный refresh токен", HttpStatus.UNAUTHORIZED.value()),

  // Ошибки связанные с сущностью роли
  ROLE_NOT_FOUND("Роль не найдена", HttpStatus.NOT_FOUND.value()),
  ROLE_ALREADY_EXISTS("Роль уже существует", HttpStatus.CONFLICT.value()),

  // Ошибки связанные с сущностью пользователь
  USER_NOT_FOUND("Пользователь не найден", HttpStatus.NOT_FOUND.value()),
  USER_ALREADY_EXISTS("Пользователь уже существует", HttpStatus.CONFLICT.value()),
  USER_HAS_BEEN_BLOCKED("Пользователь заблокирован", HttpStatus.FORBIDDEN.value()),

  // Ошибки связанные с сущностью видео
  VIDEO_NOT_FOUND("Видео не найдено", HttpStatus.NOT_FOUND.value()),
  VIDEO_HAS_BEEN_BLOCKED("Видео заблокировано", HttpStatus.FORBIDDEN.value()),

  // Системные ошибки
  UNKNOWN_SERVER_ERROR("Неизвестная ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR.value()),
  JWT_GENERATION_ERROR("Ошибка генерации JWT", HttpStatus.INTERNAL_SERVER_ERROR.value());

  private final String message;

  private final Integer httpCode;

  AppExceptionType(String message, Integer httpCode) {
    this.message = message;
    this.httpCode = httpCode;
  }

  AppExceptionType(String message) {
    this.message = message;
    this.httpCode = null;
  }
}
