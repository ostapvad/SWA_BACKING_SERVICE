package swa.weather_app.backing_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.service.MeasurementService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {


    @Autowired
    private final MeasurementService measurementService;
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }
    @PostMapping
    public WeatherMeasurement saveMeasurement(@RequestBody WeatherMeasurement newMeasurement){
        LOGGER.info(String.format("Posting new weather measurement of city %s", newMeasurement.getCity()));
        return  measurementService.saveNewMeasurement(newMeasurement);

    }
    @GetMapping("/all")
    public List<WeatherMeasurement> retrieveAllMeasurements(){
        return  measurementService.retrieveAllMeasurements();
    }

    @GetMapping()
    public List<WeatherMeasurement> getMeasurementsByCityAndTime(@RequestParam (name = "city")
                                                                     String city,
                                                                 @RequestParam (name = "from")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                 LocalDateTime from,
                                                                 @RequestParam (name = "to")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                     LocalDateTime to) throws CityMeasurementsAreNotFound {
        LOGGER.info(String.format("Getting measurements of city %s till the date %s", city, to.toString()));

        return measurementService.findByCityAndFromToTime(city, from, to);
    }

}
