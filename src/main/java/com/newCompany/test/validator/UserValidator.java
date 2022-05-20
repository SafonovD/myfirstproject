package com.newCompany.test.validator;

import com.newCompany.test.dto.UserRegistrationDto;
import com.newCompany.test.model.User;
import com.newCompany.test.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto user = (UserRegistrationDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","Required");
        if(user.getUsername().length() < 4 || user.getUsername().length() > 32){
            errors.rejectValue("username","Size.userForm.username");
        }

        if(userService.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username","Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","Required");
        if(user.getPassword().length() < 8 || user.getPassword().length() > 32){
            errors.rejectValue("password","Size.userForm.password");
        }
        if(!user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }

    }
}
