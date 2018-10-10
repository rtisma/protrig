package com.roberttisma.web.protrig.service;

import com.roberttisma.web.protrig.model.SyncIdDataResponse;
import com.roberttisma.web.protrig.config.PsqlDumpConfig;
import com.roberttisma.web.protrig.util.StreamGobbler;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkState;
import static com.roberttisma.web.protrig.exceptions.ProcessException.checkProcessServer;
import static java.nio.file.Files.exists;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
public class ProcessService {

  private final static char NEWLINE = '\n';

  private final PsqlDumpConfig psqlDumpConfig;

  @Autowired
  public ProcessService(@NonNull PsqlDumpConfig psqlDumpConfig) {
    this.psqlDumpConfig = psqlDumpConfig;
  }

  @SneakyThrows
  public SyncIdDataResponse syncIdData(){
    checkProcessServer(exists(Paths.get(psqlDumpConfig.getExe())), "syncIdData", INTERNAL_SERVER_ERROR,
        "the exe path '%s' does not exist", psqlDumpConfig.getExe());
    val pb = new ProcessBuilder();
    checkState(!isWindows(), "This app doesnt work on windows");
    val finalCommand = (psqlDumpConfig.generateCommand()).split("\\s+");
    pb.environment().putAll(psqlDumpConfig.getEnvironment());
    pb.command(finalCommand);
    pb.redirectErrorStream(true);
    val process = pb.start();
    val sb = new StringBuffer();
    val streamGobbler = StreamGobbler.builder()
        .inputStream(process.getInputStream())
        .consumer(x -> {
          sb.append(x);
          sb.append(NEWLINE);
        })
        .build();
    newSingleThreadExecutor().submit(streamGobbler);
    int exitCode = process.waitFor();
    log.info("OUTPUT: \n{}", sb.toString());
    log.info("ExitCode: {}", exitCode);
    checkProcessServer(exitCode==0, "syncIdData", INTERNAL_SERVER_ERROR, sb.toString());
    val resp = new SyncIdDataResponse();
    resp.setHttpStatus(HttpStatus.OK);
    resp.setProcessName("syncIdData");
    resp.setMessage(sb.toString());
    return resp;
  }

  private static boolean isWindows(){
    return System.getProperty("os.name").toLowerCase().startsWith("windows");
  }


}
