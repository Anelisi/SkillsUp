package br.com.southsystem.skiils_up.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class CredentialsDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    private String email;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

}
