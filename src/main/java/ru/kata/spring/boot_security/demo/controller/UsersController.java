package ru.kata.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    private final RoleService roleService;

    private final UserValidator userValidator;

    @Autowired
    public UsersController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model){
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/admin/new")
    public String showFormAddUser(Model model){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roles);
        return "newUser";
    }


    @PostMapping
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "newUser";
        }
        userService.add(user);
        return "redirect:/users/admin";
    }

    @GetMapping("/admin/update")
    public String updateUserForm(Model model, @RequestParam("id") Long id){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", userService.findById(id));
        return "updateUser";
    }

    @PatchMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             Model model){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("allRoles", roles);
            model.addAttribute("user", user);
            return "updateUser";
        }
        userService.update(user);
        return "redirect:/users/admin";
    }

    @DeleteMapping()
    public String deleteUser(@ModelAttribute("user") User user){
        userService.delete(user);
        return "redirect:/users/admin";
    }






}
