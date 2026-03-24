package dev.ztok.back.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ExceptionDto defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    AppException appException = null;
    if (e instanceof AppException) {
      appException = (AppException) e;
    } else {
      appException = new AppException(AppExceptionType.UNKNOWN_SERVER_ERROR);
    }

    return new ExceptionDto(
        appException.getMessage(),
        appException.getType(),
        appException.getHttpCode());
  }
}
