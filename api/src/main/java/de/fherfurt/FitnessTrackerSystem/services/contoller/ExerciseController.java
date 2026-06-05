package de.fherfurt.FitnessTrackerSystem.services.contoller;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercise() {
        return ResponseEntity.ok(exerciseService.getAllExercise());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") int id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addExercise(@RequestBody Exercise exercise) {
        exerciseService.addExercise(exercise);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateExercise(@RequestBody Exercise exercise) {
        exerciseService.updateExercise(exercise);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseById(@PathVariable("id") int id) {
        exerciseService.deleteExerciseById(id);
        return ResponseEntity.noContent().build();
    }
}
