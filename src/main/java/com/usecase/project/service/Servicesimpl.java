package com.usecase.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.usecase.project.Exception.ResourceNotFoundException;
import com.usecase.project.model.User;
import com.usecase.project.payload.UserDataTransfer;
import com.usecase.project.repository.Userrepo;

@Service
public class Servicesimpl implements Services {
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private Userrepo userrepo;

	public Servicesimpl(Userrepo userrepo) {
		super();
		this.userrepo = userrepo;
	}

	@Override
	public UserDataTransfer createStudent(UserDataTransfer st) {
		User student = this.dtoStudent(st);
		User savedstudent = this.userrepo.save(student);
		// TODO Auto-generated method stub
		return this.SttoDto(savedstudent);
	}

	@Override
	public UserDataTransfer updateStudent(UserDataTransfer stdo, int id) {
		User student = this.userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User" , "User-Id" , id));
		// TODO Auto-generated method stub
		student.setName(stdo.getName());
		student.setEmail(stdo.getEmail());
		student.setRole(stdo.getRole());
		student.setSubject(stdo.getSubject());
		
		
		User updateStudent = this.userrepo.save(student);
		UserDataTransfer stdo1 =  this.SttoDto(updateStudent);
		
		return stdo1;
	}

	@Override
	public UserDataTransfer getStudentbyId(int id) {
		User student = this.userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Student" , "Studentid" , id));
		// TODO Auto-generated method stub
		return this.SttoDto(student);
	}

	@Override
	public List<UserDataTransfer> getAllStudents() {
		List<User> l = this.userrepo.findAll();
		List<UserDataTransfer> stdo =  l.stream().map(student->this.SttoDto(student)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return stdo;
	}

	@Override
	public void deleteStudent(String email) {
		
		User student = this.userrepo.findByEmail(email);
		this.userrepo.delete(student);
		// TODO Auto-generated method stub
		
	}
	
	
	
	private User dtoStudent(UserDataTransfer studentDataTransfer)
	{
		User st  = new User();
	
		st.setUser_id(studentDataTransfer.getId());
		st.setName(studentDataTransfer.getName());
		st.setEmail(studentDataTransfer.getEmail());
		st.setPassword(passwordEncoder.encode(studentDataTransfer.getPassword()));
		st.setRole(studentDataTransfer.getRole());
		st.setSubject(studentDataTransfer.getSubject());
		return st;
		
	}
	
	public UserDataTransfer SttoDto(User student)
	{
		UserDataTransfer stdo = new UserDataTransfer();	
		stdo.setId(student.getUser_id());
		stdo.setName(student.getName());
		stdo.setEmail(student.getEmail());
		stdo.setPassword(passwordEncoder.encode(student.getPassword()));
		stdo.setRole(student.getRole());
		stdo.setSubject(student.getSubject());
		return stdo;
	
	}

	@Override
	public List<UserDataTransfer> findByRole(String role) {
		List<User> l = this.userrepo.findByRole(role);
		List<UserDataTransfer> stdo =  l.stream().map(student->this.SttoDto(student)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return stdo;
	}
	
	public Boolean roleVerification(String email,String role) {
        User user = userrepo.findByEmailAndRole(email, role);
        if(user!=null) {
            return true;
        }
        else {
            return false;
        }
    }

	@Override
	public UserDataTransfer updateStudentByEmail(UserDataTransfer stdo, String email) {
		User student = this.userrepo.findByEmail(email);
		// TODO Auto-generated method stub
		student.setName(stdo.getName());
		student.setEmail(stdo.getEmail());
		student.setRole(stdo.getRole());
		student.setSubject(stdo.getSubject());
		
		
		User updateStudent = this.userrepo.save(student);
		UserDataTransfer stdo1 =  this.SttoDto(updateStudent);
		
		return stdo1;
	}

	@Override
	public UserDataTransfer getStudentbyEmail(String email) {
		User student = this.userrepo.findByEmail(email);
		// TODO Auto-generated method stub
		return this.SttoDto(student);
	}

}
