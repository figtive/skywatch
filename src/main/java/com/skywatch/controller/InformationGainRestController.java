package com.skywatch.controller;

import com.skywatch.service.NewInformationGainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class InformationGainRestController {

    private final NewInformationGainService newInformationGainService;

    public InformationGainRestController(NewInformationGainService newInformationGainService) {
        this.newInformationGainService = newInformationGainService;
    }

    @GetMapping("/calculaterootig")
    public HashMap<String, Double> calculateRootIg() {
        HashMap<String, Double> out = new HashMap<>();
        out.put("rating", newInformationGainService.getInformationGain(true, "rating", "", false));
        out.put("modelAge", newInformationGainService.getInformationGain(true, "modelAge", "", false));
        out.put("firstFlight", newInformationGainService.getInformationGain(true, "firstFlight", "", false));
        out.put("pilotAge", newInformationGainService.getInformationGain(true, "pilotAge", "", false));
        out.put("weather", newInformationGainService.getInformationGain(true, "weather", "", false));
        return out;
    }
}
