package swa.weather_app.backing_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.repository.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService{

    private final MeasurementRepository measurementRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public WeatherMeasurement saveNewMeasurement(WeatherMeasurement newWeatherMeasurement){
        return measurementRepository.save(newWeatherMeasurement);
    }

    @Override
    public List<WeatherMeasurement> retrieveAllMeasurements(){
        return measurementRepository.findAll().stream().toList();
    }

    @Override
    public List<WeatherMeasurement> findByCityAndFromToTime(String city, LocalDateTime from, LocalDateTime to) throws CityMeasurementsAreNotFound {
        List<WeatherMeasurement> foundMeasurements  = measurementRepository.
                findWeatherMeasurementsByCityAndTimeBetween(city, from, to);
        if(foundMeasurements.isEmpty()){
            LOGGER.warn("Measurements are not found!");
            throw new CityMeasurementsAreNotFound(String.format("Measurements for city %s are not presented in the " +
                    "dates from %s to %s", city, from.toString(), to.toString()));
        }
        return foundMeasurements;
    }



}
