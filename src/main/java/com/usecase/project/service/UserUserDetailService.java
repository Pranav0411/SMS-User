package com.usecase.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.usecase.project.config.UserInfoDetails;
import com.usecase.project.model.User;
import com.usecase.project.repository.Userrepo;

@Component
public class UserUserDetailService implements UserDetailsService {

	@Autowired
	private Userrepo userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userrepo.findByEmail(username);
		
		return user.map(UserInfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("user not found"));
		// TODO Auto-generated method stub
		
	}

}
