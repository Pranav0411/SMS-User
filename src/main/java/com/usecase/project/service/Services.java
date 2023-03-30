package com.usecase.project.service;

import java.util.List;


import com.usecase.project.payload.UserDataTransfer;

public interface Services {

	UserDataTransfer createStudent(UserDataTransfer st);
	UserDataTransfer updateStudent(UserDataTransfer st, int id);
	UserDataTransfer getStudentbyId(int id);
	List<UserDataTransfer> getAllStudents();
	void deleteStudent(int id);
	List<UserDataTransfer> findByRole(String string);
	Boolean roleVerification(String email,String role);
	
}
