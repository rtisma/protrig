package com.roberttisma.web.protrig.controller;

import com.roberttisma.web.protrig.service.ProcessService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

  @Autowired
  private ProcessService processService;


  @ApiOperation(value = "ReadAnalysis", notes = "Retrieve the analysis object for an analysisId")
  @GetMapping(value = "/syncIdServer")
  public void syncIdServer() {
    processService.syncIdData();
  }

}
