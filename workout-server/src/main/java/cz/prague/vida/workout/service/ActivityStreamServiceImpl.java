package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.ActivityRepository;
import cz.prague.vida.workout.dao.ActivityStreamRepository;
import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.entity.ActivityStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityStreamServiceImpl implements ActivityStreamService {

    private final ActivityStreamRepository activityStreamRepository;

    @Autowired
    public ActivityStreamServiceImpl(ActivityStreamRepository activityStreamRepository) {
        this.activityStreamRepository = activityStreamRepository;
    }
    @Transactional
    public void save(ActivityStream activityStream) {
        activityStreamRepository.saveAndFlush(activityStream);
    }

    @Override
    @Transactional
    public void saveAll(List<ActivityStream> activityStreams) {
        activityStreamRepository.saveAll(activityStreams);

    }

}
