package swa.weather_app.backing_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity(name = "measurements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "temperature")
    private Float temperature;


    @Column(name = "pressure")
    private Integer pressure;

    @Column(name = "humidity")
    private int humidity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "speed", column = @Column(name = "wind_speed")),
            @AttributeOverride(name = "deg", column = @Column(name = "wind_deg")),
            @AttributeOverride(name = "gust", column = @Column(name = "wind_gust"))
    })
    private WindData wind;

    @JsonIgnore
    public Long getId(){
        return id;
    }

}

