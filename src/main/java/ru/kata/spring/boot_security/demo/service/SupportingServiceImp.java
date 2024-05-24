package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class SupportingServiceImp implements SupportingService {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public SupportingServiceImp(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Populates the given {@link Model} with the necessary data for the administrative view.
     *
     * @param model     the {@link Model} to be populated
     * @param principal the currently authenticated {@link Principal}
     */

    @Override
    public void createModelForView(Model model, Principal principal) {
        model.addAttribute("authUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activeTab", "addUser");
    }
}
