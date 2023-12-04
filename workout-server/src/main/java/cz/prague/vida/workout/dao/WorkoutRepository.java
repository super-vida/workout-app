package cz.prague.vida.workout.dao;

import cz.prague.vida.workout.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Activity, Long> {

}
