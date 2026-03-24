package dev.ztok.back.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
  private String message;
  private String type;
  private Integer httpCode;

}
