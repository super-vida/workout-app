package cz.prague.vida.workout.dao;

import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.entity.ActivityStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityStatsRepository extends JpaRepository<ActivityStats, Long> {

}
