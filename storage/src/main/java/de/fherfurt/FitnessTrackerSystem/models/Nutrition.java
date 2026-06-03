package de.fherfurt.FitnessTrackerSystem.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "nutrition")
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nutritionId;
    @ManyToOne // many from me belong to one
    @JoinColumn(name = "user_id")
    private User user;
    private int calories;
    private int proteinInGram;
    private int carbohydratesInGram;
    private int fatInGram;
    @Enumerated(EnumType.STRING)
    private MealTyp mealTyp;
    private LocalDate date;
}
