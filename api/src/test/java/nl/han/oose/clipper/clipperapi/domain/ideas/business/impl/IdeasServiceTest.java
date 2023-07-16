package nl.han.oose.clipper.clipperapi.domain.ideas.business.impl;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.impl.IdeasDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class IdeasServiceTest {
    private IdeasService ideasService;
    private IdeasDAO ideasDAO;

    @BeforeEach
    void setUp() {
        ideasDAO = mock(IdeasDAO.class);
        ideasService = new IdeasService(ideasDAO);
    }

    @Test
    void testGetAllIdeas() {
        // Arrange
        List<Idea> ideas = new ArrayList<>();
        ideas.add(new Idea());
        ideas.add(new Idea());
        when(ideasDAO.getAllIdeas()).thenReturn(ideas);

        // Act
        List<Idea> result = ideasService.getAllIdeas();

        // Assert
        assertEquals(ideas, result);
        verify(ideasDAO, times(1)).getAllIdeas();
    }

    @Test
    void testCreateIdea() {
        // Arrange
        Idea idea = new Idea();

        // Act
        ideasService.createIdea(idea);

        // Assert
        verify(ideasDAO, times(1)).createIdea(idea);
    }

    @Test
    void testDeleteIdea() {
        // Arrange
        Long ideaId = 1L;
        when(ideasDAO.deleteIdeaById(ideaId)).thenReturn(true);

        // Act
        ideasService.deleteIdea(ideaId);

        // Assert
        verify(ideasDAO, times(1)).deleteIdeaById(ideaId);
    }

    @Test
    void testDeleteIdeaThrowsNotFoundException() {
        // Arrange
        Long ideaId = 1L;
        when(ideasDAO.deleteIdeaById(ideaId)).thenReturn(false);

        // Assert
        assertThrows(NotFoundException.class, () -> ideasService.deleteIdea(ideaId));
        verify(ideasDAO, times(1)).deleteIdeaById(ideaId);
    }

    @Test
    void testLikesIdea() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;
        when(ideasDAO.likesIdea(ideaId, userId, liked)).thenReturn(true);

        // Act
        ideasService.likesIdea(ideaId, userId, liked);

        // Assert
        verify(ideasDAO, times(1)).likesIdea(ideaId, userId, liked);
    }

    @Test
    void testLikesIdeaThrowsNotFoundException() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;
        when(ideasDAO.likesIdea(ideaId, userId, liked)).thenReturn(false);

        // Assert
        assertThrows(NotFoundException.class, () -> ideasService.likesIdea(ideaId, userId, liked));
        verify(ideasDAO, times(1)).likesIdea(ideaId, userId, liked);
    }

    @Test
    void testCheckLike() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        List<String> likeStatus = new ArrayList<>();
        likeStatus.add("liked");
        when(ideasDAO.checkLike(ideaId, userId)).thenReturn(likeStatus);

        // Act
        List<String> result = ideasService.checkLike(ideaId, userId);

        // Assert
        assertEquals(likeStatus, result);
        verify(ideasDAO, times(1)).checkLike(ideaId, userId);
    }
}