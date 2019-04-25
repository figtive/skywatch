package com.skywatch.config;

import com.skywatch.model.InformationGainData;
import com.skywatch.repository.InformationGainDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class InformationGainConfig {

    private final InformationGainDataRepository informationGainDataRepository;

    public InformationGainConfig(InformationGainDataRepository informationGainDataRepository) {
        this.informationGainDataRepository = informationGainDataRepository;
    }

    @Bean
    public InformationGainData informationGainData() {
        Optional<InformationGainData> informationGainData = informationGainDataRepository.findById(1L);
        if (informationGainData.isPresent()) {
            return informationGainData.get();
        } else {

        }
    }
}
