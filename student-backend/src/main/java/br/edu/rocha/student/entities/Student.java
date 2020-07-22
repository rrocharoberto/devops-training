package br.edu.rocha.student.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student implements Serializable {

	private static final long serialVersionUID = 7876988844153860663L;

	@Id
	@Column(name = "REGISTRATION")
	private Integer registry;

	private String name;

	public Student() {
	}

	public Student(Integer registry, String name) {
		super();
		this.registry = registry;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [registry=" + registry + ", name=" + name + "]";
	}

	public Integer getRegistry() {
		return registry;
	}

	public void setRegistry(Integer registry) {
		this.registry = registry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
