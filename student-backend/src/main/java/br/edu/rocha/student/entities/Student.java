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

	private Integer category;

	public Student() {
	}

	public Student(Integer registry, String name, Integer category) {
		super();
		this.registry = registry;
		this.name = name;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Student [registry=" + registry + ", name=" + name+ ", category=" + category + "]";
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
}
