package com.roberttisma.web.protrig.config;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Getter
@Builder
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class PsqlDumpConfig {
  private static final String WHITESPACE = " ";

  // -t analysis_ids -t donor_ids -t sample_ids -t specimen_ids -t project_ids -t file_ids dcc_identifier";
  private static final String DUMP_COMMAND_FORMAT  = "%s -h %s  -p %s -U %s";

  @Value("${db.exe}")
  private String exe;

  @Value("${db.password}")
  private String password;

  @Value("${db.username}")
  private String username;

  @Value("${db.host}")
  private String host;

  @Value("${db.port}")
  private String port;

  @Value("${db.name}")
  private String name;

  @Value("${db.tables}")
  private String[] tables;

  public Map<String, String> getEnvironment(){
    val map = ImmutableMap.<String, String>builder();
    map.put("PGPASSWORD", password);
    return map.build();
  }

  public String generateCommand(){
    return format(DUMP_COMMAND_FORMAT, exe, host, port, username)+WHITESPACE+generateTableSwitches()+WHITESPACE+name;
  }

  private String generateTableSwitches(){
    return stream(tables).map(x -> "-t "+x).collect(joining(WHITESPACE));
  }

}
