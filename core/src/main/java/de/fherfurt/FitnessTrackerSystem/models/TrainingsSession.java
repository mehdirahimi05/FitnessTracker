package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;

/**
 * Data model representing a specific training session within the fitness tracker.
 * This class supports serialization for storage, comparison for sorting by date,
 * and deep cloning to maintain data integrity.
 *
 * @author Mehdi Rahimi
 */
@Getter
@Setter
@AllArgsConstructor
public class TrainingsSession implements Serializable, Comparable<TrainingsSession>, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private int durationInMinute;
    private float distanceInKm;
    private int burnedCalories;
    private ActivityType activityType;
    private User user;

    /**
     * Compares this session with another to allow sorting by date.
     * The sorting is performed in descending order (newest sessions first).
     *
     * @param other The other session to compare against.
     * @return A negative integer, zero, or a positive integer based on the date comparison.
     */
    @Override
    public int compareTo(TrainingsSession other) {
        return other.date.compareTo(this.date);
    }

    /**
     * Creates and returns a deep copy of this training session.
     * The associated user is also cloned to ensure independence between instances.
     *
     * @return A cloned instance of TrainingsSession.
     * @throws RuntimeException if cloning fails.
     */
    @Override
    public TrainingsSession clone() {
        try {
            TrainingsSession cloned = (TrainingsSession) super.clone();
            if (this.user != null) {
                cloned.user = this.user.clone();
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning of TrainingsSession failed", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingsSession that = (TrainingsSession) o;
        return durationInMinute == that.durationInMinute &&
                Float.compare(distanceInKm, that.distanceInKm) == 0 &&
                burnedCalories == that.burnedCalories &&
                Objects.equals(date, that.date) &&
                activityType == that.activityType &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, durationInMinute, distanceInKm, burnedCalories, activityType, user);
    }

    @Override
    public String toString() {
        return "TrainingsSession{" +
                "date=" + date +
                ", duration=" + durationInMinute + "min" +
                ", distance=" + distanceInKm + "km" +
                ", calories=" + burnedCalories +
                ", type=" + activityType +
                ", user=" + (user != null ? user.getLastName() : "null") +
                '}';
    }
}