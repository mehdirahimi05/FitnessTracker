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
- getMostActiveUserByAmountOfSessions
- getTrainingStreak
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
- getBodyFatPercentageProgress

## DashboardService

- getDailyDashboard
- getWeeklyDashboard
- getMonthlyDashboard