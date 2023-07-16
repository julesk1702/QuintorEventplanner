package nl.han.oose.clipper.clipperapi.domain.diet.business.impl;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.diet.data.IDietDAO;
import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.user.data.IUserDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DietService implements IDietService {

    private final IDietDAO dietDAO;
    private final IUserDAO userDAO;

    @Autowired
    public DietService(IDietDAO dietDAO, IUserDAO userDAO) {
        this.dietDAO = dietDAO;
        this.userDAO = userDAO;
    }

    @Override
    public ResponseEntity<List<Diet>> getAllDiets() {
        List<Diet> diets = dietDAO.getAllDiets();
        if (diets.isEmpty()) throw new NotFoundException("No diets found");
        return ResponseEntity.ok(diets);
    }

    @Override
    public List<DietCount> getDietCountByEventId(Long id) {
        List<Object[]> result = dietDAO.getDietCountByEventId(id);
        if (result == null || result.isEmpty()) throw new NotFoundException("No diets found for event with id: " + id);
        List<DietCount> dietCounts = new ArrayList<>();
        for (Object[] row : result) {
            String name = (String) row[0];
            Long totalCount = ((Number) row[1]).longValue();
            DietCount dietCountDto = new DietCount(name, totalCount);
            dietCounts.add(dietCountDto);
        }
        return dietCounts;
    }

    @Override
    public ResponseEntity<List<Diet>> getUserDiets(Long id) {
        List<Diet> diets = dietDAO.getUserDiets(id);
        if (diets.isEmpty()) throw new NotFoundException("No diets found");
        return ResponseEntity.ok(diets);
    }

    @Override
    public ResponseEntity<String> getUserCustomDiets(Long userId) {
        UserCustomDiets customDiets = dietDAO.getUserCustomDiets(userId);
        if (customDiets == null) throw new NotFoundException("No custom diets found");
        String customDietsString = customDiets.getCustomDiets();
        return ResponseEntity.ok(customDietsString);
    }

    @Override
    public ResponseEntity<String> setUserDiets(Long userId, SetUserDietsRequest setUserDietsRequest) {
        if (userDAO.getUserById(userId) == null) throw new NotFoundException("User not found");
        setUserDefaultDiets(userId, setUserDietsRequest.getDietIds());
        setUserCustomDiets(userId, setUserDietsRequest.getCustomDiets());

        return ResponseEntity.ok("Diets set");
    }

    private void setUserDefaultDiets(Long userId, Long[] dietIds) {
        List<UserDiet> userDiets = new ArrayList<>();

        for (Long dietId : dietIds) {
            User user = new User();
            user.setUserId(userId);
            Diet diet = new Diet();
            diet.setDietId(dietId);
            UserDiet userDiet = new UserDiet(user, diet);
            userDiet.setId(new UserDietId(userId, dietId));
            userDiets.add(userDiet);
        }
        dietDAO.setUserDiets(userDiets, userId);
    }

    private void setUserCustomDiets(Long userId, String customDiets) {
        dietDAO.setUserCustomDiets(new UserCustomDiets(userId, customDiets));
    }
}
