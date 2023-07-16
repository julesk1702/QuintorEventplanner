package nl.han.oose.clipper.clipperapi.domain.ideas.application;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.business.IIdeasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class IdeasResourceTest {
    private IdeasResource ideasResource;
    private IIdeasService ideasService;

    @BeforeEach
    void setUp() {
        ideasService = mock(IIdeasService.class);
        ideasResource = new IdeasResource(ideasService);
    }

    @Test
    void testGetAllIdeas() {
        // Arrange
        List<Idea> ideas = new ArrayList<>();
        ideas.add(new Idea());
        ideas.add(new Idea());
        when(ideasService.getAllIdeas()).thenReturn(ideas);

        // Act
        ResponseEntity<List<Idea>> response = ideasResource.getAllIdeas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideas, response.getBody());
        verify(ideasService, times(1)).getAllIdeas();
    }

    @Test
    void testCreateIdea() {
        // Arrange
        Idea idea = new Idea();

        // Act
        ResponseEntity<Void> response = ideasResource.createIdea(idea);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ideasService, times(1)).createIdea(idea);
    }

    @Test
    void testDeleteIdea() {
        // Arrange
        Long ideaId = 1L;

        // Act
        ResponseEntity<Void> response = ideasResource.deleteIdea(ideaId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ideasService, times(1)).deleteIdea(ideaId);
    }

    @Test
    void testLikeIdea() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        boolean liked = true;

        // Act
        ResponseEntity<Void> response = ideasResource.likeIdea(ideaId, userId, liked);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ideasService, times(1)).likesIdea(ideaId, userId, liked);
    }

    @Test
    void testCheckLike() {
        // Arrange
        Long ideaId = 1L;
        Long userId = 1L;
        when(ideasService.checkLike(ideaId, userId)).thenReturn(new ArrayList<>());
        
        // Act
        ResponseEntity<String> response = ideasResource.checkLike(ideaId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ideasService, times(1)).checkLike(ideaId, userId);
    }
}
