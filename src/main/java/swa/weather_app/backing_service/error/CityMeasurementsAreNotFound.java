package swa.weather_app.backing_service.error;

public class CityMeasurementsAreNotFound extends Exception{
    public CityMeasurementsAreNotFound() {
        super();
    }

    public CityMeasurementsAreNotFound(String message) {
        super(message);
    }

    public CityMeasurementsAreNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CityMeasurementsAreNotFound(Throwable cause) {
        super(cause);
    }

    protected CityMeasurementsAreNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
