package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.SetUserDietsRequest;
import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserDietResourceTests {

    @InjectMocks
    private UserDietsResource userDietResource;

    @Mock
    private IDietService dietService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserDiets() {
        Long userId = 123L;
        List<Diet> expectedDiets = Collections.singletonList(new Diet());

        when(dietService.getUserDiets(userId)).thenReturn(ResponseEntity.ok(expectedDiets));

        ResponseEntity<List<Diet>> response = userDietResource.getUserDiets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDiets, response.getBody());
    }

    @Test
    void testGetUserCustomDiets() {
        Long userId = 123L;
        String expectedCustomDiets = "Custom diets";

        when(dietService.getUserCustomDiets(userId)).thenReturn(ResponseEntity.ok(expectedCustomDiets));

        ResponseEntity<String> response = userDietResource.getUserCustomDiets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomDiets, response.getBody());
    }

    @Test
    void testSetUserDiets() {
        Long userId = 123L;
        SetUserDietsRequest setUserDietsRequest = new SetUserDietsRequest();
        String expectedResponse = "Diets set successfully";

        when(dietService.setUserDiets(userId, setUserDietsRequest)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<String> response = userDietResource.setUserDiets(userId, setUserDietsRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

}
