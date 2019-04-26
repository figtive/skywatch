package com.skywatch.algorithms;

import com.skywatch.repository.CrashRepository;
import org.springframework.stereotype.Service;

@Service
public class InformationGain {

    private final CrashRepository crashRepository;

    public InformationGain(CrashRepository crashRepository) {
        this.crashRepository = crashRepository;
    }

    private int getRatingTrueCount() {
        return crashRepository.findByRatingIs(true).size();
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
    public double booleanEntropyImpurity(int numOfTrue, int totalVals) {
        double probNumOfTrue = numOfTrue / totalVals;
        return -(probNumOfTrue * (Math.log(probNumOfTrue) / Math.log(2)) + (1 - probNumOfTrue) * (Math.log(1 - probNumOfTrue) / Math.log(2)));

    }

    public double goalEntropy(int nOfTrue, int totalVals) {
        return booleanEntropyImpurity(nOfTrue, totalVals);
    }

    public double findRemainder(int numOfTrue, int numOfFalse) {
        return (numOfTrue / (numOfTrue + numOfFalse)) * goalEntropy(numOfTrue, numOfTrue + numOfFalse) +
                (numOfFalse / (numOfTrue + numOfFalse)) * goalEntropy(numOfFalse, numOfTrue + numOfFalse);

    }

    public double findGain(int numOfTrue, int numOfFalse) {
        return booleanEntropyImpurity(numOfTrue, numOfTrue + numOfFalse) - findRemainder(numOfTrue, numOfFalse);
    }

}