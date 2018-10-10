package com.roberttisma.web.protrig.exceptions;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@Getter
public class ProcessException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final String processName;

  public ProcessException(String processName, String message, HttpStatus httpStatus) {
    super(message);
    this.processName = processName;
    this.httpStatus = httpStatus;

  }

  public ProcessException(String processName, String message, HttpStatus httpStatus, Throwable cause) {
    super(message, cause);
    this.processName = processName;
    this.httpStatus = httpStatus;
  }

  public static void checkProcessServer(boolean expression, @NonNull String processName,
     @NonNull HttpStatus httpStatus,  @NonNull  String formattedMessage, Object ... args){
    if (!expression){
      throw new ProcessException(processName, format(formattedMessage, args), httpStatus);
    }
  }


}
