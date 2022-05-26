package com.newCompany.test.dto;


import com.newCompany.test.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
public class UserRegistrationDto {

    private Long id;

    @Length(min = 4, message = "*Ваше имя пользователя должно содержать не менее 5 символов")
    @NotEmpty(message = "*Пожалуйста, укажите имя пользователя")
    private String username;
    @NotEmpty(message = "*Пожалуйста, укажите имя")
    private String firstName;
    @NotEmpty(message = "*Пожалуйста, укажите Фамилию")
    private String lastName;
    @NotEmpty(message = "*Пожалуйста, укажите EMAIL")
    private String email;
    @Length(min = 8, message = "*Пароль должен быть более 8 символов.")
    @NotEmpty(message = "*Пожалуйста, укажите пароль")
    private String password;
    @Length(min = 8, message = "*Пароль должен быть более 8 символов.")
    @NotEmpty(message = "*Пожалуйста, укажите пароль")
    private String confirmPassword;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static UserRegistrationDto fromUser(User user) {
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

}



