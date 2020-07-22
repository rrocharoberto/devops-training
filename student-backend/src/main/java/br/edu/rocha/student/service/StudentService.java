package br.edu.rocha.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.rocha.student.dao.StudentOldDAO;
import br.edu.rocha.student.entities.Student;

@Service
public class StudentService {

	@Autowired
	private StudentOldDAO studentDAO;
//	private StudentNewDAO studentDAO;
	
	public List<Student> getAllStudents() {
		return studentDAO.findAll();
	}
}
