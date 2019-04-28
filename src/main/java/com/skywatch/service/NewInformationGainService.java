package com.skywatch.service;

import com.skywatch.repository.CrashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewInformationGainService {

    private final CrashRepository crashRepository;

    public NewInformationGainService(CrashRepository crashRepository) {
        this.crashRepository = crashRepository;
    }

    private double entropy(int trueCount, int falseCount) {
        double probNumOfTrue = (double) trueCount / (trueCount + falseCount);
        return -(probNumOfTrue * (Math.log(probNumOfTrue) / Math.log(2)) +
                (1 - probNumOfTrue) * (Math.log(1 - probNumOfTrue) / Math.log(2)));
    }

    private double remainder(int trueCount, int falseCount) {
        return (double) (trueCount) / (trueCount + falseCount) * entropy(trueCount, falseCount) +
                (double) (falseCount) / (trueCount + falseCount) * entropy(trueCount, falseCount);
    }

    public double getInformationGain(boolean root, String attribute, String parent, boolean parentBool) {
        if (root) {
            switch (attribute) {
                case "rating":
                    return entropy(crashRepository.findByCrashedIs(true).size(), crashRepository.findByCrashedIs(false).size()) -
                            remainder(crashRepository.findByRatingIs(true).size(), crashRepository.findByRatingIs(false).size());
                case "modelAge":
                    return entropy(crashRepository.findByCrashedIs(true).size(), crashRepository.findByCrashedIs(false).size()) -
                            remainder(crashRepository.findByModelAgeIs(true).size(), crashRepository.findByModelAgeIs(false).size());
                case "firstFlight":
                    return entropy(crashRepository.findByCrashedIs(true).size(), crashRepository.findByCrashedIs(false).size()) -
                            remainder(crashRepository.findByFirstFlightIs(true).size(), crashRepository.findByFirstFlightIs(false).size());
                case "pilotAge":
                    return entropy(crashRepository.findByCrashedIs(true).size(), crashRepository.findByCrashedIs(false).size()) -
                            remainder(crashRepository.findByPilotAgeIs(true).size(), crashRepository.findByPilotAgeIs(false).size());
                case "weather":
                    return entropy(crashRepository.findByCrashedIs(true).size(), crashRepository.findByCrashedIs(false).size()) -
                            remainder(crashRepository.findByWeatherIs(true).size(), crashRepository.findByWeatherIs(false).size());
                default:
                    throw new IllegalArgumentException("Wrong");
            }
        } else {
            switch (parent) {
                case "rating":
                    switch (attribute) {
                        case "modelAge":
                            return entropy(crashRepository.findByModelAgeIsAndCrashedIs(parentBool, true).size(),
                                    crashRepository.findByModelAgeIsAndCrashedIs(parentBool, false).size()) -
                                    remainder(crashRepository.findByRatingAndModelAgeIs(parentBool, true).size(),
                                            crashRepository.findByRatingAndModelAgeIs(parentBool, false).size());
                    }
            }
        }
        return 0;
    }
}