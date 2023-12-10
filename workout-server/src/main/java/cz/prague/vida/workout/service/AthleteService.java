package cz.prague.vida.workout.service;

import cz.prague.vida.workout.entity.Athlete;

import java.util.Optional;

public interface AthleteService {
    Optional<Athlete> getCurrentAthlete();
}
