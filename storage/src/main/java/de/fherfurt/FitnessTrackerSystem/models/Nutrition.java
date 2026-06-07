package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing a single nutrition entry for a user.
 *
 * <p>Mapped to the {@code nutrition} table. Each record captures the
 * macronutrient breakdown of one meal on a specific date. A user can
 * have multiple nutrition entries, enabling dietary tracking over time.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see User
 * @see MealType
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "nutrition")
public class Nutrition {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nutritionId;

    /**
     * The user this nutrition entry belongs to.
     *
     * <p>Many nutrition entries can belong to one user ({@code @ManyToOne}).
     * The foreign key column in the database is {@code user_id}.</p>
     *
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Total caloric value of this meal, in kilocalories (kcal).
     */
    private int calories;

    /**
     * Protein content of this meal, in grams.
     */
    private int proteinInGram;

    /**
     * Carbohydrate content of this meal, in grams.
     */
    private int carbohydratesInGram;

    /**
     * Fat content of this meal, in grams.
     */
    private int fatInGram;

    /**
     * The type/category of this meal (e.g. {@code BREAKFAST}, {@code LUNCH}, {@code DINNER}).
     * Persisted as its enum name string via {@code @Enumerated(EnumType.STRING)}.
     *
     * @see MealType
     */
    @Enumerated(EnumType.STRING)
    private MealType mealTyp;

    /**
     * The date on which this meal was consumed.
     * Used to aggregate daily nutritional intake across entries.
     */
    private LocalDate date;
}