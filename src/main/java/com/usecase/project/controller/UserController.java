package com.usecase.project.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.project.model.User;
import com.usecase.project.payload.UserDataTransfer;
import com.usecase.project.security.Authrequest;
import com.usecase.project.service.Jwtservice;
import com.usecase.project.service.Services;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private Services services;
	
	@Autowired
	private Jwtservice jwtservice;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/home")
	public String home() {
		return "welcome";
	}
	
	@PostMapping("/create")
	public ResponseEntity<UserDataTransfer> createStudent(@RequestBody UserDataTransfer stdo)
	{
		UserDataTransfer studentDataTransfer =  this.services.createStudent(stdo);
		return new ResponseEntity<>(studentDataTransfer,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/updateuser/{id}")
	
	public ResponseEntity<UserDataTransfer> updateStudent(@RequestBody UserDataTransfer studentDataTransfer, @PathVariable int id)
	{
		UserDataTransfer studentDataTransfer2 =  this.services.updateStudent(studentDataTransfer, id);
		return ResponseEntity.ok(studentDataTransfer2);
	}
	
	
	@DeleteMapping("/delete/{id}")
	
	public ResponseEntity<?> deleteStudent(@PathVariable int id)
	{
		this.services.deleteStudent(id);
		return new ResponseEntity<String>("Student Deletion Success", HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	
	public ResponseEntity<List<UserDataTransfer>> getAllStudents()
	{
		return ResponseEntity.ok(this.services.getAllStudents());
	}
	
	@GetMapping("/getbyid/{id}")
	
	public ResponseEntity<UserDataTransfer> getStudentbyId(@PathVariable int id)
	{
		return ResponseEntity.ok(this.services.getStudentbyId(id));
	}
	
	@GetMapping("/teacher")
	public ResponseEntity<List<UserDataTransfer>> findByRole()
	{
		return ResponseEntity.ok(this.services.findByRole("ROLE_TEACHER"));
	}
	
	@PostMapping("/authenticate")
	public String authenticateandgettoken(@RequestBody Authrequest authrequest)
	{
		 boolean verified = services.roleVerification(authrequest.getUsername(), authrequest.getRole());
		 
		 if(verified) {
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		
		if(authentication.isAuthenticated())
		{
			return jwtservice.generateToken(authrequest.getUsername());
		}
		 }
		 
		else {
			
				throw new UsernameNotFoundException("Invalid User");
			}
		
		return null;
		}
			
		
		
	}
	


