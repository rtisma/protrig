package com.roberttisma.web.protrig.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.roberttisma.web.protrig.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.icgc.dcc.common.core.util.Splitters.NEWLINE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({ "statusCode", "statusName", "processName", "requestUrl", "message"})
public class ProcessError {

  private int statusCode;
  private String statusName;
  private String processName;
  private List<String> message;
  private String requestUrl;

  public void setHttpStatus(@NonNull HttpStatus status){
    setStatusName(status.getReasonPhrase());
    setStatusCode(status.value());
  }

  public void setMessage(@NonNull String message){
    this.message = ImmutableList.copyOf(NEWLINE.split(message));
  }


  @JsonIgnore
  public String toPrettyJson(){
    return JsonUtils.toPrettyJson(this);
  }

  @JsonIgnore
  public String toJson(){
    return JsonUtils.toJson(this);
  }

  @JsonIgnore
  public ResponseEntity<String> getResponseEntity(){
    return ResponseEntity.status(statusCode).body(toJson());
  }

}
