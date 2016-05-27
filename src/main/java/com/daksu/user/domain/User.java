package com.daksu.user.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.daksu.entity.Entity;

@Document(collection = User.COLLECTION_NAME)
public class User implements UserDetails, Entity, Serializable {

	public static final String COLLECTION_NAME = "users";

	@Id
	@GeneratedValue
	private BigInteger id;

	@Column(unique = true, length = 16, nullable = false)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	@Column(length = 80, nullable = false)
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	public User() {
		/* Reflection instantiation */
	}

	public User(String name, String password) {
		this.username = name;
		this.password = password;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	


}
