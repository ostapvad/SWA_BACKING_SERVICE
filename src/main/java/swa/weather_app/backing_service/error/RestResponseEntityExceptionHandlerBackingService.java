package swa.weather_app.backing_service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import swa.weather_app.backing_service.entity.ErrorMessage;


@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandlerBackingService extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CityMeasurementsAreNotFound.class)
    public ResponseEntity<ErrorMessage>  MeasurementsNotFoundException(CityMeasurementsAreNotFound exception){
        ErrorMessage msg = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }



}
