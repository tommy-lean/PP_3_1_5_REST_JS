package ru.kata.spring.boot_security.demo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UsersRepository usersRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username){
        return usersRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }


    public User findById(Long id){
//        return usersRepository.findById(id).get();
        return usersRepository.getById(id);
    }


//    @Transactional
//    @Override
//    public void delete(User user) {
//        usersRepository.delete(user);
//    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (usersRepository.findById(id).isPresent()){
            usersRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }


    public void createModelForView(Model model, Principal principal){
        model.addAttribute("authUser", findByUsername(principal.getName()));
        model.addAttribute("users", listUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activeTab", "addUser");
    }
}
