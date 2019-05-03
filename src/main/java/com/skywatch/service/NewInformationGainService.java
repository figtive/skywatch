package com.skywatch.service;

import com.skywatch.exception.CrashedFoundException;
import com.skywatch.exception.SafeFoundException;
import com.skywatch.repository.CrashRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NewInformationGainService {

    private final CrashRepository crashRepository;
    private final JdbcTemplate jdbcTemplate;
    private final CrashSqlService crashSqlService;

    public NewInformationGainService(CrashRepository crashRepository, JdbcTemplate jdbcTemplate, CrashSqlService crashSqlService) {
        this.crashRepository = crashRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.crashSqlService = crashSqlService;
    }

    private double entropy(int trueCount, int totalCount, boolean crashBool) throws CrashedFoundException, SafeFoundException {
        double probability = (double) trueCount / totalCount;
        if (probability == 0 && !crashBool) {
            throw new SafeFoundException();
        } else if (probability == 1 && crashBool) {
            throw new CrashedFoundException();
        }
        double out = -(probability * (Math.log(probability) / Math.log(2)) +
                (1 - probability) * (Math.log(1 - probability) / Math.log(2)));
        System.out.println("entropy: " + out);
        return Double.isNaN(out) ? 0 : out;
    }

    private double remainder(int trueCount, int falseCount, int trueCountGiven, int falseCountGiven, boolean crashBool) {
        System.out.printf("trueCount: %d, falseCount: %d, trueCountGiven: %d, falseCountGiven: %d\n", trueCount, falseCount, trueCountGiven, falseCountGiven);
        double out = (double) (trueCount) / (trueCount + falseCount) * entropy(trueCountGiven, trueCount, crashBool) +
                (double) (falseCount) / (trueCount + falseCount) * entropy(falseCountGiven, falseCount, crashBool);
        System.out.println("remainder" + out);
        return out;
    }

    @SuppressWarnings({"Duplicates", "ConstantConditions"})
    double getInformationGain(String attribute, ArrayList<String> parentAttributes, ArrayList<Boolean> parentBoolean, boolean crashBool) {

        Integer totalCount = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);
        parentAttributes.add("crashed");
        parentBoolean.add(true);
        Integer crashCount = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);
        parentAttributes.remove(parentAttributes.size() - 1);
        parentBoolean.remove(parentBoolean.size() - 1);

        parentAttributes.add(0, attribute);
        parentBoolean.add(0, true);
        Integer trueCount = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);
        parentBoolean.set(0, false);
        Integer falseCount = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);

        parentAttributes.add("crashed");
        parentBoolean.add(true);
        Integer falseCountGiven = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);
        parentBoolean.set(0, true);
        Integer trueCountGiven = jdbcTemplate.queryForObject(crashSqlService.mapSql(parentAttributes.toArray(new String[0]), parentBoolean.toArray(new Boolean[0])), Integer.class);

        return entropy(crashCount, totalCount, false) -
                remainder(trueCount, falseCount, trueCountGiven, falseCountGiven, crashBool);
    }
}