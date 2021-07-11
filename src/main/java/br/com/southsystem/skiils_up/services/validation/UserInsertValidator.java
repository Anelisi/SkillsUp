package br.com.southsystem.skiils_up.services.validation;

import br.com.southsystem.skiils_up.dto.UserNewDTO;
import br.com.southsystem.skiils_up.controllers.exceptions.FieldMessage;
import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsert ann) {
    }

    @Override
    public boolean isValid(UserNewDTO userNewDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User aux = userRepository.findByEmail(userNewDTO.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "E-mail já existente"));
        }
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
