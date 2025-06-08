package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class loginpage extends Basepage {

    // Constructor to initialize WebDriver and PageFactory
    public loginpage(WebDriver driver) {
        super(driver); // Call to the Basepage constructor to initialize driver and PageFactory
    }

    // Page Elements
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "product_label")
    private WebElement productsLabel;

    @FindBy(className = "bm-burger-button")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public void enterUsername(String username) {

        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {

        loginButton.click();
    }

    public void performLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLoginSuccessful() {
        try {

            return productsLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {

            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }

    public void logout() {
        try {
            menuButton.click();
            logoutLink.click();
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }
}
