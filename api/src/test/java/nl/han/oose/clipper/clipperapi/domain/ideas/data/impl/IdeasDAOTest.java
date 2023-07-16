package nl.han.oose.clipper.clipperapi.domain.ideas.data.impl;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.UserLikesIdea;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.IdeasRepository;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.UserLikesIdeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class IdeasDAOTest {
    private IdeasDAO ideasDAO;
    private IdeasRepository ideasRepository;
    private UserLikesIdeaRepository userLikesIdeaRepository;

    @BeforeEach
    void setUp() {
        ideasRepository = mock(IdeasRepository.class);
        userLikesIdeaRepository = mock(UserLikesIdeaRepository.class);
        ideasDAO = new IdeasDAO(ideasRepository, userLikesIdeaRepository);
    }

    @Test
    void testGetAllIdeas() {
        // Arrange
        List<Idea> ideas = new ArrayList<>();
        ideas.add(new Idea());
        ideas.add(new Idea());
        when(ideasRepository.findAll()).thenReturn(ideas);

        // Act
        List<Idea> result = ideasDAO.getAllIdeas();

        // Assert
        assertEquals(ideas, result);
        verify(ideasRepository, times(1)).findAll();
    }

    @Test
    void testCreateIdea() {
        // Arrange
        Idea idea = new Idea();

        // Act
        ideasDAO.createIdea(idea);

        // Assert
        verify(ideasRepository, times(1)).save(idea);
    }

    @Test
    void testDeleteIdeaById() {
        // Arrange
        Long ideaId = 1L;
        when(ideasRepository.existsById(ideaId)).thenReturn(true);

        // Act
        boolean result = ideasDAO.deleteIdeaById(ideaId);

        // Assert
        assertTrue(result);
        verify(ideasRepository, times(1)).existsById(ideaId);
        verify(ideasRepository, times(1)).deleteById(ideaId);
    }

    @Test
    void testDeleteIdeaByIdReturnsFalseWhenIdeaNotFound() {
        // Arrange
        Long ideaId = 1L;
        when(ideasRepository.existsById(ideaId)).thenReturn(false);

        // Act
        boolean result = ideasDAO.deleteIdeaById(ideaId);

        // Assert
        assertFalse(result);
        verify(ideasRepository, times(1)).existsById(ideaId);
        verify(ideasRepository, never()).deleteById(ideaId);
    }

    @Test
    void testLikesIdeaWhenIdeaExistsAndUserHasLiked() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;
        when(ideasRepository.existsById(ideaId)).thenReturn(true);
        when(ideasRepository.checkIfUserHasLiked(ideaId, userId)).thenReturn(Arrays.asList("liked"));

        // Act
        boolean result = ideasDAO.likesIdea(ideaId, userId, liked);

        // Assert
        assertTrue(result);
        verify(userLikesIdeaRepository, never()).save(any(UserLikesIdea.class));
        verify(ideasRepository, times(1)).likeIdea(ideaId, userId);
        verify(ideasRepository, never()).dislikeIdea(ideaId, userId);
    }

    @Test
    void testLikesIdeaWhenIdeaExistsAndUserHasNotLiked() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;
        when(ideasRepository.existsById(ideaId)).thenReturn(true);
        when(ideasRepository.checkIfUserHasLiked(ideaId, userId)).thenReturn(new ArrayList<>());

        // Act
        boolean result = ideasDAO.likesIdea(ideaId, userId, liked);

        // Assert
        assertTrue(result);
        verify(userLikesIdeaRepository, times(1)).save(any(UserLikesIdea.class));
        verify(ideasRepository, times(1)).likeIdea(ideaId, userId);
        verify(ideasRepository, never()).dislikeIdea(ideaId, userId);
    }

    @Test
    void testLikesIdeaWhenIdeaDoesNotExist() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;
        when(ideasRepository.existsById(ideaId)).thenReturn(false);

        // Act
        boolean result = ideasDAO.likesIdea(ideaId, userId, liked);

        // Assert
        assertFalse(result);
        verify(userLikesIdeaRepository, never()).save(any(UserLikesIdea.class));
        verify(ideasRepository, never()).likeIdea(ideaId, userId);
        verify(ideasRepository, never()).dislikeIdea(ideaId, userId);
    }

    @Test
    void testLikesIdeaWhenIdeaExistsAndUserHasLikedAndLikedIsFalse() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = false;
        when(ideasRepository.existsById(ideaId)).thenReturn(true);
        when(ideasRepository.checkIfUserHasLiked(ideaId, userId)).thenReturn(Arrays.asList("liked"));

        // Act
        boolean result = ideasDAO.likesIdea(ideaId, userId, liked);

        // Assert
        assertTrue(result);
        verify(userLikesIdeaRepository, never()).save(any(UserLikesIdea.class));
        verify(ideasRepository, never()).likeIdea(ideaId, userId);
        verify(ideasRepository, times(1)).dislikeIdea(ideaId, userId);
    }

    @Test
    void testCheckLike() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        List<String> likeStatus = Arrays.asList("liked");
        when(ideasRepository.checkIfUserHasLiked(ideaId, userId)).thenReturn(likeStatus);

        // Act
        List<String> result = ideasDAO.checkLike(ideaId, userId);

        // Assert
        assertEquals(likeStatus, result);
        verify(ideasRepository, times(1)).checkIfUserHasLiked(ideaId, userId);
    }
}
    
    