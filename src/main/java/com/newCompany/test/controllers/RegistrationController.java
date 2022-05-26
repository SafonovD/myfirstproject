package com.newCompany.test.controllers;

import com.newCompany.test.dto.UserRegistrationDto;
import com.newCompany.test.model.User;
import com.newCompany.test.services.SecurityService;
import com.newCompany.test.services.UserService;
import com.newCompany.test.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final SecurityService securityService;

    @ModelAttribute("userDto")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userDto") @Valid UserRegistrationDto userDto, BindingResult bindingResult, Model model){

//        userValidator.validate(userDto, bindingResult);
        User userExists = userService.findByUsername(userDto.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.userDto",
                            "Такой пользователь уже существует");
        } if (bindingResult.hasErrors()) {
           return "registration";
        } else {
            userService.save(userDto.toUser());
            securityService.autoLogin(userDto.getUsername(), userDto.getConfirmPassword());
            model.addAttribute("successMessage", "User has been registered successfully");
        }
        return "redirect:/index";
    }
}
