package nl.han.oose.clipper.clipperapi.domain.ideas.data;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;

import java.util.List;

public interface IIdeasDAO {
    List<Idea> getAllIdeas();

    void createIdea(Idea ideas);

    boolean deleteIdeaById(Long ideaId);

    boolean likesIdea(Long id, Long userId, boolean liked);


    List<String> checkLike(Long id, Long userId);
}
