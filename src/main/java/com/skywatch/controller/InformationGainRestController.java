package com.skywatch.controller;

import com.skywatch.model.Crash;
import com.skywatch.service.DecisionTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class InformationGainRestController {

    Logger logger = LoggerFactory.getLogger(InformationGainRestController.class);

    private final DecisionTreeService decisionTreeService;

    public InformationGainRestController(DecisionTreeService decisionTreeService) {
        this.decisionTreeService = decisionTreeService;
    }

    @GetMapping("/predict")
    public Map<String, Boolean> predictCrash(@RequestParam(name = "rating") boolean rating,
                                             @RequestParam(name = "modelAge") boolean modelAge,
                                             @RequestParam(name = "firstFlight") boolean firstFlight,
                                             @RequestParam(name = "pilotAge") boolean pilotAge,
                                             @RequestParam(name = "weather") boolean weather) {
        return Collections.singletonMap("crashed", decisionTreeService.predictCrash(new Crash(rating, modelAge, firstFlight, pilotAge, weather), true));
    }

    @GetMapping("/accuracy/get")
    public double getAccuracy(@RequestBody List<Crash> crashes) {
        int correctCount = 0;
        for (Crash crash : crashes) {
            if (decisionTreeService.predictCrash(crash) == crash.isCrashed()) {
                correctCount++;
            }
        }
        double accuracy = (double) correctCount / crashes.size() * 100;
        logger.info(String.format("Accuracy: %f", accuracy));
        return accuracy;
    }
}
