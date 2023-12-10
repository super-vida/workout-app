package cz.prague.vida.workout.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "athlete")
public class Athlete {

    @Column(name = "id")
    @Id
    private Long id = 24085813L;

    @Column(name = "username")
    private String username = "honza";

    @Column(name = "password")
    private String password = "{bcrypt}$2a$10$EZRUEVpR3CrThXDjOMuSZ.QYK.2j5C92pOLJAz3WN8XZAGO8AHnUy";

    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

    @Column(name = "resource_state")
    private Integer resourceState;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "profile_medium")
    private String profileMedium;

    @Column(name = "profile")
    private String profile;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "follower_count")
    private Integer followerCount;

    @Column(name = "friend_count")
    private Integer friendCount;

    @OneToMany
    @JoinColumn(name = "athlete_id")
    private List<Activity> activities;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "athlete_id")
    private ActivityStats activityStats;
}
