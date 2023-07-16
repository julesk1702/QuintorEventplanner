package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.DietCount;
import nl.han.oose.clipper.clipperapi.domain.diet.business.impl.DietService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class EventDietsResourceTests {

    @InjectMocks
    private EventDietsResource eventDietsResource;

    @Mock
    private DietService dietService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetDietsByEventId() {
        // Arrange
        List<DietCount> expectedResponse = List.of(new DietCount());
        doReturn(expectedResponse).when(dietService).getDietCountByEventId(0L);
        // Act
        ResponseEntity<List<DietCount>> response = eventDietsResource.getDietsByEventId(0L);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

}
