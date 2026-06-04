package de.fherfurt.FitnessTrackerSystem.services.contoller;


import de.fherfurt.FitnessTrackerSystem.models.DailyDashboard;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.services.DashboardService;
import de.fherfurt.FitnessTrackerSystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    @GetMapping("/daily")
    public ResponseEntity<DailyDashboard> getDailyDashboard(
            @RequestParam int userId,
            @RequestParam LocalDate date) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(dashboardService.getDailyDashboard(user, date));
    }
}



