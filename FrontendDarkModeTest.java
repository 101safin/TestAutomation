package assignment;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class FrontendDarkModeTest {
    private WebDriver driver;
    private Dotenv dotenv;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        dotenv = Dotenv.load();

        // Navigate to WordPress Admin URL and login to WordPress Admin
        driver.get(dotenv.get("WORDPRESS_URL"));
        driver.findElement(By.id("user_login")).sendKeys(dotenv.get("ADMIN_USER"));
        driver.findElement(By.id("user_pass")).sendKeys(dotenv.get("ADMIN_PASS"));
        driver.findElement(By.id("wp-submit")).click();

        // Ensure the login was successful by checking for the WordPress Dashboard title
        Assertions.assertEquals("Dashboard ‹ My WordPress Site — WordPress", driver.getTitle());
    }

    @Test
    public void validateDarkModeOnFrontend() {
        // Step 1: Navigate to the frontend of the WordPress site
        driver.navigate().to(dotenv.get("WORDPRESS_FRONTEND_URL")); // Use the URL of the frontend

        // Step 2: Check if the dark mode class is present in the body tag
        WebElement bodyElement = driver.findElement(By.tagName("body"));
        String bodyClassAttribute = bodyElement.getAttribute("class");

        // Step 3: Validate that dark mode is active on the frontend
        boolean isDarkModeEnabled = bodyClassAttribute.contains("wp-dark-mode-active");

        // Assert that dark mode is enabled
        Assertions.assertTrue(isDarkModeEnabled, "Dark mode should be enabled on the frontend.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

