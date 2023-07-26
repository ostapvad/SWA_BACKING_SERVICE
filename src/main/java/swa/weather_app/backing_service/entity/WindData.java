package swa.weather_app.backing_service.entity;


//  "wind": {
//          "speed": 0.62,
//          "deg": 349,
//          "gust": 1.18
//          }

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WindData {
    private Float speed;
    private Integer deg;
    private Float gust;


}
