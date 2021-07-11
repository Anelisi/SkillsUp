package br.com.southsystem.skiils_up.repositories;

import br.com.southsystem.skiils_up.models.AuthorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, Long> {
    AuthorProfile findByIdUser(Long idUser);
    List<AuthorProfile> findByAtiva(Boolean ativa);
}
