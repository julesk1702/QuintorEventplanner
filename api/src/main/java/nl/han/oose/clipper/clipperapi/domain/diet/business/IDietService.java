package nl.han.oose.clipper.clipperapi.domain.diet.business;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.DietCount;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.SetUserDietsRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDietService {

    /**
     * Gets all diets
     * @return A list of {@link Diet} in a {@link ResponseEntity}
     */
    ResponseEntity<List<Diet>> getAllDiets();

    /**
     * Gets a list of {@link DietCount}, containing the diet and the amount of people that have this diet
     * @param id The id of the event you want to get the diets with their counts from
     * @return A list of {@link DietCount}
     */
    List<DietCount> getDietCountByEventId(Long id);

    /**
     * Gets the user's diets
     * @param id The user's id
     * @return {@link ResponseEntity<List<Diet>>} with the status code and body (a list of {@link Diet})
     */
    ResponseEntity<List<Diet>> getUserDiets(Long id);

    /**
     * Gets the user's custom diets
     * @param userId The user's id
     * @return {@link ResponseEntity<String>} with the status code and body (custom diets in string format)
     */
    ResponseEntity<String> getUserCustomDiets(Long userId);

    /**
     * Replaces the user's diets with the given diets
     * @param userId The user's id
     * @param dietRequest The user's diets in a {@link SetUserDietsRequest}
     * @return {@link ResponseEntity<String>} with the status code and body (a message)
     */
    ResponseEntity<String> setUserDiets(Long userId, SetUserDietsRequest dietRequest);

}
