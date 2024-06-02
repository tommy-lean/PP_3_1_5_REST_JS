package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.util.NoSuchUserException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
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


    public User findById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(
                        "There is no employee with ID = '" + id + "' in Database"
                ));
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id);
        usersRepository.deleteById(id);

    }

    @Transactional
    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

}
