package com.daksu.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daksu.user.bean.UserBean;
import com.daksu.user.domain.User;
import com.daksu.user.repositoty.UserRepository;
import com.daksu.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void addUser(UserBean userBean) {		
		User user = new User();
		user.setUsername(userBean.getUsername());
		user.setFirstName(userBean.getFirstName());
		user.setLastName(userBean.getLastName());
		user.setRoles(userBean.getRoles());		
		user.setPassword(passwordEncoder.encode(userBean.getPassword()));
		userRepository.addUser(user);
	}
	
	@Override
	public UserBean findUserByUsername(String emailId) {
		User user = userRepository.findUserByUsername(emailId);
		UserBean userBean = new UserBean();
		userBean.setUsername(user.getUsername());
		userBean.setFirstName(user.getFirstName());
		userBean.setLastName(user.getLastName());
		userBean.setRoles(user.getRoles());
		return userBean;
	}
	
}
