package nl.han.oose.clipper.clipperapi.domain.diet.data;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserCustomDiets;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserDiet;

import java.util.List;

public interface IDietDAO {

    /**
     * Gets all diets from the database
     * @return A list of {@link Diet}
     */
    List<Diet> getAllDiets();

    /**
     * Gets all diets from the database with the given ids
     * @param dietIds The ids of the diets to get
     * @return A list of {@link Diet}
     */
    List<Diet> getDietsByIds(List<Long> dietIds);

    /**
     * Gets the amount of people that have a certain diet for a certain event
     * @param id The id of the event
     * @return A list of {@link Object[]} containing the diet and the amount of people that have this diet
     */
    List<Object[]> getDietCountByEventId(Long id);

    /**
     * Gets the user's diets from the database with the given id
     *
     * @param id The id of the user
     * @return A list of {@link Diet}
     */
    List<Diet> getUserDiets(Long id);

    /**
     * Gets the user's custom diets from the database with the given id
     * @param userId The id of the user
     * @return The user's custom diets in a {@link UserCustomDiets}
     */
    UserCustomDiets getUserCustomDiets(Long userId);

    /**
     * Replaces the user's diets with the given diets in the database
     * @param userDiets The user's diets
     */
    void setUserDiets(List<UserDiet> userDiets, Long userId);

    /**
     * Sets the user's custom diets in the database
     * @param customDiets The user's custom diets and id
     */
    void setUserCustomDiets(UserCustomDiets customDiets);

}
