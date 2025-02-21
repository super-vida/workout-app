package cz.prague.vida.workout.controller;

import cz.prague.vida.strava.api.StravaV3Client;
import cz.prague.vida.strava.model.DetailedActivity;
import cz.prague.vida.workout.configuration.StravaConfiguration;
import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.entity.ActivityStream;
import cz.prague.vida.workout.service.ActivityService;
import cz.prague.vida.workout.service.ActivityStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;

@Controller
@RequestMapping("/manage")
public class ActivityManagerController {

    @Value("${manageActions}")
    private List<String> manageActions;
    private final ActivityService activityService;
    private final ActivityStreamService activityStreamService;

    private StravaConfiguration stravaConfiguration;

    Logger logger = LoggerFactory.getLogger(ActivityManagerController.class);

    @Autowired
    public ActivityManagerController(ActivityService activityService, ActivityStreamService activityStreamService, StravaConfiguration stravaConfiguration) {
        this.activityService = activityService;
        this.activityStreamService = activityStreamService;
        this.stravaConfiguration = stravaConfiguration;
    }

    @RequestMapping("/form")
    public String manageShow(Model model) {
        model.addAttribute("manageActions", manageActions);
        return "manage/form";
    }

    @RequestMapping("/process")
    public String manageProcess(@RequestParam("action") String action, Model model) throws Exception {
        String result = "Action : " + action + " action has been done";

        if ("SYNCHRONIZE_NEW".equals(action)) {
            synchronizeNewActivities();
        } else if ("DELETE_ALL_AND_SYNCHRONIZE".equals(action)) {
            deleteAllAndSynchronizeActivities();
        }

        model.addAttribute("message", result);
        return "manage/confirmation";
    }

    private void deleteAllAndSynchronizeActivities() throws Exception {
        StravaV3Client strava = getStravaV3Client();
        activityService.deleteAll();
        Long count = activityService.count();
        for (int i = 1; i < 30; i++) {
            List<DetailedActivity> activities = strava.getCurrentAthleteActivities(i, 20);
            for (DetailedActivity activity : activities) {
                Activity workout = convertWorkout(activity);
                activityService.save(workout);
                long start = System.currentTimeMillis();
                List<Map> streams = strava.findActivityStreams(activity.getId(), new String[]{"latlng", "time", "distance", "heartrate", "watts", "altitude", "cadence", "temp", "moving", "grade_smooth", "velocity_smooth"});
                logger.trace("findActivityStreams last " + (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
                populateStreams(streams, workout);
                logger.trace("populateStreams last " + (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
                logger.trace("save last " + (System.currentTimeMillis() - start));
            }
        }
    }

    private void synchronizeNewActivities() throws IOException, URISyntaxException, InterruptedException, ParseException {

        Date newestActivity = activityService.findNewestActivity();

        System.out.println(newestActivity);

       // return;

        StravaV3Client strava = getStravaV3Client();
        for (int i = 1; i < 30; i++) {
            List<DetailedActivity> activities = strava.getCurrentAthleteActivities(i, 30);
            for (DetailedActivity activity : activities) {
                Activity workout = convertWorkout(activity);
                Optional<Activity> savedWorkout = activityService.findById(workout.getId());
                if (savedWorkout.isEmpty()) {
                    activityService.save(workout);
                }
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

    public void populateStreams(List<Map> streams, Activity activity) throws Exception {
        List<ActivityStream> list = new ArrayList<>();
        activity.setActivityStreams(new ArrayList<>());
        int size = ((List) streams.get(0).get("data")).size();
        ActivityStream s = new ActivityStream();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < streams.size(); j++) {
                Map map = (Map) streams.get(j);
                String type = (String) map.get("type");
                Object object = ((List) map.get("data")).get(i);
                if ("latlng".equals(type)) {
                   s.setLatlng(String.valueOf(object));
                } else if ("time".equals(type)) {
                    s.setTime((Double) object);
                } else if ("distance".equals(type)) {
                    s.setDistance((Double) object);
                } else if ("heartrate".equals(type)) {
                    s.setHeartrate((Double) object);
                } else if ("watts".equals(type)) {
                    s.setWats((Double) object);
                } else if ("altitude".equals(type)) {
                    s.setAltitude((Double) object);
                } else if ("cadence".equals(type)) {
                    s.setCadence((Double) object);
                } else if ("temp".equals(type)) {
                    s.setTemp((Double) object);
                } else if ("moving".equals(type)) {
                    s.setMoving((Boolean) object);
                } else if ("grade_smooth".equals(type)) {
                    s.setGradeSmooth((Double) object);
                } else if ("velocity_smooth".equals(type)) {
                    s.setVelocitySmooth((Double) object);
                }
            }
            s.setPointer(i);
            s.setActivityId(activity.getId());
           // activity.getActivityStreams().add(s);
            list.add(s);
            s = new ActivityStream();
        }
        activityStreamService.saveAll(list);
    }
}
