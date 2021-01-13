package br.edu.rocha.student.functional.prod;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HealthCheckITCase {

	private static String baseURL;

	@BeforeClass
	public static void setup() throws FileNotFoundException, IOException {
		baseURL = System.getProperty("base.server.url");
		if (baseURL == null) {
			baseURL = "http://localhost:4200/students";
		}
	}

	@Test
	public void healthCheck() throws MalformedURLException {
		WebDriver driver = prepareDriver();
		try {
			String version = driver.findElement(By.id("app_version")).getText();
			System.out.println("Version retrieved: " + version);
			Assert.assertTrue(version.startsWith("build_"));
		} finally {
			driver.quit();
		}
	}

	private WebDriver prepareDriver() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--silent");
		WebDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(baseURL);
		return driver;
	}
}
