package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Cartpage extends Basepage {

    // Constructor to initialize WebDriver and call the parent class constructor
    public Cartpage(WebDriver driver) {
        super(driver); // Correct call to the parent class (Basepage) constructor
    }

    @FindBy(xpath = "//a[@class=\"btn_action cart_button\"]")
    WebElement finalbtn;

    @FindBy(xpath = "//h2[text()=\"THANK YOU FOR YOUR ORDER\"]")
    WebElement message;

    public void final_check()
    {
        finalbtn.click();
    }

   public String text()
   {
     String result=  message.getText();
     return result;
   }
}
