package com.skywatch.controller;

import com.skywatch.model.Crash;
import com.skywatch.service.DecisionTreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class InformationGainRestController {


    private final DecisionTreeService decisionTreeService;

    public InformationGainRestController(DecisionTreeService decisionTreeService) {
        this.decisionTreeService = decisionTreeService;
    }

    @GetMapping("/predict")
    public Map<String, Boolean> predictCrash(@RequestBody Crash crash) {
        return Collections.singletonMap("crashed", decisionTreeService.predictCrash(crash));
    }
}
