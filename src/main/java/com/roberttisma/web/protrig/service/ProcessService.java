package com.roberttisma.web.protrig.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessService {

  public void syncIdData(){
    log.info("heeeeeeeeeeeeeeeeeeeeeeeyyyy");
  }
  private static final String DUMP_COMMAND_FORMAT  = "PGPASSWORD=%s pg_dump -h %s  -p %s -U ${db_user} -t analysis_ids -t donor_ids -t sample_ids -t specimen_ids -t project_ids -t file_ids dcc_identifier | gzip  > ${output_file}\n";



}
