package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.dto.CourseDTO;
import br.com.southsystem.skiils_up.models.Course;
import br.com.southsystem.skiils_up.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> findAll() {
        List<Course> list = service.findAll();
        List<CourseDTO> dtoList = list.stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        Course obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Course course) {
        course = service.create(course);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Course course, @PathVariable Long id) {
        course.setId(id);
        service.update(course);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
