package com.newCompany.test.services;

import com.newCompany.test.model.User;

import java.util.List;

public interface UserService {

    User save(User username);

    User userUpdate(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);


}
