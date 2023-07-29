package swa.weather_app.backing_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindDTO {
    @JsonProperty("speed")
    private double speed;

    @JsonProperty("deg")
    private int deg;

    @JsonProperty("gust")
    private double gust;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
