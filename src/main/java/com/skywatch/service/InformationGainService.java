//package com.skywatch.service;
//
//import com.skywatch.model.Crash;
//import com.skywatch.model.InformationGainData;
//import com.skywatch.repository.CrashRepository;
//import com.skywatch.repository.InformationGainDataRepository;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class InformationGainService {
//
//    private final CrashRepository crashRepository;
//
//    private final InformationGainDataRepository informationGainDataRepository;
//
//
//    private int calculateNumOfCrashedTrue(String attribute) {
//        ArrayList<Crash> crashes = new ArrayList<>(crashRepository.findAll());
//
//        int numOfCrashedTrue = 0;
//
//        switch(attribute) {
//            case "rating":
//                for (Crash crash : crashes) {
//                    if (crash.isRating() && crash.isCrashed()) {
//                        numOfCrashedTrue++;
//                    }
//                }
//
//            case "modelAge":
//                for (Crash crash : crashes) {
//                    if (crash.isModelAge()) {
//                        numOfCrashedTrue++;
//                    }
//                }
//
//            case "firstFlight":
//                for (Crash crash : crashes) {
//                    if (crash.isFirstFlight()) {
//                        numOfCrashedTrue++;
//                    }
//                }
//
//            case "pilotAge":
//                for (Crash crash : crashes) {
//                    if (crash.isPilotAge()) {
//                        numOfCrashedTrue++;
//                    }
//                }
//
//            case "weather":
//                for (Crash crash : crashes) {
//                    if (crash.isWeather()) {
//                        numOfCrashedTrue++;
//                    }
//                }
//        }
//        return numOfCrashedTrue;
//    }
//
//    private int calculateNumOfCrashedTrueChild(String attribute, boolean bool) {
//        int val = 0;
//        switch (attribute) {
//            case "rating":
//                val = crashRepository.findByRatingAndCrashedIs(bool, true).size();
//                break;
//            case "modelAge":
//                val = crashRepository.findByModelAgeIsAndCrashedIs(bool, true).size();
//                break;
//            case "firstFlight":
//                val = crashRepository.findByFirstFlightAndCrashedIs(bool, true).size();
//                break;
//            case "pilotAge":
//                val = crashRepository.findByPilotAgeAndCrashedIs(bool, true).size();
//                break;
//            case "weather":
//                val = crashRepository.findByWeatherAndCrashedIs(bool, true).size();
//                break;
//        }
//        return val;
//    }
//
//
//    public InformationGainService(CrashRepository crashRepository, InformationGainDataRepository informationGainDataRepository) {
//        this.crashRepository = crashRepository;
//        this.informationGainDataRepository = informationGainDataRepository;
//    }
//
//
//    //@param num of TRUE of the variable
//    //@param total sample/number of values of the variable
//    private double booleanEntropyImpurity(int numOfTrue, int totalVals) {
//        double probNumOfTrue = totalVals != 0 ? (double) numOfTrue / totalVals : 0;
//        return -(probNumOfTrue * (Math.log(probNumOfTrue) / Math.log(2)) + (1 - probNumOfTrue) * (Math.log(1 - probNumOfTrue) / Math.log(2)));
//
//    }
//
//    private double goalEntropy(int nOfTrue, int totalVals) {
//        return booleanEntropyImpurity(nOfTrue, totalVals);
//    }
//
//    private double findRemainder(int numOfTrue, int numOfFalse, String attribute) {
//        int numerator = calculateNumOfCrashedTrue(attribute);
//        return numOfTrue != 0 && numOfFalse != 0 ?
//            (double) (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfTrue), (numOfTrue + numOfFalse)) +
//                    (double) (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfFalse), (numOfTrue + numOfFalse)) : 0;
//
//
//    }
//
//    //TODO
//    private double findRemainderChild(int numOfTrue, int numOfFalse, String attribute, boolean bool) {
//        int numerator = calculateNumOfCrashedTrueChild(attribute, bool);
//        return numOfTrue != 0 && numOfFalse != 0 ?
//            (double) (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfTrue), (numOfTrue + numOfFalse)) +
//                    (double) (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy((numerator/numOfFalse), (numOfTrue + numOfFalse)) : 0;
//
//
//    }
//
//    private double findGain(int numOfTrue, int numOfFalse, String attribute) {
//        int numerator = calculateNumOfCrashedTrue(attribute);
//        return booleanEntropyImpurity(numerator, numOfTrue + numOfFalse) - findRemainder(numOfTrue, numOfFalse, attribute);
//    }
//
//    // private double findChildGain(int numOfTrue, int numOfFalse, String parentAttribute, String childAttribute, boolean bool) {
//    //     int numerator = calculateNumOfCrashedTrueChild(parentAttribute, bool);
//    //     switch(parentAttribute){
//    //         case "rating":
//    //             if(childAttribute == "modelAge"){
//    //             return booleanEntropyImpurity(numerator, crashRepository.findByRatingIs(bool).size()) - findRemainder(numOfTrue, numOfFalse, parentAttribute);
//    //             }
//    //         case "modelAge":
//    //             return booleanEntropyImpurity(numerator,numOfFalse) - findRemainder(numOfTrue, numOfFalse, parentAttribute);
//    //     }
//    // }
//
//    public InformationGainData calculateGain() {
//        double[] data = new double[5];
//        data[0] = findGain(
//                crashRepository.findByRatingIs(true).size(),
//                crashRepository.findByRatingIs(false).size(),
//                "rating"
//        );
//        data[1] = findGain(
//                crashRepository.findByModelAgeIs(true).size(),
//                crashRepository.findByModelAgeIs(false).size(),
//                "modelAge"
//        );
//        data[2] = findGain(
//                crashRepository.findByFirstFlightIs(true).size(),
//                crashRepository.findByFirstFlightIs(false).size(),
//                "firstFlight"
//        );
//        data[3] = findGain(
//                crashRepository.findByPilotAgeIs(true).size(),
//                crashRepository.findByPilotAgeIs(false).size(),
//                "pilotAge"
//        );
//        data[4] = findGain(
//                crashRepository.findByWeatherIs(true).size(),
//                crashRepository.findByWeatherIs(false).size(),
//                "weather"
//        );
//        informationGainDataRepository.deleteAll();
//        return informationGainDataRepository.save(new InformationGainData(data));
//    }
//
//    public InformationGainData calculateGainChild(String childAttribute) {
//        int numOfTrue = crashRepository.findByCrashedIs(true).size();
//        int numOfFalse = crashRepository.findByCrashedIs(false).size();
//        Node temporaryNode;
//        if (childAttribute.equals("rating")) {
//            temporaryNode = new Node("rating", null);
//        }
//        double[] data = new double[5];
//        data[0] = findChildGain(
//                numOfTrue,
//                numOfFalse,
//                temporaryNode.getParent();
//                "rating",
//                temporaryNode.getBoolean();
//
//        );
//        data[1] = findGain(
//                crashRepository.findByModelAgeIs(true).size(),
//                crashRepository.findByModelAgeIs(false).size(),
//                "modelAge"
//        );
//        data[2] = findGain(
//                crashRepository.findByFirstFlightIs(true).size(),
//                crashRepository.findByFirstFlightIs(false).size(),
//                "firstFlight"
//        );
//        data[3] = findGain(
//                crashRepository.findByPilotAgeIs(true).size(),
//                crashRepository.findByPilotAgeIs(false).size(),
//                "pilotAge"
//        );
//        data[4] = findGain(
//                crashRepository.findByWeatherIs(true).size(),
//                crashRepository.findByWeatherIs(false).size(),
//                "weather"
//        );
//        informationGainDataRepository.deleteAll();
//        return informationGainDataRepository.save(new InformationGainData(data));
//    }
//}