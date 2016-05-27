package com.daksu.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.daksu.dao.JpaDao;
import com.daksu.user.domain.User;
import com.daksu.user.repositoty.UserRepository;

public class JpaUserDao extends JpaDao<User, Long> implements UserDao {

	@Autowired
	private UserRepository userRepository;
	
	public JpaUserDao() {
		super(User.class);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.findByName(username);
		if (null == user) {
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findByName(String name) {
		return userRepository.findUserByUsername(name);
	}

}
