package cz.prague.vida.workout.service;

import cz.prague.vida.workout.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> findAll();

    Optional<Activity> findById(Long id);

    void save(Activity activity);

    void deleteById(Long id);

    void deleteAll();

    Page<Activity> findAll(PageRequest pageRequest);
}
