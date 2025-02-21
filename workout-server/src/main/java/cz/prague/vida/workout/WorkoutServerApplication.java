package cz.prague.vida.workout;

import cz.prague.vida.workout.configuration.StravaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(StravaConfiguration.class)
public class WorkoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkoutServerApplication.class, args);
    }
}
