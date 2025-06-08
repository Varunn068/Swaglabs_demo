package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Checkoutpage extends Basepage {

    public Checkoutpage(WebDriver driver)
    {
        super(driver);

    }

    @FindBy(xpath = "//a[@class='btn_action checkout_button']")
    WebElement checkout;


    public void checkbox()
    {
        checkout.click();
    }
}
