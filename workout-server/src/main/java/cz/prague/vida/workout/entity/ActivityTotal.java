package cz.prague.vida.workout.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "activity_total")
public class ActivityTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "activity_stats_id")
    private Long activityStatsId;

     @Column(name = "count")
    private Integer count = null;

     @Column(name = "distance")
    private Float distance = null;

     @Column(name = "moving_time")
    private Integer movingTime = null;

     @Column(name = "elapsed_time")
    private Integer elapsedTime = null;

     @Column(name = "elevation_gain")
    private Float elevationGain = null;

     @Column(name = "achievement_count")
    private Integer achievementCount = null;
}
