package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.enums.ErrorMessages;
import br.com.southsystem.skiils_up.models.AuthorProfile;
import br.com.southsystem.skiils_up.repositories.AuthorProfileRepository;
import br.com.southsystem.skiils_up.services.exceptions.DataIntegrityException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorProfileService {

    private final AuthorProfileRepository repo;
    private final CourseService courseService;


    public AuthorProfile create(AuthorProfile author) {
        author.setId(null);
        return this.repo.save(author);
    }

    public Page<AuthorProfile> findPage(Integer page, Integer linesPerPage) {
        final var pageRequest = PageRequest.of(page,linesPerPage);
        return repo.findAll(pageRequest);
    }

    public AuthorProfile findById(Long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.AUTHOR_PROFILE_NOT_FOUND.getDesc()));
    }

    public AuthorProfile findByIdUser(Long idUser) {
        try {
            return repo.findByIdUser(idUser);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.NONEXISTENT_ID_USER.getDesc() + e.getMessage());
        }
    }

    public List<AuthorProfile> findByAtiva(Boolean ativa) {
        try {
            return repo.findByAtiva(ativa);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.NON_EXIST_ENABLE_STATUS.getDesc() + e.getMessage());
        }
    }

    public void authorCourse(Long id, Long idCourse) {
        AuthorProfile author = findById(id);
        try {
            author.getAuthorCoursesList().add(idCourse);
            repo.save(author);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.AUTHOR_PROFILE_NOT_FOUND.getDesc());
        }
    }

    public AuthorProfile update(AuthorProfile author) {
        this.findById(author.getId());
        return this.repo.save(author);
    }

    public void delete(Long id) {
        findById(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ErrorMessages.CANT_DELETE_AUTHOR_PROFILE.getDesc());
        }
    }

    public void enableStatus(Long id, Boolean ativa) {

        try {
            AuthorProfile author = findById(id);
            author.setEnable(ativa);
            repo.save(author);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.AUTHOR_PROFILE_NOT_FOUND.getDesc());
        }
    }
}

