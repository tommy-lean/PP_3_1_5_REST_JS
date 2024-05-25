package ru.kata.spring.boot_security.demo.service;





import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> listUsers();

    void add(User user);

//    void delete(User user);


    void deleteById(Long id);

    void update(User user);

    User findById(Long id);

    User findByUsername(String username);

    void createModelForView(Model model, Principal principal);

}
