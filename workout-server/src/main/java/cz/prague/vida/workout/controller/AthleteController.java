package cz.prague.vida.workout.controller;

import cz.prague.vida.strava.api.StravaV3Client;
import cz.prague.vida.strava.model.ActivityStats;
import cz.prague.vida.strava.model.ActivityTotal;
import cz.prague.vida.strava.model.SportType;
import cz.prague.vida.workout.configuration.StravaConfiguration;
import cz.prague.vida.workout.entity.Athlete;
import cz.prague.vida.workout.service.ActivityStatsService;
import cz.prague.vida.workout.service.AthleteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/athlete")
public class AthleteController {

    private final AthleteService athleteService;
    private final ActivityStatsService activityStatsService;

    private final StravaConfiguration stravaConfiguration;

    public AthleteController(AthleteService athleteService, StravaConfiguration stravaConfiguration, ActivityStatsService activityStatsService) {
        this.athleteService = athleteService;
        this.stravaConfiguration = stravaConfiguration;
        this.activityStatsService = activityStatsService;
    }

    private StravaV3Client getStravaV3Client() throws IOException, URISyntaxException, InterruptedException {
        return StravaV3Client.Builder.newInstance()
                .withClientId(stravaConfiguration.getClientId())
                .withClientSecret(stravaConfiguration.getClientSecret())
                .withRefreshToken(stravaConfiguration.getRefreshToken())
                .build();
    }

    @GetMapping("/current")
    public String getCurrentAthlete(Model model) {
        Optional<Athlete> athlete = athleteService.getCurrentAthlete();
        if (athlete.isPresent()){
            model.addAttribute("athlete", athlete.get());
        }
        return "athlete/athlete-detail";
    }

    @GetMapping("/refresh")
    public String refreshAtleteData(Model model) throws IOException, URISyntaxException, InterruptedException {
        Optional<Athlete> athlete = athleteService.getCurrentAthlete();
        activityStatsService.delete(athlete.get().getActivityStats());
        if (athlete.isPresent()){
            ActivityStats athleteStats = getStravaV3Client().getAthleteStats(athlete.get().getId());
            populateAthleteStats(athlete.get(), athleteStats);
            model.addAttribute("athlete", athlete.get());
            activityStatsService.save(athlete.get().getActivityStats());
        }
        return "athlete/athlete-detail";
    }

    private void populateAthleteStats(Athlete athlete, ActivityStats athleteStats) {
        athlete.setActivityStats(new cz.prague.vida.workout.entity.ActivityStats());
        athlete.getActivityStats().setId(athlete.getId());
        athlete.getActivityStats().setBiggestClimbElevationGain(athleteStats.getBiggestClimbElevationGain());
        athlete.getActivityStats().setBiggestRideDistance(athleteStats.getBiggestRideDistance());
        athlete.getActivityStats().setActivityTotals(new ArrayList<>());
        if (athleteStats.getAllRideTotals() != null){
            ActivityTotal allRideTotals = athleteStats.getAllRideTotals();
            cz.prague.vida.workout.entity.ActivityTotal activityTotal = new cz.prague.vida.workout.entity.ActivityTotal();
            activityTotal.setTotalType("ALL_RIDE_TOTALS");
            activityTotal.setAchievementCount(allRideTotals.getAchievementCount());
            activityTotal.setCount(allRideTotals.getCount());
            activityTotal.setDistance(allRideTotals.getDistance());
            activityTotal.setElapsedTime(allRideTotals.getElapsedTime());
            activityTotal.setElevationGain(allRideTotals.getElevationGain());
            activityTotal.setMovingTime(allRideTotals.getMovingTime());
            activityTotal.setType(SportType.RIDE.getValue());
            activityTotal.setActivityStatsId(athlete.getActivityStats().getId());
            athlete.getActivityStats().getActivityTotals().add(activityTotal);
        }
        if (athleteStats.getYtdRideTotals() != null){
            ActivityTotal allRideTotals = athleteStats.getYtdRideTotals();
            cz.prague.vida.workout.entity.ActivityTotal activityTotal = new cz.prague.vida.workout.entity.ActivityTotal();
            activityTotal.setTotalType("YEAR_TO_DAY_RIDE_TOTALS");
            activityTotal.setAchievementCount(allRideTotals.getAchievementCount());
            activityTotal.setCount(allRideTotals.getCount());
            activityTotal.setDistance(allRideTotals.getDistance());
            activityTotal.setElapsedTime(allRideTotals.getElapsedTime());
            activityTotal.setElevationGain(allRideTotals.getElevationGain());
            activityTotal.setMovingTime(allRideTotals.getMovingTime());
            activityTotal.setType(SportType.RIDE.getValue());
            activityTotal.setActivityStatsId(athlete.getActivityStats().getId());
            athlete.getActivityStats().getActivityTotals().add(activityTotal);
        }
    }
}









