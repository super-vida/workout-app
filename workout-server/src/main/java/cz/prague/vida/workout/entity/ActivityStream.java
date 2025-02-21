package cz.prague.vida.workout.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "activity_stream")
public class ActivityStream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "pointer")
    private Integer pointer;
    @Column(name = "activity_id")
    private Long activityId;
    @Column(name = "temp")
    private Double temp;
    @Column(name = "wats")
    private Double wats;
    @Column(name = "moving")
    private Boolean moving;
    @Column(name = "latlng")
    private String latlng;
    @Column(name = "velocity_smooth")
    private Double velocitySmooth;
    @Column(name = "grade_smooth")
    private Double gradeSmooth;
    @Column(name = "cadence")
    private Double cadence;
    @Column(name = "distance")
    private Double distance;
    @Column(name = "heartrate")
    private Double heartrate;
    @Column(name = "altitude")
    private Double altitude;
    @Column(name = "time")
    private Double time;
}
