package nl.han.oose.clipper.clipperapi.domain.ideas.business.impl;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.business.IIdeasService;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.impl.IdeasDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeasService implements IIdeasService {
    private final IdeasDAO ideasDAO;

    @Autowired
    public IdeasService(IdeasDAO ideasDAO) {
        this.ideasDAO = ideasDAO;
    }

    @Override
    public List<Idea> getAllIdeas() {
        return ideasDAO.getAllIdeas();
    }

    @Override
    public void createIdea(Idea ideas) {
        ideasDAO.createIdea(ideas);
    }

    @Override
    public void deleteIdea(Long id) {
        boolean exists = ideasDAO.deleteIdeaById(id);
        if (!exists) throw new NotFoundException("Idea suggestion with id: " + id + " does not exist.");
    }

    @Override
    public void likesIdea(Long id, Long userId, boolean liked) {
        boolean exists = ideasDAO.likesIdea(id, userId, liked);
        if (!exists) throw new NotFoundException("Idea suggestion with id: " + id + " does not exist.");
    }

    @Override
    public List<String> checkLike(Long id, Long userId) {
        return ideasDAO.checkLike(id, userId);
    }
}
