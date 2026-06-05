package de.fherfurt.FitnessTrackerSystem.contoller;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.NutritionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.services.NutritionService;
import de.fherfurt.FitnessTrackerSystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/nutrition")
public class NutritionController {
    private final NutritionService nutritionService;
    private final UserService userService;

    public NutritionController(NutritionService nutritionService, UserService userService){
        this.nutritionService = nutritionService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Nutrition>> getAllNutrition(){
        return ResponseEntity.ok(nutritionService.getAllNutrition());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nutrition> getNutritionById(@PathVariable("id") int id){
        return nutritionService.getNutritionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addNutrition(@RequestBody Nutrition nutrition){
        nutritionService.addNutrition(nutrition);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateNutrition(@RequestBody Nutrition nutrition) {
        nutritionService.updateNutrition(nutrition);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutritionById(@PathVariable("id") int id) {
        nutritionService.deleteNutritionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nutrition_summary")
    public ResponseEntity<NutritionSummary> getDailyNutritionSummary(
            @RequestParam int userId,
            @RequestParam LocalDate date) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(nutritionService.getDailyNutritionSummary(user, date));
    }
}
