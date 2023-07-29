package swa.weather_app.backing_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swa.weather_app.backing_service.dtos.WeatherMeasurementDTO;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.entity.WindData;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.service.MeasurementService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MeasurementController {


    @Autowired
    private final MeasurementService measurementService;
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Backing service is OK.", HttpStatus.OK);
    }

    @PostMapping("/measurements")
    public ResponseEntity<WeatherMeasurement> saveMeasurement(@RequestBody WeatherMeasurementDTO measurementDTO) {
        LOGGER.info(String.format("Posting new weather measurement of city %s", measurementDTO.getCity()));
        var weatherMeasurement = WeatherMeasurement.builder()
                .time(measurementDTO.getTime())
                .city(measurementDTO.getCity())
                .temperature((float) measurementDTO.getTemperature())
                .pressure(measurementDTO.getPressure())
                .humidity(measurementDTO.getHumidity()).wind(
                WindData.builder().speed((float) measurementDTO.getWind().getSpeed())
                        .gust((float) measurementDTO.getWind().getGust())
                        .deg(measurementDTO.getWind().getDeg()).build()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.saveNewMeasurement(weatherMeasurement));
    }

    @GetMapping("/measurements/all")
    public ResponseEntity<List<WeatherMeasurement>> retrieveAllMeasurements() {

        return ResponseEntity.status(HttpStatus.OK).body(measurementService.retrieveAllMeasurements());
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<WeatherMeasurement>> getMeasurementsByCityAndTime(@RequestParam(name = "city")
                                                                                 String city,
                                                                                 @RequestParam(name = "from")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                 LocalDateTime from,
                                                                                 @RequestParam(name = "to")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                 LocalDateTime to) throws CityMeasurementsAreNotFound {
        LOGGER.info(String.format("Getting measurements of city %s till the date %s", city, to.toString()));

        List<WeatherMeasurement> foundMeasurements = measurementService.findByCityAndFromToTime(city, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(foundMeasurements);
    }

}
