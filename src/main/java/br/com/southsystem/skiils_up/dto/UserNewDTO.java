package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.enums.ClientProfile;
import br.com.southsystem.skiils_up.enums.UserCategories;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UserNewDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 120)
    private String firstName;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 120)
    private String lastName;

    @NotEmpty(message = "Preenchimento obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    private String birthday;

    @NotEmpty
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    @Enumerated
    private UserCategories category;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String street;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String civicNumber;

    private String complement;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String postalCode;

    private String city;
    private String state;

    @Enumerated
    private ClientProfile client;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String telephone1;

    private String telephone2;
    private String telephone3;
}
