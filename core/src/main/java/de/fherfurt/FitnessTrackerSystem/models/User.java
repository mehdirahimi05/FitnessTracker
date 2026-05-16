package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;

/**
 * Data model representing a user within the Fitness Tracker System.
 * This class supports serialization for data persistence, implements comparison
 * logic for alphabetical sorting, and provides cloning capabilities.
 *
 * @author Mehdi Rahimi
 */

@Getter
@Setter
@AllArgsConstructor
public class User implements Serializable, Comparable<User>, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private float weight;
    private float height;

    /**
     * Compares this user with another user for sorting purposes.
     * The primary sorting criterion is the last name, followed by the first name.
     *
     * @param other The other user to compare against.
     * @return A negative integer, zero, or a positive integer as this user
     * is less than, equal to, or greater than the specified user.
     */
    @Override
    public int compareTo(User other) {
        int res = this.lastName.compareTo(other.lastName);
        if (res == 0) {
            return this.firstName.compareTo(other.firstName);
        }
        return res;
    }

    /**
     * Creates and returns a copy of this user.
     *
     * @return A cloned instance of the User object.
     * @throws RuntimeException if the cloning process fails.
     */
    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning of User failed", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Float.compare(weight, user.weight) == 0 &&
                Float.compare(height, user.height) == 0 &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, weight, height);
    }

    @Override
    public String toString() {
        return "User{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate + ", weight=" + weight + ", height=" + height + '}';
    }
}