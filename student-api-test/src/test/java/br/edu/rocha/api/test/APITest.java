package br.edu.rocha.api.test;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8080/student-backend";
	}
	
	@Test
	public void testGetFixedStudent() {
		RestAssured.given()
				.log().all()
			.when()
				.get("/api/fixedStudent")
			.then()
				.log().all()
				.statusCode(200);
	}
	
	@Test
	public void testGetStudents() {
		RestAssured.given()
				.log().all()
			.when()
				.get("/students")
			.then()
				.log().all()
				.statusCode(200);
	}
	
	@Test
	public void testSaveStudent() {
		

		//TODO: precisa verificar qual estratégia usar pois o banco pode estar poluído
		//E daí pode dar erro quando for salvar, pois já existe o registro no banco.
		
		//Uma estratégia que eu percebi é rodar o banco em um container sem "volumes"
		RestAssured.given()
				.body("{" + 
						"    \"register\": 111," + 
						"    \"name\": \"Student 111\"" + 
						"}")
				.contentType(ContentType.JSON)
			.when()
				.post("/students")
			.then()
				.statusCode(201);
	}
}

