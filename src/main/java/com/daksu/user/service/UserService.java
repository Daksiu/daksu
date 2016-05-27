package com.daksu.user.service;

import com.daksu.user.bean.UserBean;

public interface UserService {

	void addUser(UserBean userBean);

	UserBean findUserByUsername(String emailId);

}
