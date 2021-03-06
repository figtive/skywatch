package com.skywatch.repository;

import com.skywatch.model.Crash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrashRepository extends JpaRepository<Crash, Long> {
    List<Crash> findByRatingIs(boolean rating);
    List<Crash> findByModelAgeIs(boolean modelAge);
    List<Crash> findByFirstFlightIs(boolean firstFlight);
    List<Crash> findByPilotAgeIs(boolean pilotAge);
    List<Crash> findByWeatherIs(boolean weather);
    List<Crash> findByCrashedIs(boolean crashed);

    List<Crash> findByRatingAndCrashedIs(boolean rating, boolean crashed);
    List<Crash> findByModelAgeIsAndCrashedIs(boolean modelAge, boolean crashed);
    List<Crash> findByFirstFlightAndCrashedIs(boolean firstFlight, boolean crashed);
    List<Crash> findByPilotAgeAndCrashedIs(boolean pilotAge, boolean crashed);
    List<Crash> findByWeatherAndCrashedIs(boolean weather, boolean crashed);

    List<Crash> findByRatingAndModelAgeIs(boolean rating, boolean modelAge);
}
