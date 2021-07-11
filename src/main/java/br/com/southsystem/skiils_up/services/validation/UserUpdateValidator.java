package br.com.southsystem.skiils_up.services.validation;

import br.com.southsystem.skiils_up.dto.UserDTO;
import br.com.southsystem.skiils_up.controllers.exceptions.FieldMessage;
import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, UserDTO> {

    private HttpServletRequest request;

    private UserRepository userRepository;

    @Override
    public void initialize(UserUpdate ann) {
    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User aux = userRepository.findByEmail(userDTO.getEmail());
        if (aux != null && !aux.getId().equals(uriId)) {
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

