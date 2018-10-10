package com.roberttisma.web.protrig.model;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.icgc.dcc.common.core.util.Splitters.NEWLINE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIdDataResponse {

  private HttpStatus httpStatus;
  private String processName;
  private List<String> message;

  public void setMessage(@NonNull String message){
    this.message = ImmutableList.copyOf(NEWLINE.split(message));
  }

}
