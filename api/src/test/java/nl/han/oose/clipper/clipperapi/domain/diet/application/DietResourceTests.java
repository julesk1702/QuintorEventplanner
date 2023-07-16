package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class DietResourceTests {

    @InjectMocks
    private DietResource dietResource;

    @Mock
    private IDietService IDietService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllDiets() {
        // Arrange
        doReturn(new ResponseEntity<>("", HttpStatus.OK)).when(IDietService).getAllDiets();

        // Act
        ResponseEntity response = dietResource.getAllDiets();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
    }

}
