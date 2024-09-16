package assignment;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class LoginTest {
    private WebDriver driver;
    private Dotenv dotenv;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        dotenv = Dotenv.load(); // Load environment variables
        driver.get(dotenv.get("WORDPRESS_URL"));
    }

    @Test
    public void loginToWordpress() {
        driver.findElement(By.id("user_login")).sendKeys(dotenv.get("ADMIN_USER"));
        driver.findElement(By.id("user_pass")).sendKeys(dotenv.get("ADMIN_PASS"));
        driver.findElement(By.id("wp-submit")).click();

        // Verify successful login
        String dashboardTitle = driver.getTitle();
        Assertions.assertEquals("Dashboard ‹ My WordPress Site — WordPress", dashboardTitle);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

