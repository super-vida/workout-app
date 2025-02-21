package cz.prague.vida.workout.dao;

import cz.prague.vida.workout.entity.ActivityStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityStreamRepository extends JpaRepository<ActivityStream, Long> {

}
