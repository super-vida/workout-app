package cz.prague.vida.workout.service;

import cz.prague.vida.workout.entity.ActivityStats;
import cz.prague.vida.workout.entity.Athlete;

import java.util.Optional;

public interface ActivityStatsService {

    void delete(ActivityStats activityStats);
    void save(ActivityStats ActivityStats);
}
