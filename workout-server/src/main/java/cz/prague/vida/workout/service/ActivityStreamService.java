package cz.prague.vida.workout.service;

import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.entity.ActivityStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ActivityStreamService {

    void save(ActivityStream activityStream);
    void saveAll(List<ActivityStream> activityStreams);


}
