package com.blogapplication.repository;

import org.apache.catalina.User;

public interface UserDao {
    User getUserById(Long id);

}
