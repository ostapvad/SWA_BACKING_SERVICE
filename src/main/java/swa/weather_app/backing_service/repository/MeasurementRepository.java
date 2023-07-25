package swa.weather_app.backing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swa.weather_app.backing_service.entity.WeatherMeasurement;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<WeatherMeasurement, Long>{
  //  List<Measurement> FindByCity(String city);
    List <WeatherMeasurement> findWeatherMeasurementsByCityAndTimeBetween(String city, LocalDateTime from,
                                                                                  LocalDateTime to);
    List<WeatherMeasurement>findWeatherMeasurementsByCity(String city);

}
