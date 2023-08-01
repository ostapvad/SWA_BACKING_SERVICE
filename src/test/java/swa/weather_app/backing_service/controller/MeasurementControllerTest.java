package swa.weather_app.backing_service.controller;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import org.springframework.test.web.servlet.MockMvc;
import swa.weather_app.backing_service.dtos.WeatherMeasurementDTO;
import swa.weather_app.backing_service.entity.WeatherMeasurement;
import swa.weather_app.backing_service.entity.WindData;
import swa.weather_app.backing_service.error.CityMeasurementsAreNotFound;
import swa.weather_app.backing_service.service.MeasurementService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MeasurementController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MeasurementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MeasurementService measurementService;
    LocalDateTime curTime =  LocalDateTime.of(2023, 10, 1, 1, 1, 1);
    WindData curWindData = WindData.builder().deg(35).speed(10f).deg(15).build();
    WeatherMeasurement newMeasurement = WeatherMeasurement.builder().time(curTime).city("Odesa").temperature(35F).
            pressure(100).wind(curWindData).build();

    @Test
    //@Disabled
    @DisplayName("Ensures that post request has Created response")
    @Order(1)
    void PostResponseIsCorrect() throws  Exception{
        MockHttpServletResponse response =  mockMvc.perform(post("/measurements").
                contentType(MediaType.APPLICATION_JSON).content("{\"city\": \"Odesa\"}")).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    @Test
    //@Disabled
    @DisplayName("Checks that it's always OK response for retrieve all measurements request")
    @Order(2)
    void GetAllMeasurementsReturnsAlwaysCorrectResponse() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/measurements/all")).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }


    @Test
   // @Disabled
    @DisplayName("Retrieve measurements by city and time succeeds test")
    @Order(3)
    void RetrieveMeasurementByCityAndDateSucceeds() throws Exception {

        when(measurementService.findByCityAndFromToTime("Odesa", curTime, curTime.plusDays(1))).thenReturn(
                List.of(newMeasurement));
        String url = String.format("/measurements?city=%s&from=%s&to=%s", newMeasurement.getCity(),
                newMeasurement.getTime().toString(), newMeasurement.getTime().plusDays(1).toString());

        MockHttpServletResponse response = mockMvc.perform(
              get(url)
        ).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
   //@Disabled
    @DisplayName("Get measurement by city fails test")
    @Order(4)
    void RetrieveMeasurementByCityAndDateFails() throws Exception {
        when(measurementService.findByCityAndFromToTime("Odesa", curTime, curTime.minusDays(1))).thenThrow(
                CityMeasurementsAreNotFound.class);
        String url = String.format("/measurements?city=%s&from=%s&to=%s", newMeasurement.getCity(),
                newMeasurement.getTime().toString(), newMeasurement.getTime().minusDays(1).toString());

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
        ).andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

}
