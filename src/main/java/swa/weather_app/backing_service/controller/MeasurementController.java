package swa.weather_app.backing_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import swa.weather_app.backing_service.dtos.WeatherMeasurementDTO;
import swa.weather_app.backing_service.entity.ErrorMessage;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.entity.WindData;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.service.MeasurementService;
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Tag(name = "Backing service", description = "Backing service management APIs")
public class MeasurementController {


    @Autowired
    private final MeasurementService measurementService;
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/")
    @Operation(
            summary = "Health check status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backing service is running", content =
                    @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string",
                            format = "plain", example = "Backing service is OK."))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred", content = @Content())
    })
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Backing service is OK.", HttpStatus.OK);
    }

    @GetMapping("/measurements/all")
    @Operation(summary = "Retrieve all measurements from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of measurements received",
                    content = @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = WeatherMeasurement.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred", content = @Content())
    })
    public ResponseEntity<List<WeatherMeasurement>> retrieveAllMeasurements() {
        return ResponseEntity.status(HttpStatus.OK).body(measurementService.retrieveAllMeasurements());
    }

    @GetMapping("/measurements")
    @Operation(summary = "Find measurement by city and in time interval from and to")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of measurements received",
                    content =
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WeatherMeasurement.class)))),
            @ApiResponse(responseCode = "404", description =
                    "Weather measurements of city in the current time interval are not found", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred", content = @Content())
    })
    public ResponseEntity<List<WeatherMeasurement>> getMeasurementsByCityAndTime(@RequestParam(name = "city")
                                                                                 String city,
                                                                                 @RequestParam(name = "from")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                 LocalDateTime from,
                                                                                 @RequestParam(name = "to")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                 LocalDateTime to) throws CityMeasurementsAreNotFound {
        city = UriUtils.decode(city, StandardCharsets.UTF_8);
        LOGGER.info(String.format("Getting measurements of city %s till the date %s", city, to.toString()));

        List<WeatherMeasurement> foundMeasurements = measurementService.findByCityAndFromToTime(city, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(foundMeasurements);
    }
    @PostMapping("/measurements")
    @Operation(summary = "Save new measurement to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Weather measurement is created in DB",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = WeatherMeasurement.class))),
            @ApiResponse(responseCode = "500", description = "Internal error occurred", content = @Content())
    })
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

}
