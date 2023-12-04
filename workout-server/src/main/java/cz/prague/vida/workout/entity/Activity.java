package cz.prague.vida.workout.entity;

import cz.prague.vida.strava.model.SportType;
import cz.prague.vida.strava.model.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ACTIVITY")
public class Activity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "external_id")
    private String externalId = null;

    @Column(name = "upload_id")
    private Long uploadId = null;

    @Column(name = "workout_year")
    private Integer year;

    @Column(name = "workout_month")
    private Integer month;

    @Column(name = "workout_day")
    private Integer workoutDay;

//    @Column(name = "athlete")
//    private MetaAthlete athlete = null;

    @Column(name = "name")
    private String name = null;

    @Column(name = "distance")
    private Float distance = null;

    @Column(name = "moving_time")
    private Integer movingTime = null;

    @Column(name = "elapsed_time")
    private Integer elapsedTime = null;

    @Column(name = "total_elevation_gain")
    private Float totalElevationGain = null;

    @Column(name = "elev_high")
    private Float elevHigh = null;

    @Column(name = "elev_low")
    private Float elevLow = null;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ActivityType type = null;

    @Column(name = "sport_type")
    @Enumerated(EnumType.STRING)
    private SportType sportType = null;

    @Column(name = "start_date")
    private Date startDate = null;

    @Column(name = "start_date_local")
    private Date startDateLocal = null;

    @Column(name = "timezone")
    private String timezone = null;

//    @Column(name = "start_latlng")
//    private LatLng startLatlng = null;

//    @Column(name = "end_latlng")
//    private LatLng endLatlng = null;

    @Column(name = "achievement_count")
    private Integer achievementCount = null;

    @Column(name = "kudos_count")
    private Integer kudosCount = null;

    @Column(name = "comment_count")
    private Integer commentCount = null;

    @Column(name = "athlete_count")
    private Integer athleteCount = null;

    @Column(name = "photo_count")
    private Integer photoCount = null;

    @Column(name = "total_photo_count")
    private Integer totalPhotoCount = null;

//    @Column(name = "map")
//    private PolylineMap map = null;

    @Column(name = "trainer")
    private Boolean trainer = null;

    @Column(name = "commute")
    private Boolean commute = null;

    @Column(name = "manual")
    private Boolean manual = null;

    @Column(name = "private")
    private Boolean privateWorkout = null;

    @Column(name = "flagged")
    private Boolean flagged = null;

    @Column(name = "workout_type")
    private Integer workoutType = null;

    @Column(name = "upload_id_str")
    private String uploadIdStr = null;

    @Column(name = "average_speed")
    private Float averageSpeed = null;

    @Column(name = "max_speed")
    private Float maxSpeed = null;

    @Column(name = "has_kudoed")
    private Boolean hasKudoed = null;

    @Column(name = "hide_from_home")
    private Boolean hideFromHome = null;

    @Column(name = "gear_id")
    private String gearId = null;

    @Column(name = "kilojoules")
    private Float kilojoules = null;

    @Column(name = "average_watts")
    private Float averageWatts = null;

    @Column(name = "device_watts")
    private Boolean deviceWatts = null;

    @Column(name = "max_watts")
    private Integer maxWatts = null;

    @Column(name = "weighted_average_watts")
    private Integer weightedAverageWatts = null;

    @Column(name = "description")
    private String description = null;

//    @SerializedName("photos")
//    private PhotosSummary photos = null;

//    @SerializedName("gear")
//    private SummaryGear gear = null;

    @Column(name = "calories")
    private Float calories = null;
}
