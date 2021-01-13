package br.edu.rocha.student.api.prod;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class APIHealthCheckITCase {
	
	@BeforeClass
	public static void setup() throws FileNotFoundException, IOException {
		
		String baseURL = System.getProperty("base.server.url");
        if (baseURL == null) {
        	baseURL = "http://localhost:8080/student-backend";
        }
		RestAssured.baseURI = baseURL;
	}
	
	@Test
	public void testGetAppVersion() {

		String version = RestAssured
			.given()
				.log().all()
			.when()
				.get("/api/appVersion")
			.then()
				.log().all()
				.statusCode(200)
				.extract().asString();

		
		assertTrue(version.startsWith("build_"));
	}
}

