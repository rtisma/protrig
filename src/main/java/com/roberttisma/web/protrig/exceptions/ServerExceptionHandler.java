/*
 * Copyright (c) 2018. Ontario Institute for Cancer Research
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.roberttisma.web.protrig.exceptions;

import com.roberttisma.web.protrig.model.ProcessError;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.icgc.dcc.common.core.util.Splitters.NEWLINE;
import static org.icgc.dcc.common.core.util.stream.Collectors.toImmutableList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ServerExceptionHandler {

  private static final String AMPERSAND = "&";
  private static final String QUESTION_MARK= "?";

  @ExceptionHandler(ProcessException.class)
  public ResponseEntity<String> handleProcessException(HttpServletRequest request, HttpServletResponse response, ProcessException ex){
    val p = new ProcessError();
    p.setMessage(ex.getMessage());
    p.setRequestUrl(generateRequestUrlWithParams(request));
    p.setProcessName(ex.getProcessName());
    p.setHttpStatus(INTERNAL_SERVER_ERROR);
    log.error(p.toPrettyJson());
    return p.getResponseEntity();
  }

  private static List<String> getFullStackTraceList(Throwable t){
    return NEWLINE.splitToList(getStackTraceAsString(t))
        .stream()
        .map(String::trim)
        .collect(toImmutableList());
  }

  private static String generateRequestUrlWithParams(HttpServletRequest request){
    val requestUrl = request.getRequestURL().toString();
    val paramEntries = request.getParameterMap().entrySet();
    if (paramEntries.size() > 0){
      val params = paramEntries.stream()
          .map(x -> createUrlParams(x.getKey(), x.getValue()))
          .flatMap(Collection::stream)
          .collect(joining(AMPERSAND));
      return requestUrl+QUESTION_MARK+params;
    }
    return requestUrl;
  }

  private static List<String> createUrlParams(String key, String ... values){
    return stream(values)
        .map(x -> createUrlParam(key, x))
        .collect(toImmutableList());
  }

  private static String createUrlParam(String key, String value){
    return format("%s=%s", key, value);
  }


}
