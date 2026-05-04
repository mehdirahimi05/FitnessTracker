package de.fherfurt.FitnessTrackerSystem.models.utils;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import java.util.Comparator;

public class TrainingsSessionCaloriesComparator implements Comparator<TrainingsSession> {

    @Override
    public int compare(TrainingsSession trainingsSession1, TrainingsSession trainingsSession2) {
        if (trainingsSession1 == null || trainingsSession2 == null) return 0;

        return Integer.compare(trainingsSession2.getBurnedCalories(), trainingsSession1.getBurnedCalories());
    }
}
