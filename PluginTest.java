package assignment;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class PluginTest {
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
    public void checkAndInstallDarkModePlugin() {
        driver.findElement(By.xpath("//div[text()='Plugins']")).click();
        boolean pluginInstalled = driver.findElements(By.xpath("//tr[contains(@data-slug, 'wp-dark-mode')]")).size() > 0;

        if (!pluginInstalled) {
            // Install the plugin
            driver.findElement(By.xpath("//a[text()='Add New']")).click();
            driver.findElement(By.id("plugin-search-input")).sendKeys("WP Dark Mode");
            driver.findElement(By.xpath("//button[text()='Install Now']")).click();
            driver.findElement(By.xpath("//button[text()='Activate']")).click();
        }

        Assertions.assertTrue(pluginInstalled, "WP Dark Mode plugin installed and activated successfully.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

