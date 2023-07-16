package nl.han.oose.clipper.clipperapi.domain.ideas.business;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;

import java.util.List;

public interface IIdeasService {
    List<Idea> getAllIdeas();

    void createIdea(Idea ideas);

    void deleteIdea(Long ideaId);

    void likesIdea(Long id, Long userId, boolean liked);


    List<String> checkLike(Long id, Long userId);
}
