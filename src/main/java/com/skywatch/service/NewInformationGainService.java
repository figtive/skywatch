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
