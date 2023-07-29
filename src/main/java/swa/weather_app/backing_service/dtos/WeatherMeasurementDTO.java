package swa.weather_app.backing_service.dtos;

import java.time.LocalDateTime;

public class WeatherMeasurementDTO {
    private LocalDateTime time;

    private String city;

    private double temperature;

    private int pressure;

    private int humidity;

    private WindDTO wind;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public WindDTO getWind() {
        return wind;
    }

    public void setWind(WindDTO wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
                "time=" + time +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", wind=" + wind +
                '}';
    }
}
