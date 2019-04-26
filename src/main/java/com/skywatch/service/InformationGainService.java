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


    public int calculateNumOfCrashedTrue(String attribute) {
        ArrayList<Crash> crashes = new ArrayList<>();
        for (Crash object : crashRepository.findAll()) {
            crashes.add(object);
        }

        int numOfCrashedTrue = 0;

        switch(attribute) {
            case "rating":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isRating() && crashes.get(i).isCrashed()) {
                        numOfCrashedTrue++;
                    }
                }

            case "modelAge":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isModelAge() && crashes.get(i).isModelAge()) {
                        numOfCrashedTrue++;
                    }
                }

            case "firstFlight":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isFirstFlight() && crashes.get(i).isFirstFlight()) {
                        numOfCrashedTrue++;
                    }
                }

            case "pilotAge":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isPilotAge() && crashes.get(i).isPilotAge()) {
                        numOfCrashedTrue++;
                    }
                }

            case "weather":
                for (int i=0; i< crashes.size(); i++) {
                    if (crashes.get(i).isWeather() && crashes.get(i).isWeather()) {
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

    public InformationGainData calculateGainChild() {
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