//package com.skywatch.repository;
//
//import com.skywatch.model.InformationGainData;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface InformationGainDataRepository extends JpaRepository<InformationGainData, Long> {
//    List<InformationGainData> findByRatingAndCrashedIs(boolean rating, boolean crashed);
//    List<InformationGainData> findByModelAgeIsAndCrashedIs(boolean modelAge, boolean crashed);
//    List<InformationGainData> findByFirstFlightAndCrashedIs(boolean firstFlight, boolean crashed);
//    List<InformationGainData> findByPilotAgeAndCrashedIs(boolean pilotAge, boolean crashed);
//    List<InformationGainData> findByWeatherAndCrashedIs(boolean weather, boolean crashed);
//    List<InformationGainData> findByCrashedIs(boolean crashed);
//}
