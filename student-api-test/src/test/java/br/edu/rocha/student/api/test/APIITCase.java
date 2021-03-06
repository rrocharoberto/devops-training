package br.edu.rocha.student.api.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APIITCase {
	
	@BeforeClass
	public static void setup() throws FileNotFoundException, IOException {
		
		String baseURL = System.getProperty("base.server.url");
        if (baseURL == null) {
        	baseURL = "http://localhost:8080/student-backend";
        }
		RestAssured.baseURI = baseURL;
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
		

		//precisa verificar qual estratégia usar pois o banco pode estar poluído
		//E daí pode dar erro quando for salvar, pois já existe o registro no banco.
		
		//Uma estratégia que eu percebi é rodar o banco em um container sem "volumes"
		//cada build vai gerar uma imagem nova zerada (inclusive do banco)
		RestAssured.given()
				.body("{" + 
						"    \"registry\": 111," + 
						"    \"name\": \"Student 111\"," + 
						"    \"category\": 2" + 
						"}")
				.contentType(ContentType.JSON)
			.when()
				.post("/students")
			.then()
				.statusCode(201);
	}
}

