package com.usecase.project.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.usecase.project.payload.JwtResponse;
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
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	
@PutMapping("/updateuserbyEmail/{email}")
	
	public ResponseEntity<UserDataTransfer> updateStudentByEmail(@RequestBody UserDataTransfer studentDataTransfer, @PathVariable String email)
	{
		UserDataTransfer studentDataTransfer2 =  this.services.updateStudentByEmail(studentDataTransfer, email);
		return ResponseEntity.ok(studentDataTransfer2);
	}
	
	
	@DeleteMapping("/delete/{email}")
	
	public ResponseEntity<?> deleteStudent(@PathVariable String email)
	{
		this.services.deleteStudent(email);
		return new ResponseEntity<String>("Student Deletion Success", HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	
	public ResponseEntity<List<UserDataTransfer>> getAllStudents()
	{
		return ResponseEntity.ok(this.services.getAllStudents());
	}
	
	@GetMapping("/getbyId/{id}")
	
	public ResponseEntity<UserDataTransfer> getStudentbyId(@PathVariable int id)
	{
		return ResponseEntity.ok(this.services.getStudentbyId(id));
	}
	
	@GetMapping("/getbyEmail/{email}")
	
	public ResponseEntity<UserDataTransfer> getStudentByEmail(@PathVariable String email)
	{
		return ResponseEntity.ok(this.services.getStudentbyEmail(email));
	}
	
	@GetMapping("/teacher")
	public ResponseEntity<List<UserDataTransfer>> findByRole()
	{
		return ResponseEntity.ok(this.services.findByRole("ROLE_TEACHER"));
	}
	
//	@PostMapping("/authenticate")
//	public String authenticateandgettoken(@RequestBody Authrequest authrequest)
//	{
	//	 boolean verified = services.roleVerification(authrequest.getUsername(), authrequest.getRole());
		 
	//	 if(verified) {
	//	Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		
	//	if(authentication.isAuthenticated())
	//	{
	//		return jwtservice.generateToken(authrequest.getUsername());
		//}
		// }
		 
		//else {
			
			//	throw new UsernameNotFoundException("Invalid User");
		//	}
		
		//return null;
		//}
			
	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createToken(@RequestBody Authrequest request) throws Exception
	{
		boolean verified = services.roleVerification(request.getUsername(), request.getRole());
		if(verified)
		{
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = jwtservice.generateToken(userDetails);
		
		JwtResponse response = new JwtResponse();
		response.setToken(token);
		response.setUser(this.modelMapper.map((User)userDetails, UserDataTransfer.class));
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
		}
		else {
			
				throw new UsernameNotFoundException("Invalid User");
			}
	}

	private void authenticate(String email, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			
		} catch (BadCredentialsException e) {
			
			System.out.println("Credentials are not correct");
			throw new Exception("Invalid details");
			// TODO: handle exception
		}
		
		
		
			
		
		// TODO Auto-generated method stub
		
	}
		
		
	}
	


