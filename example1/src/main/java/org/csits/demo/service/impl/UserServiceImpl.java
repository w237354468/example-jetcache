package org.csits.demo.service.impl;

import org.csits.demo.domain.User;
import org.csits.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Integer id) {
        return new User(id, "testUser" + id);
    }
}
