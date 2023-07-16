package nl.han.oose.clipper.clipperapi.domain.diet.data.impl;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.data.DietRepository;
import nl.han.oose.clipper.clipperapi.domain.diet.data.IDietDAO;
import nl.han.oose.clipper.clipperapi.domain.diet.data.UserCustomDietsRepository;
import nl.han.oose.clipper.clipperapi.domain.diet.data.UserDietRepository;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserCustomDiets;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DietDAO implements IDietDAO {

    private final DietRepository dietRepository;
    private final UserDietRepository userDietRepository;
    private final UserCustomDietsRepository userCustomDietsRepository;

    @Autowired
    public DietDAO(DietRepository dietRepository, UserDietRepository userDietRepository, UserCustomDietsRepository userCustomDietsRepository) {
        this.dietRepository = dietRepository;
        this.userDietRepository = userDietRepository;
        this.userCustomDietsRepository = userCustomDietsRepository;
    }

    @Override
    public List<Diet> getAllDiets() {
        return dietRepository.findAll();
    }

    @Override
    public List<Diet> getDietsByIds(List<Long> dietIds) {
        return dietRepository.findAllById(dietIds);
    }

    @Override
    public List<Object[]> getDietCountByEventId(Long id) {
        return dietRepository.findDietCountsByEventId(id);
    }

    @Override
    public List<Diet> getUserDiets(Long id) {
        return userDietRepository.findDietsByUserId(id).orElse(null);
    }

    @Override
    public UserCustomDiets getUserCustomDiets(Long userId) {
        return userCustomDietsRepository.findById(userId).orElse(null);
    }

    @Override
    public void setUserDiets(List<UserDiet> userDiets, Long userId) {
        userDietRepository.deleteByUserId(userId);
        if (!userDiets.isEmpty()) userDietRepository.saveAll(userDiets);
    }

    @Override
    public void setUserCustomDiets(UserCustomDiets customDiets) {
        userCustomDietsRepository.deleteById(customDiets.getUser_id());
        if (customDiets.getCustomDiets() != null && !customDiets.getCustomDiets().isEmpty()) { userCustomDietsRepository.save(customDiets); }
    }
}
