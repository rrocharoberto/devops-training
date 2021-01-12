package br.edu.rocha.student.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.rocha.student.entities.Student;
import br.edu.rocha.student.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRest {

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/appVersion")
	public String getAppVersion() {
		System.out.println("Inside getAppVersion().");
		return appVersion;
	}
	
	@GetMapping("/fixedStudent")
	public Student getFixedStudent() {
		System.out.println("Inside getFixedStudent().");
		return new Student(1, "Student fixed 1", 2);
	}
	
	@GetMapping("/allStudents")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}
}
