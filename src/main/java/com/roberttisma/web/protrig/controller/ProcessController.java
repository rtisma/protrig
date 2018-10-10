package com.roberttisma.web.protrig.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {


  @ApiOperation(value = "ReadAnalysis", notes = "Retrieve the analysis object for an analysisId")
  @GetMapping(value = "/syncIdServer")
  public void syncIdServer() {
    log.info("helooooooooooooooooooooooooooooo");
  }

}
