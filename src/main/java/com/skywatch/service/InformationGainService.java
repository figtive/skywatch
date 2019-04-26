package com.skywatch.service;

import com.skywatch.model.Crash;
import com.skywatch.model.InformationGainData;
import com.skywatch.repository.CrashRepository;
import com.skywatch.repository.InformationGainDataRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class InformationGainService {
`
    private final CrashRepository crashRepository;

    private final InformationGainDataRepository informationGainDataRepository;


    private int calculateNumOfCrashedTrue(String attribute) {
        ArrayList<Crash> crashes = new ArrayList<>(crashRepository.findAll());

        int numOfCrashedTrue = 0;

        switch(attribute) {
            case "rating":
                for (Crash crash : crashes) {
                    if (crash.isRating() && crash.isCrashed()) {
                        numOfCrashedTrue++;
                    }
                }

            case "modelAge":
                for (Crash crash : crashes) {
                    if (crash.isModelAge()) {
                        numOfCrashedTrue++;
                    }
                }

            case "firstFlight":
                for (Crash crash : crashes) {
                    if (crash.isFirstFlight()) {
                        numOfCrashedTrue++;
                    }
                }

            case "pilotAge":
                for (Crash crash : crashes) {
                    if (crash.isPilotAge()) {
                        numOfCrashedTrue++;
                    }
                }

            case "weather":
                for (Crash crash : crashes) {
                    if (crash.isWeather()) {
                        numOfCrashedTrue++;
                    }
                }
        }
        return numOfCrashedTrue;
    }


    public InformationGainService(CrashRepository crashRepository, InformationGainDataRepository informationGainDataRepository) {
        this.crashRepository = crashRepository;
        this.informationGainDataRepository = informationGainDataRepository;
    }


    //@param num of TRUE of the variable
    //@param total sample/number of values of the variable
    private double booleanEntropyImpurity(int numOfTrue, int totalVals) {
        double probNumOfTrue = totalVals != 0 ? (double) numOfTrue / totalVals : 0;
        return -(probNumOfTrue * (Math.log(probNumOfTrue) / Math.log(2)) + (1 - probNumOfTrue) * (Math.log(1 - probNumOfTrue) / Math.log(2)));

    }

    private double goalEntropy(int nOfTrue, int totalVals) {
        return booleanEntropyImpurity(nOfTrue, totalVals);
    }

    private double findRemainder(int numOfTrue, int numOfFalse, String attribute) {
        int numerator = calculateNumOfCrashedTrue(attribute);
        return numOfTrue != 0 && numOfFalse != 0 ?
            (double) (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfTrue), (numOfTrue + numOfFalse)) +
                    (double) (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfFalse), (numOfTrue + numOfFalse)) : 0;


    }

    private double findGain(int numOfTrue, int numOfFalse, String attribute) {
        int numerator = calculateNumOfCrashedTrue(attribute);
        return booleanEntropyImpurity(numerator, numOfTrue + numOfFalse) - findRemainder(numOfTrue, numOfFalse, attribute);
    }

    private double findChildGain(int numOfTrue, int numOfFalse, String attributeParent, boolean bool) {
        int numerator = calculateNumOfCrashedTrueChild(attribute, bool);
        return booleanEntropyImpurity(numerator, informationGainDataRepository.findByRatingIs(bool)) - findRemainder(numOfTrue, numOfFalse, attribute);
    }

    public InformationGainData calculateGain() {
        double[] data = new double[5];
        data[0] = findGain(
                crashRepository.findByRatingIs(true).size(),
                crashRepository.findByRatingIs(false).size(),
                "rating"
        );
        data[1] = findGain(
                crashRepository.findByModelAgeIs(true).size(),
                crashRepository.findByModelAgeIs(false).size(),
                "modelAge"
        );
        data[2] = findGain(
                crashRepository.findByFirstFlightIs(true).size(),
                crashRepository.findByFirstFlightIs(false).size(),
                "firstFlight"
        );
        data[3] = findGain(
                crashRepository.findByPilotAgeIs(true).size(),
                crashRepository.findByPilotAgeIs(false).size(),
                "pilotAge"
        );
        data[4] = findGain(
                crashRepository.findByWeatherIs(true).size(),
                crashRepository.findByWeatherIs(false).size(),
                "weather"
        );
        informationGainDataRepository.deleteAll();
        return informationGainDataRepository.save(new InformationGainData(data));
    }

    public InformationGainData calculateChildGain() {
        double[] data = new double[5];
        data[0] = findGain(
                crashRepository.findByRatingIs(true).size(),
                crashRepository.findByRatingIs(false).size(),
                "rating"
        );
        data[1] = findGain(
                crashRepository.findByModelAgeIs(true).size(),
                crashRepository.findByModelAgeIs(false).size(),
                "modelAge"
        );
        data[2] = findGain(
                crashRepository.findByFirstFlightIs(true).size(),
                crashRepository.findByFirstFlightIs(false).size(),
                "firstFlight"
        );
        data[3] = findGain(
                crashRepository.findByPilotAgeIs(true).size(),
                crashRepository.findByPilotAgeIs(false).size(),
                "pilotAge"
        );
        data[4] = findGain(
                crashRepository.findByWeatherIs(true).size(),
                crashRepository.findByWeatherIs(false).size(),
                "weather"
        );
        informationGainDataRepository.deleteAll();
        return informationGainDataRepository.save(new InformationGainData(data));
    }
}