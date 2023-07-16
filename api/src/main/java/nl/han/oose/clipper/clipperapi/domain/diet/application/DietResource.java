package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diets")
public class DietResource {

    private final IDietService dietService;

    @Autowired
    public DietResource(IDietService dietService) {
        this.dietService = dietService;
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<Diet>> getAllDiets() {
        return dietService.getAllDiets();
    }

}
