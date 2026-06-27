package de.fherfurt.FitnessTrackerSystem.contoller;

import de.fherfurt.FitnessTrackerSystem.contoller.request.AddExerciseRequest;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlanExercise;
import de.fherfurt.FitnessTrackerSystem.services.WorkoutPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/workout_plan")
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutPlan>> getAllWorkoutPlan() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getWorkoutPlanById(@PathVariable("id") int id) {
        return workoutPlanService.getWorkoutPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WorkoutPlan> addWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        workoutPlanService.addWorkoutPlan(workoutPlan);
        return ResponseEntity.status(201).body(workoutPlan);
    }

    @PutMapping
    public ResponseEntity<Void> updateWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        workoutPlanService.updateWorkoutPlan(workoutPlan);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlanById(@PathVariable("id") int id) {
        workoutPlanService.deleteWorkoutPlan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-exercise")
    public ResponseEntity<WorkoutPlanExercise> addExerciseToWorkoutPlan(
            @RequestBody AddExerciseRequest request) {
        return ResponseEntity.ok(workoutPlanService.addExerciseToWorkoutPlan(
                request.getWorkoutPlan(),
                request.getExercise(),
                request.getSets(),
                request.getRepetitions()
        ));
    }

    @DeleteMapping("/{workoutPlanId}/exercise/{exerciseId}")
    public ResponseEntity<Void> removeExerciseFromWorkoutPlan(
            @PathVariable("workoutPlanId") int workoutPlanId,
            @PathVariable("exerciseId") int workoutPlanExerciseId) {

        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanById(workoutPlanId).orElse(null);
        workoutPlanService.removeExerciseFromWorkoutPlan(workoutPlan, workoutPlanExerciseId);
        return ResponseEntity.noContent().build();
    }
}
