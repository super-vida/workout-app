package cz.prague.vida.workout.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@ConfigurationProperties("strava")
public class StravaConfiguration {

    private String clientId;
    private String clientSecret;
    private String refreshToken;
}
