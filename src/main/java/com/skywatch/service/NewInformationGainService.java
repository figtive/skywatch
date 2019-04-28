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
}