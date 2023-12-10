package cz.prague.vida.workout.controller;

import cz.prague.vida.workout.entity.Athlete;
import cz.prague.vida.workout.service.AthleteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/athlete")
public class AthleteController {

    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @GetMapping("/current")
    public String getCurrentAthlete(Model model) {
        Optional<Athlete> athlete = athleteService.getCurrentAthlete();
        if (athlete.isPresent()){
            model.addAttribute("athlete", athlete.get());
        }
        return "athlete/athlete-detail";
    }
}









