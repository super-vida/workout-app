package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.ActivityRepository;
import cz.prague.vida.workout.entity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAllByOrderByStartDateDesc();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    @Override
    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        activityRepository.deleteAll();
    }

    @Override
    public Page<Activity> findAll(PageRequest pageRequest) {
        return activityRepository.findAll(pageRequest);
    }
}
