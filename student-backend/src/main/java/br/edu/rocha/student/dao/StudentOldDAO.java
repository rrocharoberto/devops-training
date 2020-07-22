package br.edu.rocha.student.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.rocha.student.entities.Student;

@Repository
public class StudentOldDAO {

	@Autowired
	private EntityManager entityManager;

	public List<Student> findAll() {
		System.out.println("findAll of StudentOldDAO...");
		return entityManager.createQuery("select s from Student s", Student.class).getResultList();
	}
}
