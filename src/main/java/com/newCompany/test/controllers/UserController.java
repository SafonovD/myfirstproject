package com.newCompany.test.controllers;

import com.newCompany.test.model.User;
import com.newCompany.test.repositories.RoleRepository;
import com.newCompany.test.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/user")
    public String userList(Model model){
        model.addAttribute("user", userService.getAll());
        return "userList";
    }

    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("useredit", user);
        model.addAttribute("roleList", roleRepository.findAll());
        return "/edit";
    }

    @PostMapping("/user/update")
    public String userUpdate(User user){
        userService.userUpdate(user);
        return "redirect:/user";
    }

    @GetMapping("/user/delete/{user}")
    public String deleteUser(@PathVariable ("user") Long id){
        userService.deleteById(id);
        return "redirect:/user";
    }
}
