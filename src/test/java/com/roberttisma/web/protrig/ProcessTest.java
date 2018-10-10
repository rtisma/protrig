package com.roberttisma.web.protrig;

import com.roberttisma.web.protrig.service.ProcessService;
import com.roberttisma.web.protrig.config.PsqlDumpConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

@Slf4j
public class ProcessTest {

  @Test
  public void testRob(){
    val cmd = PsqlDumpConfig.builder()
        .host("localhost")
        .port("8082")
        .password("password")
        .username("postgres")
        .name("song")
        .exe("/usr/bin/pg_dump")
        .tables(new String[]{ "analysis", "donor", "sample"})
        .build();

    val service = new ProcessService(cmd);
    val out = service.syncIdData();
    log.info("sdfsdf");

  }

}
