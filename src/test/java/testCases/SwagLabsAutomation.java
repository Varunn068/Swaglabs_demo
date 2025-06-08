import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class SwagLabsAutomation {

    private WebDriver driver;
    private WebDriverWait wait;

    // Hardcoded login credentials
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String BASE_URL = "https://www.saucedemo.com/v1/";

    public void setUp() {
        // Initialize Chrome driver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    public void login() {
        System.out.println("Navigating to Swag Labs...");
        driver.get(BASE_URL);

        System.out.println("Logging in with hardcoded credentials...");
        // Find and fill username
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        usernameField.sendKeys(USERNAME);

        // Find and fill password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(PASSWORD);

        // Click login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        System.out.println("Successfully logged in!");
    }

    public void selectAndAddItem() {
        System.out.println("Adding item to cart...");

        // Wait for products page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_list")));

        // Find the first "ADD TO CART" button and click it (for v1, buttons have different IDs)
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[contains(text(),'ADD TO CART')]"));
        if (!addToCartButtons.isEmpty()) {
            WebElement firstAddButton = addToCartButtons.get(0);

            // Get item name from the same container
            WebElement itemContainer = firstAddButton.findElement(By.xpath("./ancestor::div[@class='inventory_item']"));
            String itemName = itemContainer.findElement(By.className("inventory_item_name")).getText();

            firstAddButton.click();
            System.out.println("Added item to cart: " + itemName);
        }

        // Click on cart icon (v1 uses different class/structure)
        WebElement cartIcon = driver.findElement(By.xpath("//div[@id='shopping_cart_container']//a"));
        cartIcon.click();
        System.out.println("Navigated to cart page");
    }

    public void proceedToCheckout() {
        System.out.println("Proceeding to checkout...");

        // Wait for cart page to load and click checkout (v1 uses different selectors)
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn_action checkout_button']")));
        checkoutButton.click();

        // Fill checkout information (hardcoded) - v1 uses same IDs
        System.out.println("Filling checkout information...");
        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        firstName.sendKeys("John");

        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Doe");

        WebElement postalCode = driver.findElement(By.id("postal-code"));
        postalCode.sendKeys("12345");

        // Continue to overview (v1 uses different selector)
        WebElement continueButton = driver.findElement(By.xpath("//input[@class='btn_primary cart_button']"));
        continueButton.click();
        System.out.println("Moved to checkout overview");
    }

    public void completeOrder() {
        System.out.println("Completing the order...");

        // Wait for overview page and finish order (v1 uses different selector)
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn_action cart_button']")));
        finishButton.click();

        // Verify order completion (v1 uses different class)
        WebElement confirmationMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("complete-header")));
        System.out.println("Order completed! Message: " + confirmationMessage.getText());
    }

    public void tearDown() {
        System.out.println("Closing browser...");
        if (driver != null) {
            driver.quit();
        }
    }

    public void runFullTest() {
        try {
            setUp();
            login();
            Thread.sleep(2000); // Brief pause to see the action

            selectAndAddItem();
            Thread.sleep(2000);

            proceedToCheckout();
            Thread.sleep(2000);

            completeOrder();
            Thread.sleep(3000); // Pause to see completion message

            System.out.println("Test completed successfully!");

        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            tearDown();
        }
    }

    public static void main(String[] args) {
        SwagLabsAutomation test = new SwagLabsAutomation();
        test.runFullTest();
    }
}