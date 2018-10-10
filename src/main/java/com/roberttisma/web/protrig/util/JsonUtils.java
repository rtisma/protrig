package com.roberttisma.web.protrig.util;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;

public class JsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static class PrettyPrinter extends DefaultPrettyPrinter {
    public static final PrettyPrinter instance = new PrettyPrinter(4);

    public PrettyPrinter(int indentSize) {
      val sb = new StringBuilder();
      for (int i=0; i<indentSize; i++){
        sb.append(' ');
      }
      indentArraysWith(new DefaultIndenter(sb.toString(), DefaultIndenter.SYS_LF));
    }
  }

  @SneakyThrows
  public static String toPrettyJson(Object o) {
    return MAPPER.writer(PrettyPrinter.instance).writeValueAsString(o);
  }

  @SneakyThrows
  public static String toJson(Object o) {
    return MAPPER.writeValueAsString(o);
  }

}
