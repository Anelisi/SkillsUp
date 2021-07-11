package br.com.southsystem.skiils_up.repositories;

import br.com.southsystem.skiils_up.models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findByIdUser(Long idUser);
    List<StudentProfile> findByEnable(Boolean enable);
}
