package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Homepage extends Basepage{

    public Homepage(WebDriver driver)
    {
        super(driver);
    }

    // Page Elements
    @FindBy(className = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(xpath = "//button[contains(@class, 'btn_primary') and contains(@class, 'btn_inventory')]")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//div[@class=\"shopping_cart_container\"]")
    private WebElement cartbtn;

    public List<String> getAllItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Add item to cart by exact name match
     */
    public void addItemToCartByName(String itemName) {
        for (WebElement item : inventoryItems) {
            WebElement nameElement = item.findElement(By.className("inventory_item_name"));

            if (nameElement.getText().trim().equals(itemName.trim())) {
                WebElement addButton = item.findElement(By.xpath(".//button[contains(@class, 'btn_primary') and contains(@class, 'btn_inventory')]"));
                addButton.click();
                System.out.println("Added to cart: " + itemName);
                return;
            }
        }
        System.out.println("Item not found: " + itemName);
    }

    public void cart_btn()
    {
        cartbtn.click();
    }

}





