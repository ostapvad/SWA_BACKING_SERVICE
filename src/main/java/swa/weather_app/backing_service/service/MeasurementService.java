package swa.weather_app.backing_service.service;

import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.entity.WeatherMeasurement;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementService {


    WeatherMeasurement saveNewMeasurement(WeatherMeasurement weatherMeasurements);

    List<WeatherMeasurement> retrieveAllMeasurements();
    List <WeatherMeasurement> findByCityAndFromToTime(String city, LocalDateTime from, LocalDateTime to) throws CityMeasurementsAreNotFound;
}
