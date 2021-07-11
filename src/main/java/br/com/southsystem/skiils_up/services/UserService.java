package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.dto.UserDTO;
import br.com.southsystem.skiils_up.dto.UserNewDTO;
import br.com.southsystem.skiils_up.enums.ClientProfile;
import br.com.southsystem.skiils_up.models.Address;
import br.com.southsystem.skiils_up.models.AuthorProfile;
import br.com.southsystem.skiils_up.models.StudentProfile;
import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.repositories.AddressRepository;
import br.com.southsystem.skiils_up.repositories.UserRepository;
import br.com.southsystem.skiils_up.security.UserSS;
import br.com.southsystem.skiils_up.services.exceptions.AuthorizationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static br.com.southsystem.skiils_up.enums.UserCategories.AUTHOR;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final AuthorProfileService authorProfileService;
    private final StudentProfileService studentProfileService;
    private final BCryptPasswordEncoder passwordEncoder;

    public static UserSS authenticated() {

        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public Page<User> findAll(Integer page, Integer linesPerPage) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        return  repository.findAll(pageRequest);
    }

    public User findById(Long id) {

        UserSS user = authenticated();
        if (user == null || !user.hasRole(ClientProfile.ADMIN) && !id.equals(user.getId())) {
            throw  new AuthorizationException("Acesso negado!");
        }

        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não existe."));
    }

    public User create(User user) {
        user.setId(null);
        return this.repository.save(user);
    }

    public void addProfile(Long idUser) {
        final var user = findById(idUser);
        if (user.getCategory().equals(AUTHOR)){
            authorProfileService.create(new AuthorProfile(null, user.getId(), true, "Escreva uma apresentação sua!"));
        } else studentProfileService.create(new StudentProfile(null, user.getId(), true));
    }

    public User update(User user) {
        this.findById(user.getId());
        return this.repository.save(user);
    }

    public void delete(Long id) {
        findById(id);

        repository.deleteById(id);
    }

    public User fromDTO(UserDTO userDto) {
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), null, null, userDto.getEmail(), null, null);
    }

    public User fromNewDTO(UserNewDTO userNewDto) {
        User user = new User(null, userNewDto.getFirstName(), userNewDto.getLastName(), userNewDto.getCpf(), String.valueOf(userNewDto.getBirthday()), userNewDto.getEmail(), passwordEncoder.encode(userNewDto.getPassword()) , userNewDto.getCategory());
        user.getPhone().addAll(Arrays.asList(userNewDto.getTelephone1()));
        if(userNewDto.getTelephone2() != null) {
            user.getPhone().add(userNewDto.getTelephone2());
        }
        if(userNewDto.getTelephone3() != null) {
            user.getPhone().add(userNewDto.getTelephone3());
        }
        if (userNewDto.getClient()== ClientProfile.ADMIN) {
            user.addClient(userNewDto.getClient());
        }
        return user;
    }

    public User addAdress(User user, UserNewDTO userNewDto) {
        Address address = new Address(null, user.getId(), userNewDto.getStreet(), userNewDto.getCivicNumber(), userNewDto.getComplement(), userNewDto.getPostalCode(), userNewDto.getCity(), userNewDto.getState());
        addressRepository.save(address);

        return user;
    }
}

