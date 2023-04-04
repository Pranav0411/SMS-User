package com.usecase.project.service;

import java.util.List;


import com.usecase.project.payload.UserDataTransfer;

public interface Services {

	UserDataTransfer createStudent(UserDataTransfer st);
	UserDataTransfer updateStudent(UserDataTransfer st, int id);
	UserDataTransfer updateStudentByEmail(UserDataTransfer st, String email);
	UserDataTransfer getStudentbyId(int id);
	UserDataTransfer getStudentbyEmail(String email);
	List<UserDataTransfer> getAllStudents();
	void deleteStudent(String email);
	List<UserDataTransfer> findByRole(String string);
	Boolean roleVerification(String email,String role);
	
}
