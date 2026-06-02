package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.DailyDashboard;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;

public interface IDashboardService {
    DailyDashboard getDailyDashboard(User user, LocalDate date);
}
