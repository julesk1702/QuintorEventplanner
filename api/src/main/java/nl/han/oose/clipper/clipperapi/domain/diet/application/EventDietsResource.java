package nl.han.oose.clipper.clipperapi.domain.diet.application;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.DietCount;
import nl.han.oose.clipper.clipperapi.domain.diet.business.IDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/diets")
public class EventDietsResource {

    private final IDietService dietService;

    @Autowired
    public EventDietsResource(IDietService dietService) {
        this.dietService = dietService;
    }

    @CrossOrigin
    @RequestMapping("")
    public ResponseEntity<List<DietCount>> getDietsByEventId(@PathVariable Long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(dietService.getDietCountByEventId(eventId));
    }

}
