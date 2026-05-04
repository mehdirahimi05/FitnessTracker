package de.fherfurt.FitnessTrackerSystem.models;

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
public class User implements Serializable, Comparable<User>, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private float weight;
    private float height;

    /**
     * Constructs a new User with the specified personal and physical attributes.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @param birthDate The user's date of birth.
     * @param weight    The user's weight in kilograms.
     * @param height    The user's height in meters.
     */
    public User(String firstName, String lastName, LocalDate birthDate, float weight, float height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.weight = weight;
        this.height = height;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

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