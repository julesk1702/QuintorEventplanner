package nl.han.oose.clipper.clipperapi.domain.diet.data;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.DietCount;
import nl.han.oose.clipper.clipperapi.domain.diet.data.impl.DietDAO;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserCustomDiets;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserDiet;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DietDAOTests {
    
    @Mock
    private DietRepository dietRepository;

    @Mock
    private UserDietRepository userDietRepository;

    @Mock
    private UserCustomDietsRepository userCustomDietsRepository;

    @InjectMocks
    private DietDAO dietDAO;
    
    @Test
    void testGetAllDiets() {
        // Act
        dietDAO.getAllDiets();

        // Assert
        verify(dietRepository, times(1)).findAll();
    }

    @Test
    void testGetDietsByIds() {
        List<Long> dietIds = Arrays.asList(1L, 2L);
        Diet diet1 = new Diet();
        diet1.setDietId(1L);
        Diet diet2 = new Diet();
        diet2.setDietId(2L);
        List<Diet> expectedDiets = Arrays.asList(diet1, diet2);

        when(dietRepository.findAllById(dietIds)).thenReturn(expectedDiets);

        List<Diet> diets = dietDAO.getDietsByIds(dietIds);

        assertEquals(expectedDiets, diets);
    }

    @Test
    void testGetUserDiets() {
        // Arrange
        doReturn(Optional.empty()).when(userDietRepository).findDietsByUserId(1L);

        // Act
        dietDAO.getUserDiets(1L);

        // Assert
        verify(userDietRepository, times(1)).findDietsByUserId(1L);
    }

    @Test
    void testGetUserCustomDiets() {
        // Arrange
        doReturn(Optional.empty()).when(userCustomDietsRepository).findById(1L);

        // Act
        dietDAO.getUserCustomDiets(1L);

        // Assert
        verify(userCustomDietsRepository, times(1)).findById(1L);
    }

    @Test
    void testSetUserDietsWithUserDiets() {
        // Arrange
        List<UserDiet> userDiets = List.of(new UserDiet());

        // Act
        dietDAO.setUserDiets(userDiets, 1L);

        // Assert
        verify(userDietRepository, times(1)).deleteByUserId(1L);
        verify(userDietRepository, times(1)).saveAll(userDiets);
    }

    @Test
    void testSetUserDietsWithoutUserDiets() {
        // Arrange
        List<UserDiet> userDiets = new ArrayList<>();

        // Act
        dietDAO.setUserDiets(userDiets, 1L);

        // Assert
        verify(userDietRepository, times(1)).deleteByUserId(1L);
        verify(userDietRepository, times(0)).saveAll(userDiets);
    }

    @Test
    void testSetUserCustomDietsWithUserCustomDiets() {
        // Arrange
        UserCustomDiets userCustomDiets = new UserCustomDiets();
        userCustomDiets.setCustomDiets("test");
        userCustomDiets.setUser_id(1L);

        // Act
        dietDAO.setUserCustomDiets(userCustomDiets);

        // Assert
        verify(userCustomDietsRepository, times(1)).deleteById(userCustomDiets.getUser_id());
        verify(userCustomDietsRepository, times(1)).save(userCustomDiets);
    }

    @Test
    void testSetUserCustomDietsWithoutUserCustomDiets() {
        // Arrange
        UserCustomDiets userCustomDiets = new UserCustomDiets();
        userCustomDiets.setUser_id(1L);

        // Act
        dietDAO.setUserCustomDiets(userCustomDiets);

        // Assert
        verify(userCustomDietsRepository, times(1)).deleteById(userCustomDiets.getUser_id());
        verify(userCustomDietsRepository, times(0)).save(userCustomDiets);
    }

    @Test
    void testGetDietCountByEventId() {
        // Arrange
        List<Object[]> dietCounts = new ArrayList<>();
        doReturn(dietCounts).when(dietRepository).findDietCountsByEventId(1L);

        // Act
        dietDAO.getDietCountByEventId(1L);

        // Assert
        verify(dietRepository, times(1)).findDietCountsByEventId(1L);
    }
}
