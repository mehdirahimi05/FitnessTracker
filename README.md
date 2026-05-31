# Usecases

## User

- CRUD
- signUp
- login
- logout *(Placeholder – kommt mit Spring Security)*

## UserDetails

*(nur CRUD – über UserRepository via Cascade)*

## TrainingsSession

- CRUD
- getTotalTrainingTimeByUser
- getTotalCaloriesBurnedByUser
- getMostActiveUserByAmountOfSessions
- getPersonalBest
- getTrainingStreak
- getImprovement
- filterSessions

## ActivityType

*(nur CRUD)*

## WorkoutPlan

- CRUD
- addExerciseToWorkoutPlan
- removeExerciseFromWorkoutPlan
- getExercisesInPlan
- getWorkoutPlansByDay

## Exercise

*(nur CRUD)*

## WorkoutPlanExercise

*(nur CRUD – über WorkoutPlanService)*

## Nutrition

- CRUD
- setDailyGoal
- getDailyNutritionSummary
- hasReachedDailyGoal
- setReminderTime
- getReminderTime

## BodyMeasurement

- CRUD
- calculateBMI
- getLatestMeasurement
- getMeasurementHistory
- getWeightProgress

## DashboardService

- getDailyDashboard
- getWeeklyDashboard
- getMonthlyDashboard