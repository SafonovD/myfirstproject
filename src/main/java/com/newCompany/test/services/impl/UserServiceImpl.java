package com.newCompany.test.services.impl;


import com.newCompany.test.model.Role;
import com.newCompany.test.model.Status;
import com.newCompany.test.model.User;
import com.newCompany.test.repositories.RoleRepository;
import com.newCompany.test.repositories.UserRepository;
import com.newCompany.test.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User save(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        }
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }
    public User userUpdate(User user){
        Date createDate = (findById(user.getId()).getCreated());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreated(createDate);
        user.setUpdated(new Date());
        User updateUser = userRepository.save(user);
        log.info("IN update - user: {} successfully update", updateUser);
        return updateUser;
    }


    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found ",result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByName - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if(result == null){
            log.warn("IN findById - no user faund by id: {}",id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", result,id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long idCode) {
        userRepository.deleteById(idCode);
        log.info("IN delete - user with id: {} successfully delete",idCode);
    }
}
