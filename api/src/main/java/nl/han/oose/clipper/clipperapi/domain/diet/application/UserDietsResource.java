package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.SetUserDietsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class UserDietsResource {

    private final IDietService dietService;

    @Autowired
    public UserDietsResource(IDietService dietService) {
        this.dietService = dietService;
    }

    @CrossOrigin
    @GetMapping("/diets")
    public ResponseEntity<List<Diet>> getUserDiets(@PathVariable Long userId) {
        return dietService.getUserDiets(userId);
    }

    @CrossOrigin
    @GetMapping("/custom-diets")
    public ResponseEntity<String> getUserCustomDiets(@PathVariable Long userId) {
        return dietService.getUserCustomDiets(userId);
    }

    // Please note that this endpoint will replace the user's diets with the given diets, not add them.
    @CrossOrigin
    @PostMapping("/diets")
    public ResponseEntity<String> setUserDiets(@PathVariable Long userId, @RequestBody SetUserDietsRequest setUserDietsRequest) {
        return dietService.setUserDiets(userId, setUserDietsRequest);
    }

}
