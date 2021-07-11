package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.dto.StudentProfileDTO;
import br.com.southsystem.skiils_up.models.StudentProfile;
import br.com.southsystem.skiils_up.services.StudentProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/student-profile")
public class StudentProfileController {

    private final StudentProfileService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public Page<StudentProfileDTO> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage) {
        Page<StudentProfile> list = service.findAll(page, linesPerPage);
        return list.map(StudentProfileDTO::new);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentProfile> findById(@PathVariable Long id) {
        StudentProfile obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/user/{idUser}")
    public ResponseEntity<StudentProfile> findByIdUser(@PathVariable Long idUser) {
        StudentProfile obj = service.findByIdUser(idUser);
        return ResponseEntity.ok().body(obj);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/enable/{enable}")
    public ResponseEntity<List<StudentProfile>> findByEnable(@PathVariable Boolean enable) {
        List<StudentProfile> list = service.findByEnable(enable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody StudentProfileDTO studentDTO) {
        StudentProfile student = service.fromDTO(studentDTO);
        student = service.create(student);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody StudentProfileDTO studentDTO,
                                       @PathVariable Long id) {
        StudentProfile student = service.fromDTO(studentDTO);
        student.setId(id);
        service.update(student);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/saved-items/{id}/{idCourse}")
    public ResponseEntity<Void> addCoursesToSavedItemsList(@PathVariable Long idCourse,
                                            @PathVariable Long id) {
        service.addCoursesToSavedItemsList(id,idCourse);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/student-order/{id}/{idOrder}")
    public ResponseEntity<Void> addOrderToOrderList(@PathVariable Long idOrder,
                                                    @PathVariable Long id) {
        service.addOrderToOrderList(id,idOrder);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> logicEnable(@RequestBody StudentProfile student, @PathVariable Long id) {
        service.enableStatus(id, student.isEnable());
        return ResponseEntity.noContent().build();
    }
}
