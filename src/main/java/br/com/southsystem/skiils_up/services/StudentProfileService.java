package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.dto.StudentProfileDTO;
import br.com.southsystem.skiils_up.enums.ErrorMessages;
import br.com.southsystem.skiils_up.models.StudentProfile;
import br.com.southsystem.skiils_up.repositories.StudentProfileRepository;
import br.com.southsystem.skiils_up.services.exceptions.DataIntegrityException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentProfileService {

    private final StudentProfileRepository repo;

    public Page<StudentProfile> findAll(Integer page, Integer linesPerPage) {
        final var pageRequest = PageRequest.of(page, linesPerPage);
        return  repo.findAll(pageRequest);
    }

    public StudentProfile create(StudentProfile student) {
        student.setId(null);
        return this.repo.save(student);
    }

    public StudentProfile findById(Long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.STUDENT_PROFILE_NOT_FOUND.getDesc()));
    }

    public StudentProfile findByIdUser(Long idUser) {
        try {
            return repo.findByIdUser(idUser);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.NONEXISTENT_ID_USER.getDesc() + e.getMessage());
        }
    }

    public List<StudentProfile> findByEnable(Boolean enable) {
        try {
            return repo.findByEnable(enable);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.NON_EXIST_ENABLE_STATUS.getDesc() + e.getMessage());
        }
    }

    public void addCoursesToSavedItemsList(Long id, Long idCourse) {
        StudentProfile student = findById(id);
        try {
            student.getSavedItems().add(idCourse);
            repo.save(student);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.STUDENT_PROFILE_NOT_FOUND.getDesc());
        }
    }

    public void addOrderToOrderList(Long id, Long idOrder) {
        StudentProfile student = findById(id);
        try {
            student.getOrdersList().add(idOrder);
            repo.save(student);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.STUDENT_PROFILE_NOT_FOUND.getDesc());
        }
    }

    public StudentProfile update(StudentProfile student) {
        this.findById(student.getId());
        return this.repo.save(student);
    }

    public void delete(Long id) {
        findById(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ErrorMessages.CANT_DELETE_STUDENT_PROFILE.getDesc());
        }
    }

    public void enableStatus(Long id, Boolean ativa) {

        try {
            StudentProfile student = findById(id);
            student.setEnable(ativa);
            repo.save(student);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessages.STUDENT_PROFILE_NOT_FOUND.getDesc());
        }
    }

    public StudentProfile fromDTO(StudentProfileDTO objDTO) {
        return new StudentProfile(objDTO.getId(), objDTO.getIdUser(), objDTO.isAtiva());
    }
}

