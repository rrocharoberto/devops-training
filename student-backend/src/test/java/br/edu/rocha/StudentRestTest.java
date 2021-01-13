package br.edu.rocha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import br.edu.rocha.student.entities.Student;
import br.edu.rocha.student.rest.StudentRest;

public class StudentRestTest {

    @Test
    public void testFixedStudent() {
        StudentRest rest = new StudentRest();
        ResponseEntity<Student> std = rest.getFixedStudent();
        assertEquals(1, std.getBody().getRegistry());
    }
}
