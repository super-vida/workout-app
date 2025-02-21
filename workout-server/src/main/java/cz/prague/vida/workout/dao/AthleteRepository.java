package cz.prague.vida.workout.dao;

import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
