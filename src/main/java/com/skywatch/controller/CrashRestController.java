package com.skywatch.controller;

import com.skywatch.model.Crash;
import com.skywatch.repository.CrashRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrashRestController {

    private final CrashRepository crashRepository;

    public CrashRestController(CrashRepository crashRepository) {
        this.crashRepository = crashRepository;
    }

    @GetMapping("/getratingtrue")
    public List<Crash> getRatingTrue() {
        return crashRepository.findByRatingIs(true);
    }

    @PostMapping("/postcrash")
    public Crash postCrash(@RequestBody Crash crash) {
        return crashRepository.save(crash);
    }

    @PostMapping("/batchpostdata")
    public List<Crash> batchPostCrash(@RequestBody List<Crash> crashes) {
        return crashRepository.saveAll(crashes);
    }

    @GetMapping("/deleteallcrash")
    public String deleteAllCrash() {
        crashRepository.deleteAll();
        return "Success";
    }
}
