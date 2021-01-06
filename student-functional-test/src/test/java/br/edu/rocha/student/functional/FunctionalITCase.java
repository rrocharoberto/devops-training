package br.edu.rocha.student.functional;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class FunctionalITCase {

	private static String baseURL;

	@BeforeClass
	public static void setup() throws FileNotFoundException, IOException {
		baseURL = System.getProperty("base.server.url");
        if (baseURL == null) {
        	baseURL = "http://localhost:4200/students";
        }
	}
	
	@Test
	public void saveNewStudent() {
		WebDriver driver = prepareDriver();
		try {

			// click in add student
			driver.findElement(By.id("addStudent")).click();

			// write id and
			driver.findElement(By.id("registry")).sendKeys("2");

			// write name
			driver.findElement(By.id("name")).sendKeys("Student 02");

			// choose category
			Select dropdown = new Select(driver.findElement(By.id("category")));
			dropdown.selectByVisibleText("Special");

			// save student
			driver.findElement(By.id("saveStudent")).click();

			// check success message
			String text = driver.findElement(By.id("message")).getText();
			assertEquals("Student 2 registration Success!", text);

		} finally {
			// close the driver
			driver.quit();
		}
	}

	private WebDriver prepareDriver() {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(baseURL);
		return driver;
	}
}
