package de.fherfurt.FitnessTrackerSystem.services.utils;

import java.util.function.Predicate;

/**
 * Interface for Filter Objects
 *
 * @param <T> Filter class
 * @author Mehdi Rahimi
 */
public interface IFilterObject<T> {
    /**
     * Method to build Predicate using the Filter Object
     * @return Predicate of T
     */
    Predicate<T> buildPredicate();
}

