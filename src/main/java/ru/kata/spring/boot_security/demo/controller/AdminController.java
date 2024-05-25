package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.SupportingService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final SupportingService supportingService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, SupportingService supportingService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.supportingService = supportingService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        User authUser = userService.findByUsername(principal.getName());
        model.addAttribute("newUser", new User());
//        supportingService.createModelForView(model, principal);
        model.addAttribute("authUser", authUser);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activeTab", "usersTable");
        /*updateUser*/
//        model.addAttribute("user", userService.findById(id));
        return "adminPageTest";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                          Principal principal, Model model) {
        userValidator.validate(user, bindingResult);
//        supportingService.createModelForView(model, principal);
        model.addAttribute("authUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activeTab", "addUser");
        if (bindingResult.hasErrors()) {
            return "adminPageTest"; // TODO!!!!!!!!!!!!!!!!!!
        }
        userService.add(user);
        return "redirect:/users/admin";
    }


    @PatchMapping("/admin/edit")
    public String updateUser(@ModelAttribute("userIter") @Valid User user,
                             BindingResult bindingResult,
                             Model model, Principal principal) {
//        userValidator.validate(user, bindingResult);
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("authUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("activeTab", "usersTable");
//        model.addAttribute("newUser", new User());
        if (bindingResult.hasErrors()) {
            return "adminPageTest";
        }
        userService.update(user);
        return "redirect:/users/admin";
    }

    @DeleteMapping("/admin")
    public String deleteUser(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        userService.deleteById(id);
        return "redirect:/users/admin";
    }


//    @GetMapping("/admin/new")
//    public String showFormAddUser(Model model, Principal principal){
//        List<Role> roles = roleService.getAllRoles();
//        model.addAttribute("user", new User());
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("thisUser", user);
//        model.addAttribute("allRoles", roles);
//        return "newUser2";
//    }


//    @GetMapping("/admin/update")
//    public String updateUserForm(Model model, @RequestParam(value = "id") Long id){
//        List<Role> roles = roleService.getAllRoles();
//        model.addAttribute("allRoles", roles);
//        model.addAttribute("user", userService.findById(id));
//        return "updateUser";
//    }


}
