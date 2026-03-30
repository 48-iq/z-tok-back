package dev.ztok.back.exceptions;

public class AppException extends RuntimeException {

  private AppExceptionType exceptionType;

  public AppException(AppExceptionType exceptionType) {
    this.exceptionType = exceptionType;
  }

  @Override
  public String getMessage() {
    return exceptionType.getMessage();
  }

  public String getType() {
    return exceptionType.name();
  }

  public Integer getHttpCode() {
    return exceptionType.getHttpCode();
  }

}
