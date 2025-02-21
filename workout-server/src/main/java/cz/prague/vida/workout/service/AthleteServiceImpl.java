package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.AthleteRepository;
import cz.prague.vida.workout.entity.ActivityStats;
import cz.prague.vida.workout.entity.ActivityTotal;
import cz.prague.vida.workout.entity.Athlete;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository athleteRepository;

    private EntityManager entityManager;

    @Autowired
    public AthleteServiceImpl(AthleteRepository athleteRepository, EntityManager entityManager) {
        this.athleteRepository = athleteRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Athlete> getCurrentAthlete() {
        return athleteRepository.findById(24085813L);
    }

    @Override
    public void save(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    @Override
    @Transactional
    public void updateStats(Athlete athlete) {
        Athlete athlete2 = entityManager.find(Athlete.class, athlete.getId());
        athlete2.setActivityStats(null);
        entityManager.merge(athlete2);
        entityManager.flush();
        // entityManager.merge(athlete.getActivityStats());
//        entityManager.remove(athlete.getActivityStats());
        athlete2 = entityManager.find(Athlete.class, athlete.getId());
        athlete2.setActivityStats(athlete.getActivityStats());
        entityManager.merge(athlete2);
    }
}
