package cz.prague.vida.workout.controller;

import cz.prague.vida.workout.entity.Activity;
import cz.prague.vida.workout.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

//    @GetMapping("/list")
//    public String listActivities(Model model) {
//        List<Activity> activities = activityService.findAll();
//        model.addAttribute("activities", activities);
//        return "activity/activity-list";
//    }



    @GetMapping("/list")
    public String listActivities(Model model, @RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 15, Sort.by("startDate").descending());
        Page<Activity> activities = activityService.findAll(pageRequest);
        model.addAttribute("activities", activities);
        model.addAttribute("currentPage", page);
        return "activity/activity-list";
    }
}









