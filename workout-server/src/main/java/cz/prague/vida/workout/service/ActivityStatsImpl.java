package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.ActivityStatsRepository;
import cz.prague.vida.workout.entity.ActivityStats;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityStatsImpl implements ActivityStatsService {

    private final ActivityStatsRepository activityStatsRepository;

    @Autowired
    public ActivityStatsImpl(ActivityStatsRepository activityStatsRepository) {
        this.activityStatsRepository = activityStatsRepository;
    }


    @Override
    @Transactional
    public void delete(ActivityStats activityStats) {
        activityStatsRepository.delete(activityStats);
    }

    @Override
    @Transactional
    public void save(ActivityStats activityStats) {
        activityStatsRepository.save(activityStats);
    }
}
