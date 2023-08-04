package swa.weather_app.backing_service.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.entity.WindData;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Order;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.repository.MeasurementRepository;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class MeasurementServiceTest {
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private MeasurementRepository measurementRepository;
    private final LocalDateTime curTime =  LocalDateTime.of(2023, 10, 1, 1,
            1, 1);
    private final WindData curWindData = WindData.builder().deg(35).speed(10f).deg(15).build();
    private WeatherMeasurement.WeatherMeasurementBuilder measurementBuilder = WeatherMeasurement.builder().
            temperature(35F).pressure(100).wind(curWindData).time(curTime);

    @BeforeEach
    public void cleanRepository(){
        measurementRepository.deleteAll();
    }

    @Test
    @Description("Checks that new measurement is saved to repository correctly")
    @Disabled
    @Order(1)
    public void SaveNewMeasurementRepositoryReturnsSavedEntity(){
        WeatherMeasurement measurementToSave = measurementBuilder.city("Odesa").build();
        WeatherMeasurement savedMeasurement = measurementService.saveNewMeasurement(measurementToSave);

        Assertions.assertEquals(1, measurementRepository.count());
        Assertions.assertEquals(measurementToSave, savedMeasurement);

    }
    @Test
    @Description("Controls that multiple measurements instances are retrieved from repository")
    @Disabled
    @Order(2)
    public void RetrieveAllMeasurementsPerformsCorrectly(){
        List<WeatherMeasurement> measurementsToSave = List.of(measurementBuilder.city("Lviv").build(),
                measurementBuilder.city("Kyiv").build(), measurementBuilder.city("Kharkiv").build());
        for(var measurement: measurementsToSave){
            measurementService.saveNewMeasurement(measurement);
        }

        List<WeatherMeasurement> foundMeasurements = measurementService.retrieveAllMeasurements();
        Assertions.assertEquals(measurementsToSave.size(), measurementRepository.count());
        Assertions.assertEquals(measurementsToSave.size(), foundMeasurements.size());
        for(int i = 0; i < foundMeasurements.size(); i++){
            Assertions.assertEquals(measurementsToSave.get(i).toString(), foundMeasurements.get(i).toString());
        }

    }
    @Test
    @Description("City not found exception is thrown control")
    @Disabled
    @Order(3)
    public void FindByCityAndDateThrowsException(){
        WeatherMeasurement measurementToSave = measurementBuilder.city("Donetsk").build();
        measurementService.saveNewMeasurement(measurementToSave);
        Assert.assertThrows(CityMeasurementsAreNotFound.class,
                () -> measurementService.findByCityAndFromToTime("Donetsk", curTime.minusDays(2), curTime.minusDays(1)));

    }

    @Test
    @Description("Finds by target city and date successfully")
    @Disabled
    @Order(4)
    public void FindByCityAndDateReturnsMultipleCities() throws CityMeasurementsAreNotFound {
        WeatherMeasurement targetMeasurement =  measurementBuilder.city("Dnipro").build();
        measurementService.saveNewMeasurement(targetMeasurement);
        measurementService.saveNewMeasurement( measurementBuilder.city("Lugansk").build());

        List<WeatherMeasurement> retrievedMeasurements = measurementService.findByCityAndFromToTime(
                targetMeasurement.getCity(), curTime.minusDays(1), curTime.plusDays(1));
        Assertions.assertEquals(1, retrievedMeasurements.size());
        Assertions.assertEquals(targetMeasurement.toString(), retrievedMeasurements.get(0).toString());


    }


}