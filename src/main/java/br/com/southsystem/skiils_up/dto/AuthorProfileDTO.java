package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.models.AuthorProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AuthorProfileDTO {

    private Long id;
    private Long idUser;
    private boolean ativa;
    private String presentation;
    private List<Long> authorCoursesList;

    public AuthorProfileDTO(AuthorProfile authorProfile) {
        this.id = authorProfile.getId();
        this.idUser = authorProfile.getIdUser();
        this.ativa = authorProfile.isEnable();
        this.presentation = authorProfile.getPresentation();
    }
}