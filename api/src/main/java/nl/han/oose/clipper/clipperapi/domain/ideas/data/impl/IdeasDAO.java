package nl.han.oose.clipper.clipperapi.domain.ideas.data.impl;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.UserLikesIdea;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.IIdeasDAO;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.IdeasRepository;
import nl.han.oose.clipper.clipperapi.domain.ideas.data.UserLikesIdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class IdeasDAO implements IIdeasDAO {
    private final IdeasRepository ideasRepository;
    private final UserLikesIdeaRepository userLikesIdeaRepository;
    @Autowired
    public IdeasDAO(IdeasRepository ideasRepository, UserLikesIdeaRepository userLikesIdeaRepository) {
        this.ideasRepository = ideasRepository;
        this.userLikesIdeaRepository = userLikesIdeaRepository;
    }

    @Override
    public List<Idea> getAllIdeas() {
        return ideasRepository.findAll();
    }

    @Override
    public void createIdea(Idea ideas) {
        ideasRepository.save(ideas);
    }

    @Override
    @Transactional
    public boolean deleteIdeaById(Long id) {
        boolean exists = ideasRepository.existsById(id);
        if (exists) ideasRepository.deleteById(id);
        return exists;
    }

    @Override
    @Transactional
    public boolean likesIdea(Long id, Long userId, boolean liked) {
        boolean exists = ideasRepository.existsById(id);
        if (exists) {
            if(!ideasRepository.checkIfUserHasLiked(id, userId).isEmpty()) {
                if(liked) {
                    userLikesIdeaRepository.saveLike(id, userId);
                }
                if(!liked) {
                    userLikesIdeaRepository.saveDislike(id, userId);
                }
            }
            UserLikesIdea body = new UserLikesIdea();
            body.setIdeas_id(id);
            body.setUser_id(userId);
            body.setLiked(true);
            if(ideasRepository.checkIfUserHasLiked(id, userId).isEmpty()) {
                userLikesIdeaRepository.save(body);
            }
            if(liked) {
                ideasRepository.likeIdea(id, userId);
            }
            if(!liked) {
                ideasRepository.dislikeIdea(id, userId);
            }
        }
        return exists;
    }

    @Override
    public List<String> checkLike(Long id, Long userId) {
        return ideasRepository.checkIfUserHasLiked(id, userId);
    }
}
