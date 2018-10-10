package com.roberttisma.web.protrig.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Component
public class PsqlDumpCommand {
  private static final String WHITESPACE = " ";

  // -t analysis_ids -t donor_ids -t sample_ids -t specimen_ids -t project_ids -t file_ids dcc_identifier";
  private static final String DUMP_COMMAND_FORMAT  = "PGPASSWORD=%s pg_dump -h %s  -p %s -U %s";

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

  public String generateCommand(){
    return format(DUMP_COMMAND_FORMAT, password, host, port, username)+WHITESPACE+generateTableSwitches()+WHITESPACE+name;
  }

  private String generateTableSwitches(){
    return stream(tables).map(x -> "-t "+x).collect(joining(WHITESPACE));
  }

}
