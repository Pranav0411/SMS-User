package com.usecase.project.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usecase.project.model.User;



	
public interface Userrepo extends JpaRepository<User, Integer>{

	//Optional<User> findByEmail(String username);
	User findByEmail(String username);
	
	User findByEmailAndRole(String email,String Role);
	
	List<User> findByRole(String role);
		
	

}



