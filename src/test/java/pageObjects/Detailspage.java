package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Detailspage extends Basepage {

    // Constructor to initialize WebDriver and call the parent class constructor
    public Detailspage(WebDriver driver) {
        super(driver); // Calls the constructor of Basepage to initialize driver and PageFactory
    }

    @FindBy(xpath = "//input[@id=\"first-name\"]")
    WebElement fname;

    @FindBy(xpath = "//input[@id=\"last-name\"]")
    WebElement lname;

    @FindBy(xpath = "//input[@id=\"postal-code\"]")
    WebElement pin;

    @FindBy(xpath = "//input[@type=\"submit\"]")
    WebElement submit;

    public void first_name(String FName)
    {
        fname.sendKeys(FName);
    }

    public void last_name(String LName)
    {
        lname.sendKeys(LName);
    }

    public  void zip(String number)
    {
        pin.sendKeys(number);
    }
    public void cart_check()
    {
        submit.click();
    }
}
