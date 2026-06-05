package de.fherfurt.FitnessTrackerSystem.services.contoller;


import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.services.ActivityTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activity_type")
public class ActivityTypeController {
    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService){
        this.activityTypeService = activityTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityType>> getAllActivityType() {
        return ResponseEntity.ok(activityTypeService.getAllActivityType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityType> getActivityTypeById(@PathVariable("id") int id) {
        return activityTypeService.getActivityTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addActivityType(@RequestBody ActivityType activityType) {
        activityTypeService.addActivityType(activityType);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateActivityType(@RequestBody ActivityType activityType) {
        activityTypeService.updateActivityType(activityType);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityTypeById(@PathVariable("id") int id) {
        activityTypeService.deleteActivityTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
