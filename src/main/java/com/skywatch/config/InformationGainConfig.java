package com.skywatch.config;

import com.skywatch.service.InformationGainService;
import com.skywatch.model.InformationGainData;
import com.skywatch.repository.InformationGainDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class InformationGainConfig {

    private final InformationGainDataRepository informationGainDataRepository;
    private final InformationGainService informationGainService;

    public InformationGainConfig(InformationGainDataRepository informationGainDataRepository, InformationGainService informationGainService) {
        this.informationGainDataRepository = informationGainDataRepository;
        this.informationGainService = informationGainService;
    }

    @Bean
    public InformationGainData informationGainData() {
        Optional<InformationGainData> informationGainData = informationGainDataRepository.findById(1L);
        return informationGainData.orElseGet(informationGainService::calculateGain);
    }
}
