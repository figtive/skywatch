package com.skywatch.service;

import com.skywatch.model.InformationGainData;
import com.skywatch.repository.CrashRepository;
import com.skywatch.repository.InformationGainDataRepository;
import org.springframework.stereotype.Service;

@Service
public class InformationGainService {

    private final CrashRepository crashRepository;

    private final InformationGainDataRepository informationGainDataRepository;

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
        double probNumOfTrue = totalVals != 0 ? numOfTrue / totalVals : 0;
        return -(probNumOfTrue * (Math.log(probNumOfTrue) / Math.log(2)) + (1 - probNumOfTrue) * (Math.log(1 - probNumOfTrue) / Math.log(2)));

    }

    private double goalEntropy(int nOfTrue, int totalVals) {
        return booleanEntropyImpurity(nOfTrue, totalVals);
    }

    private double findRemainder(int numOfTrue, int numOfFalse) {
        return numOfTrue != 0 && numOfFalse != 0 ?
                (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy(numOfTrue, numOfTrue + numOfFalse) +
                (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy(numOfFalse, numOfTrue + numOfFalse)
                :
                0;

    }
    
    private double findGain(int numOfOutcomeTrue, int numOfOutcomeFalse, int numOfTrue, int numOfFalse) {
        return booleanEntropyImpurity(numOfOutcomeTrue, numOfOutcomeTrue + numOfOutcomeFalse) - findRemainder(numOfTrue, numOfFalse);
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