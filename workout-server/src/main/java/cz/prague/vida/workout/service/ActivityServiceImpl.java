package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.ActivityRepository;
import cz.prague.vida.workout.dao.AthleteRepository;
import cz.prague.vida.workout.entity.Activity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private EntityManager entityManager;

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, EntityManager entityManager) {
        this.activityRepository = activityRepository;
        this.entityManager = entityManager;
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
    public Long count(){
        return activityRepository.count();
    }

    @Override
    @Transactional
    public void save(Activity activity) {
        activityRepository.saveAndFlush(activity);
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
    public Date findNewestActivity() {
        Activity singleResult = (Activity) entityManager.createQuery("select a from Activity a where a.startDate = (select max(startDate) from Activity )").getSingleResult();
        return singleResult != null ? singleResult.getStartDate() : new Date(0);
    }

    @Override
    public Page<Activity> findAll(PageRequest pageRequest) {
        return activityRepository.findAll(pageRequest);
    }
}
