package br.com.southsystem.skiils_up.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    AUTHOR_PROFILE_NOT_FOUND ("Este perfil de professor não existe."),
    CANT_DELETE_AUTHOR_PROFILE("Não é possível excluir um perfil vinculado a um professor"),
    NON_EXIST_ENABLE_STATUS("Status de habilitação inexistente \n"),
    NONEXISTENT_ID_USER("IdUser inexistente \n"),
    STUDENT_PROFILE_NOT_FOUND("Este perfil de estudante não existe."),
    CANT_DELETE_STUDENT_PROFILE ("Não é possível excluir um perfil vinculado a um estudante"),
    ACCESS_DENIED("Acesso negado!"),
    USER_DOESNT("Usuário não existe.");

    private final String desc;
}
