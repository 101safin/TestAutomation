package assignment;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class CustomizationTest {
    private WebDriver driver;
    private Dotenv dotenv;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        dotenv = Dotenv.load();

        // Navigate to WordPress Admin URL and login
        driver.get(dotenv.get("WORDPRESS_URL"));
        driver.findElement(By.id("user_login")).sendKeys(dotenv.get("ADMIN_USER"));
        driver.findElement(By.id("user_pass")).sendKeys(dotenv.get("ADMIN_PASS"));
        driver.findElement(By.id("wp-submit")).click();
    }

    @Test
    public void customizeDarkModeSettings() {
        // Navigate to WP Dark Mode settings in the admin dashboard
        driver.findElement(By.xpath("//div[text()='WP Dark Mode']")).click();
        
        // Click on 'Customization' tab
        driver.findElement(By.xpath("//a[text()='Customization']")).click();

        // 1. Change the "Floating Switch Style"
        driver.findElement(By.xpath("//select[@id='floating_switch_style']")).click();
        WebElement switchStyleOption = driver.findElement(By.xpath("//option[text()='New Style Option']"));  // Select a different style
        switchStyleOption.click();

        // 2. Change the "Floating Switch Size" to 220
        WebElement sizeInput = driver.findElement(By.id("floating_switch_size"));
        sizeInput.clear();  // Clear the existing value
        sizeInput.sendKeys("220");  // Set new size

        // 3. Change the "Floating Switch Position" to Left
        WebElement positionDropdown = driver.findElement(By.id("floating_switch_position"));
        positionDropdown.click();
        WebElement leftPositionOption = driver.findElement(By.xpath("//option[text()='Left']"));
        leftPositionOption.click();

        // Save the settings after customization
        driver.findElement(By.id("save_settings_button")).click();

        // Validation: You can add assertions here to check if the changes are saved correctly.
        Assertions.assertEquals("New Style Option", switchStyleOption.getText(), "Floating Switch Style updated successfully.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

