package nl.han.oose.clipper.clipperapi.domain.ideas.application;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import nl.han.oose.clipper.clipperapi.domain.ideas.business.IIdeasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ideas")
public class IdeasResource {
    private final IIdeasService ideasService;

    public IdeasResource(IIdeasService ideasService) {
        this.ideasService = ideasService;
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<Idea>> getAllIdeas(){
        return ResponseEntity.status(HttpStatus.OK).body(ideasService.getAllIdeas());
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<Void> createIdea(@RequestBody Idea ideas){
        ideasService.createIdea(ideas);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id){
        ideasService.deleteIdea(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin
    @PutMapping("/{id}/likes/{userId}")
    public ResponseEntity<Void> likeIdea(@PathVariable Long id, @PathVariable Long userId, @RequestParam boolean liked){
        ideasService.likesIdea(id, userId, liked);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin
    @GetMapping("/{id}/checklike/{userId}")
    public ResponseEntity<String> checkLike(@PathVariable Long id, @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(ideasService.checkLike(id, userId).toString());
    }
}