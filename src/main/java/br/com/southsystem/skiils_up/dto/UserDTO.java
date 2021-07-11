package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.services.validation.UserUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@UserUpdate
public class UserDTO implements Serializable {

    private Long id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=3, max=120)
    private String firstName;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=3, max=120)
    private String lastName;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public UserDTO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
    }
}