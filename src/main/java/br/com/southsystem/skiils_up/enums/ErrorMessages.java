package br.com.southsystem.skiils_up.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    AUTHOR_PROFILE_NOT_FOUND ("Este perfil de professor não existe."),
    CANT_DELETE_AUTHOR_PROFILE("Não é possível excluir um perfil vinculado a um professor"),
    NON_EXIST_ENABLE_STATUS("Status de habilitação inexistente \n"),
    NON_EXISTENT_ID_USER("IdUser inexistente \n"),
    STUDENT_PROFILE_NOT_FOUND("Este perfil de estudante não existe."),
    CANT_DELETE_STUDENT_PROFILE ("Não é possível excluir um perfil vinculado a um estudante"),
    IMPOSSIBLE_DELETE("Não é possível excluir um pedido que esteja vinculado a algum curso"),
    ACCESS_DENIED("Acesso negado!"),
    VALIDATION_COURSE("Não é possível excluir um curso que possua avaliações ou que esteja inserido em algum pedido ou salvo como favorito"),
    NON_EXIST_ID_COURSE("Id do Curso inexistente!"),
    NON_EXIST_USER("Usuário não existe.");

    private final String desc;
}
