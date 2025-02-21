package cz.prague.vida.workout.dao;

import cz.prague.vida.workout.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    public List<Activity> findAllByOrderByStartDateDesc();


}
