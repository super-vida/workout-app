package cz.prague.vida.workout.controller;

import cz.prague.vida.strava.api.StravaV3Client;
import cz.prague.vida.strava.model.DetailedActivity;
import cz.prague.vida.workout.configuration.StravaConfiguration;
import cz.prague.vida.workout.dao.WorkoutRepository;
import cz.prague.vida.workout.entity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manage")
public class ActivityManagerController {

    @Value("${manageActions}")
    private List<String> manageActions;
    private final WorkoutRepository workoutRepository;

    private StravaConfiguration stravaConfiguration;

    @Autowired
    public ActivityManagerController(WorkoutRepository workoutRepository, StravaConfiguration stravaConfiguration) {
        this.workoutRepository = workoutRepository;
        this.stravaConfiguration = stravaConfiguration;
    }

    @RequestMapping("/form")
    public String manageShow(Model model) {
        model.addAttribute("manageActions", manageActions);
        return "manage/form";
    }

    @RequestMapping("/process")
    public String manageProcess(@RequestParam("action") String action, Model model) throws IOException, URISyntaxException, ParseException, InterruptedException {
        String result = "Action : " + action + " action has been done";

        if ("SYNCHRONIZE_NEW".equals(action)) {
            synchronizeNewActivities();
        } else if ("DELETE_ALL_AND_SYNCHRONIZE".equals(action)) {
            deleteAllAndSynchronizeActivities();
        }

        model.addAttribute("message", result);
        return "manage/confirmation";
    }

    private void deleteAllAndSynchronizeActivities() throws IOException, URISyntaxException, InterruptedException, ParseException {
        StravaV3Client strava = getStravaV3Client();
        workoutRepository.deleteAll();
        for (int i = 1; i < 60; i++) {
            List<DetailedActivity> activities = strava.getCurrentAthleteActivities(i, 30);
            for (DetailedActivity activity : activities) {
                Activity workout = convertWorkout(activity);
                workoutRepository.save(workout);
            }
        }
    }

    private void synchronizeNewActivities() throws IOException, URISyntaxException, InterruptedException, ParseException {
        StravaV3Client strava = getStravaV3Client();
        List<DetailedActivity> activities = strava.getCurrentAthleteActivities(1, 1);
        for (DetailedActivity activity : activities) {
            Activity workout = convertWorkout(activity);
            Optional<Activity> savedWorkout = workoutRepository.findById(workout.getId());
            if (savedWorkout.isEmpty()) {
                workoutRepository.save(workout);
            }
        }
    }

    private StravaV3Client getStravaV3Client() throws IOException, URISyntaxException, InterruptedException {
        return StravaV3Client.Builder.newInstance()
                .withClientId(stravaConfiguration.getClientId())
                .withClientSecret(stravaConfiguration.getClientSecret())
                .withRefreshToken(stravaConfiguration.getRefreshToken())
                .build();
    }

    private Activity convertWorkout(DetailedActivity activity) throws ParseException {
        Activity workout = new Activity();
        populateTimes(workout, activity);
        workout.setAchievementCount(activity.getAchievementCount());
        workout.setAthleteCount(activity.getAthleteCount());
        workout.setAverageSpeed(activity.getAverageSpeed());
        workout.setAverageWatts(activity.getAverageWatts());
        workout.setCalories(activity.getCalories());
        workout.setCommentCount(activity.getCommentCount());
        workout.setCommute(activity.isCommute());
        workout.setDescription(activity.getDescription());
        workout.setDeviceWatts(activity.isDeviceWatts());
        workout.setDistance(activity.getDistance());
        workout.setElapsedTime(activity.getElapsedTime());
        workout.setElevHigh(activity.getElevHigh());
        workout.setElevLow(activity.getElevLow());
        workout.setExternalId(activity.getExternalId());
        workout.setFlagged(activity.isFlagged());
        workout.setGearId(activity.getGearId());
        workout.setHasKudoed(activity.isHasKudoed());
        workout.setHideFromHome(activity.isHideFromHome());
        workout.setKilojoules(activity.getKilojoules());
        workout.setKudosCount(activity.getKudosCount());
        workout.setManual(activity.isManual());
        workout.setMaxSpeed(activity.getMaxSpeed());
        workout.setMaxWatts(activity.getMaxWatts());
        workout.setMovingTime(activity.getMovingTime());
        workout.setName(activity.getName());
        workout.setPhotoCount(activity.getPhotoCount());
        workout.setPrivateWorkout(activity.isPrivate());
        workout.setSportType(activity.getSportType());
        workout.setId(activity.getId());
        workout.setTimezone(activity.getTimezone());
        workout.setTotalElevationGain(activity.getTotalElevationGain());
        workout.setTotalPhotoCount(activity.getTotalPhotoCount());
        workout.setTrainer(activity.isTrainer());
        workout.setType(activity.getType());
        workout.setUploadId(activity.getUploadId());
        workout.setUploadIdStr(activity.getUploadIdStr());
        workout.setWeightedAverageWatts(activity.getWeightedAverageWatts());
        workout.setWorkoutType(activity.getWorkoutType());
        return workout;
    }

    private void populateTimes(Activity workout, DetailedActivity activity) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        workout.setStartDate(simpleDateFormat.parse(activity.getStartDate()));
        workout.setStartDateLocal(simpleDateFormat.parse(activity.getStartDateLocal()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workout.getStartDate());
        workout.setYear(calendar.get(Calendar.YEAR));
        workout.setMonth(calendar.get(Calendar.MONTH) + 1);
        workout.setWorkoutDay(calendar.get(Calendar.DAY_OF_YEAR));
    }
}
