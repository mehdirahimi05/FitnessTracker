package de.fherfurt.FitnessTrackerSystem.services;


import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.repositories.IActivityTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityTypeServiceTest {
    @Mock
    IActivityTypeRepository activityTypeRepository;

    @InjectMocks
    ActivityTypeService activityTypeService;

    private ActivityType activityType1 = new ActivityType(
            1,
            "Kraft Training"
    );

    private ActivityType activityType2 = new ActivityType(
            2,
            "Boxen"
    );

    @Test
    void testGetAllActivityTypeNull() {
        // Arrange
        when(activityTypeRepository.findAll()).thenReturn(List.of());

        // Act
        List<ActivityType> result = activityTypeService.getAllActivityType();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetActivityTypeOne() {
        // Arrange
        when(activityTypeRepository.findAll()).thenReturn(List.of(activityType1));

        // Act
        List<ActivityType> result = activityTypeService.getAllActivityType();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testGetActivityType() {
        // Arrange
        when(activityTypeRepository.findAll()).thenReturn(List.of(activityType1, activityType2));

        // Act
        List<ActivityType> result = activityTypeService.getAllActivityType();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetActivityTypeByIdSuccess() {
        // Arrange
        when(activityTypeRepository.findById(1)).thenReturn(Optional.of(activityType1));

        // Act
        Optional<ActivityType> result = activityTypeService.getActivityTypeById(activityType1.getActivityTypeId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(activityType1, result.get());
    }

    @Test
    void testCheckIsOwnActivityTypeNotFound() {
        // Arrange
        when(activityTypeRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = activityTypeService.checkIsOwnActivityType(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnActivityTypeSuccess() {
        // Arrange
        when(activityTypeRepository.findById(1)).thenReturn(Optional.of(activityType1));

        // Act
        boolean result = activityTypeService.checkIsOwnActivityType(activityType1.getActivityTypeId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddActivityTypeSuccess() {
        // Act
        activityTypeService.addActivityType(activityType1);

        // Assert -> verify if save() was called
        verify(activityTypeRepository).save(activityType1);
    }

    @Test
    void testUpdateActivityTypeSuccess() {
        // Act
        activityTypeService.updateActivityType(activityType1);

        // Assert -> verify if save() was called
        verify(activityTypeRepository).save(activityType1);
    }

    @Test
    void testDeleteActivityTypeById() {
        // Act
        activityTypeService.deleteActivityTypeById(activityType1.getActivityTypeId());

        // Assert -> verify if deleteById() was called
        verify(activityTypeRepository).deleteById(activityType1.getActivityTypeId());
    }
}
