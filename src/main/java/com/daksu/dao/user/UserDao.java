package com.daksu.dao.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.daksu.dao.Dao;
import com.daksu.user.domain.User;

public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}