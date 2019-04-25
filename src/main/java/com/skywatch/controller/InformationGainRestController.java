package com.skywatch.controller;

import com.skywatch.service.InformationGainService;
import com.skywatch.model.InformationGainData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationGainRestController {

    private final InformationGainData informationGainData;

    private final InformationGainService informationGainService;

    public InformationGainRestController(InformationGainData informationGainData, InformationGainService informationGainService) {
        this.informationGainData = informationGainData;
        this.informationGainService = informationGainService;
    }

    @GetMapping("/getig")
    public InformationGainData getIg() {
        return informationGainData;
    }

    @GetMapping("/calculateig")
    public InformationGainData calculateIg() {
        return informationGainService.calculateGain();
    }
}
