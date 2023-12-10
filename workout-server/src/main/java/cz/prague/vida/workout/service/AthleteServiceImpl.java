package cz.prague.vida.workout.service;

import cz.prague.vida.workout.dao.AthleteRepository;
import cz.prague.vida.workout.entity.Athlete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository athleteRepository;

    @Autowired
    public AthleteServiceImpl(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Optional<Athlete> getCurrentAthlete() {
        return athleteRepository.findById(24085813L);
    }

    @Override
    public void save(Athlete athlete) {
        athleteRepository.save(athlete);
    }
}
