package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.dto.AuthorProfileDTO;
import br.com.southsystem.skiils_up.models.AuthorProfile;
import br.com.southsystem.skiils_up.services.AuthorProfileService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/author-profile")
public class AuthorProfileController {

    private final AuthorProfileService service;

    public AuthorProfileController(AuthorProfileService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public Page<AuthorProfileDTO> findAll(
            @RequestParam(value = "page",  defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage){
        Page<AuthorProfile> list = service.findPage(page, linesPerPage);
        return list.map(AuthorProfileDTO::new);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorProfile> findById(@PathVariable Long id) {
        AuthorProfile obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/user/{idUser}")
    public ResponseEntity<AuthorProfile> findByIdUser(@PathVariable Long idUser) {
        AuthorProfile obj = service.findByIdUser(idUser);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/enable/{ativa}")
    public ResponseEntity<List<AuthorProfile>> findByAtiva(@PathVariable Boolean ativa) {
        List<AuthorProfile> obj = service.findByAtiva(ativa);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AuthorProfile author) {
        author = service.create(author);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody AuthorProfile author,
                                       @PathVariable Long id) {
        author.setId(id);
        service.update(author);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(path = "author-course/{id}/{idCourse}")
    public ResponseEntity<Void> authorCourse(@PathVariable Long idCourse,
                                             @PathVariable Long id) {
        service.authorCourse(id,idCourse);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<Void> partUpdate(@RequestBody AuthorProfile author,
                                           @PathVariable Long id) {
        author.setId(id);
        service.update(author);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<Void> logicEnable(@RequestBody AuthorProfile author, @PathVariable Long id) {
        service.enableStatus(id, author.isEnable());
        return ResponseEntity.noContent().build();
    }
}
