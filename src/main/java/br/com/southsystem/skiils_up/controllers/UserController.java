package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.dto.UserDTO;
import br.com.southsystem.skiils_up.dto.UserNewDTO;
import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Api( tags = "Users")
public class UserController {

    private UserService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "This method is used to get all the users.")
    @GetMapping
    public Page<UserDTO> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage) {
        Page<User> list = service.findAll(page, linesPerPage);
        Page<UserDTO> dtoList = list.map(UserDTO::new);
        return dtoList;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserNewDTO userNewDTO){
        User user = service.fromNewDTO(userNewDTO);
        user = service.create(user);
        user = service.addAdress(user, userNewDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/{idUser}")
    public void addProfile(@PathVariable Long idUser) {
       service.addProfile(idUser);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        service.update(user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public @ResponseBody void partUpdate(@RequestBody Map<Object, Object> fields, @PathVariable Long id) {
        User user = service.findById(id);

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(User.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        service.update(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
