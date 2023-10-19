package com.group1.mstory.login;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

	private String email;
	private String pass;
	private int id;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Users user) {
		this.email = user.getEmail();
		this.pass = user.getPass();
		this.id = user.getId();
		this.authorities = Arrays.stream(user.getRole().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public UserDetailsImpl() {}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public String getUsername() {

		return email;
	}

	public int getId(){
		return id;
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
}
