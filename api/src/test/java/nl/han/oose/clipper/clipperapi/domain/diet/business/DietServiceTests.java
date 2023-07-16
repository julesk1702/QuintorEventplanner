package nl.han.oose.clipper.clipperapi.domain.diet.business;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.DietCount;
import nl.han.oose.clipper.clipperapi.domain.diet.business.impl.DietService;
import nl.han.oose.clipper.clipperapi.domain.diet.data.IDietDAO;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.SetUserDietsRequest;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserCustomDiets;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;
import nl.han.oose.clipper.clipperapi.domain.user.data.IUserDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DietServiceTests {

    @InjectMocks
    private DietService dietServiceImpl;

    @Mock
    private IDietDAO IDietDAO;

    @Mock
    private IUserDAO IUserDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllDietsWithValidDiets() {
        // Arrange
        List<Diet> diets = List.of(new Diet(), new Diet());
        doReturn(diets).when(IDietDAO).getAllDiets();

        // Act
        ResponseEntity<List<Diet>> response = dietServiceImpl.getAllDiets();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllDietsWithNoDiets() {
        // Arrange
        doReturn(List.of()).when(IDietDAO).getAllDiets();

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            dietServiceImpl.getAllDiets();
        });
    }

    @Test
    void testGetUserDiets() {
        Long userId = 123L;

        List<Diet> expectedDiets = Collections.singletonList(new Diet());

        when(IDietDAO.getUserDiets(userId)).thenReturn(expectedDiets);

        ResponseEntity<List<Diet>> response = dietServiceImpl.getUserDiets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDiets, response.getBody());
    }

    @Test
    void testGetUserDietsNotFound() {
        Long userId = 123L;

        when(IDietDAO.getUserDiets(userId)).thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> dietServiceImpl.getUserDiets(userId)
        );

        assertEquals("No diets found", exception.getMessage());
    }

    @Test
    void testGetUserCustomDietsFound() {
        Long userId = 123L;
        String expectedCustomDiets = "Custom diets";

        UserCustomDiets userCustomDiets = new UserCustomDiets(userId, expectedCustomDiets);

        when(IDietDAO.getUserCustomDiets(userId)).thenReturn(userCustomDiets);

        ResponseEntity<String> response = dietServiceImpl.getUserCustomDiets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomDiets, response.getBody());
    }

    @Test
    void testSetUserDietsNotFound() {
        Long userId = 123L;
        SetUserDietsRequest setUserDietsRequest = new SetUserDietsRequest();

        when(IUserDAO.getUserById(userId)).thenReturn(null);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> dietServiceImpl.setUserDiets(userId, setUserDietsRequest)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testSetUserDiets() {
        Long userId = 1L;
        Long[] dietIds = {1L, 2L};
        String customDiets = "Custom diets";

        when(IUserDAO.getUserById(userId)).thenReturn(new User());

        // Call the controller method
        SetUserDietsRequest setUserDietsRequest = new SetUserDietsRequest(dietIds, customDiets);
        ResponseEntity<String> responseEntity = dietServiceImpl.setUserDiets(userId, setUserDietsRequest);

        verify(IUserDAO, times(1)).getUserById(userId);
        verify(IDietDAO, times(1)).setUserDiets(anyList(), eq(userId));
        verify(IDietDAO, times(1)).setUserCustomDiets(any(UserCustomDiets.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Diets set", responseEntity.getBody());
    }


    @Test
    void testGetDietCountByEventIdWithEventWithDiets() {
        // Arrange
        List<Object[]> expectedResponse = new ArrayList<>();
        Object[] data1 = {"Diet A", 10L};
        Object[] data2 = {"Diet B", 5L};
        expectedResponse.add(data1);
        expectedResponse.add(data2);
        doReturn(expectedResponse).when(IDietDAO).getDietCountByEventId(1L);
        // Act
        List<DietCount> response = dietServiceImpl.getDietCountByEventId(1L);
        // Assert
        assertEquals(expectedResponse.size(), response.size());
        for (int i = 0; i < expectedResponse.size(); i++) {
            Object[] expectedData = expectedResponse.get(i);
            DietCount actualData = response.get(i);
            assertEquals(expectedData[0], actualData.getName());
            assertEquals(expectedData[1], actualData.getTotalCount());
        }
    }

    @Test
    void testGetDietCountByEventIdWithEventWithoutDiets() {
        // Arrange
        List<Object[]> expectedResponse = new ArrayList<>();
        doReturn(expectedResponse).when(IDietDAO).getDietCountByEventId(1L);
        // Act & Assert
        assertThrows(NotFoundException.class, () -> dietServiceImpl.getDietCountByEventId(2L));
    }

}
