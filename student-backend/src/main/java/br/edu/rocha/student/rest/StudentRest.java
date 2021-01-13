package br.edu.rocha.student.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.rocha.student.entities.Student;
import br.edu.rocha.student.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRest {

	private Logger logger = LoggerFactory.getLogger(StudentRest.class);

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/appVersion")
	public ResponseEntity<String> getAppVersion() {
		logger.info("Inside getAppVersion().");
		return ResponseEntity.ok(appVersion);
	}
	
	@GetMapping("/fixedStudent")
	public ResponseEntity<Student> getFixedStudent() {
		logger.info("Inside getFixedStudent().");
		return ResponseEntity.ok(new Student(1, "Student fixed 1", 2));
	}
	
	@GetMapping("/allStudents")
	public List<Student> getAllStudents() {
		logger.info("Inside allStudents().");
		return studentService.getAllStudents();
	}
}
