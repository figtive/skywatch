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

    private final CrashRepository crashRepository;

    private final InformationGainDataRepository informationGainDataRepository;


    public int calculateNumOfOutcomeTrue(String attribute) {
        ArrayList<Crash> crashes = new ArrayList<>();
        for (Crash object : crashRepository.findAll()) {
            crashes.add(object);
        }

        int numOfOutcomeTrue = 0;

        switch(attribute) {
            case "rating":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isRating() && crashes.get(i).isCrashed()) {
                        numOfOutcomeTrue++;
                    }
                }

            case "modelAge":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isModelAge() && crashes.get(i).isModelAge()) {
                        numOfOutcomeTrue++;
                    }
                }

            case "firstFlight":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isFirstFlight() && crashes.get(i).isFirstFlight()) {
                        numOfOutcomeTrue++;
                    }
                }

            case "pilotAge":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isPilotAge() && crashes.get(i).isPilotAge()) {
                        numOfOutcomeTrue++;
                    }
                }

            case "weather":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isWeather() && crashes.get(i).isWeather()) {
                        numOfOutcomeTrue++;
                    }
                }
        }
        return numOfOutcomeTrue;
    }


    public InformationGainService(CrashRepository crashRepository, InformationGainDataRepository informationGainDataRepository) {
        this.crashRepository = crashRepository;
        this.informationGainDataRepository = informationGainDataRepository;
    }

    //@param sum of each value of the variable
    //@param total sample/number of values of the variable
    public double entropyImpurity(int[] sumOfEachVal, int totalVal) {
        double entropy = 0.0;
        for (int value : sumOfEachVal) {
            int probability = value / totalVal;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        return entropy;
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
        int numerator = calculateNumOfOutcomeTrue(attribute);
        return numOfTrue != 0 && numOfFalse != 0 ?
                (double) (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfTrue), (numOfTrue + numOfFalse)) +
                        (double) (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfFalse), (numOfTrue + numOfFalse)) : 0;

    }

    private double findGain(int numOfOutcomeTrue, int numOfOutcomeFalse, int numOfTrue, int numOfFalse) {
        return booleanEntropyImpurity(numOfOutcomeTrue, numOfOutcomeTrue + numOfOutcomeFalse) - findRemainder(numOfTrue, numOfFalse, );
    }

    public InformationGainData calculateGain() {
        double[] data = new double[5];
        int crashedTrue = crashRepository.findByCrashedIs(true).size();
        int crashedFalse = crashRepository.findAll().size() - crashedTrue;
        data[0] = findGain(
                crashedTrue,
                crashedFalse,
                crashRepository.findByRatingIs(true).size(),
                crashRepository.findByRatingIs(false).size()
        );
        data[1] = findGain(
                crashedTrue,
                crashedFalse,
                crashRepository.findByModelAgeIs(true).size(),
                crashRepository.findByModelAgeIs(false).size()
        );
        data[2] = findGain(
                crashedTrue,
                crashedFalse,
                crashRepository.findByFirstFlightIs(true).size(),
                crashRepository.findByFirstFlightIs(false).size()
        );
        data[3] = findGain(
                crashedTrue,
                crashedFalse,
                crashRepository.findByPilotAgeIs(true).size(),
                crashRepository.findByPilotAgeIs(false).size()
        );
        data[4] = findGain(
                crashedTrue,
                crashedFalse,
                crashRepository.findByWeatherIs(true).size(),
                crashRepository.findByWeatherIs(false).size()
        );
        informationGainDataRepository.deleteAll();
        return informationGainDataRepository.save(new InformationGainData(data));
    }
}