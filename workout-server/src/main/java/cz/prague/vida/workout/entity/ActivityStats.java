package cz.prague.vida.workout.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "activity_stats")
public class ActivityStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "athlete_id")
    private Long athletId;

    @Column(name = "biggest_ride_distance")
    private Double biggestRideDistance = null;

    @Column(name = "biggest_climb_elevation_gain")
    private Double biggestClimbElevationGain = null;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_stats_id")
    private List<ActivityTotal> activityTotals;
}
