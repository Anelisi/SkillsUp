package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.models.Rating;
import br.com.southsystem.skiils_up.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<Rating> findAll() {
        return this.ratingService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Rating> findById(@PathVariable Integer id){
        Rating rating = ratingService.findById(id);
        return ResponseEntity.ok().body(rating);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Rating rating){
        rating = ratingService.create(rating);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rating.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Rating rating, @PathVariable Integer id) {
        rating.setId(id);
        ratingService.update(rating);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
