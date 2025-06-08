package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.Baseclass;

public class TC03_Addtocart extends Baseclass {

    @Test(groups = "Acceptance")
    public void testAddSpecificItemToCart() throws Exception{
       logger.info("cart test");
        try{
        loginpage Loginpage = new loginpage(driver);
        Loginpage.enterUsername(p.getProperty("Username"));
        Loginpage.enterPassword(p.getProperty("Password"));
        Loginpage.clickLogin();
        Thread.sleep(1300);

        Homepage home = new Homepage(driver);
        home.addItemToCartByName("Sauce Labs Bike Light");
        System.out.println("updated 🛒");
        home.addItemToCartByName("Test.allTheThings() T-Shirt (Red)");
        System.out.println("updated 🛒");
        home.addItemToCartByName("Sauce Labs Onesie");
        System.out.println("updated 🛒");
        home.addItemToCartByName("Sauce Labs Bolt T-Shirt");
        System.out.println("updated 🛒");
        home.addItemToCartByName("Sauce Labs Backpack");
        System.out.println("updated 🛒");
        home.addItemToCartByName("Sauce Labs Fleece Jacket");
        System.out.println("updated 🛒");
        Thread.sleep(1000);
        home.cart_btn();
        System.out.println("[Button] 🛒");
        Thread.sleep(1000);
        Checkoutpage checkoutpage =new Checkoutpage(driver);
        checkoutpage.checkbox();
        Detailspage detailspage = new Detailspage(driver);
        detailspage.first_name(p.getProperty("Customer_first"));
        detailspage.last_name(p.getProperty("Customer_last"));
        detailspage.zip(p.getProperty("piny"));
        detailspage.cart_check();
        Thread.sleep(2000);
        Cartpage cartpage = new Cartpage(driver);
        cartpage.final_check();
        Thread.sleep(2000);
       String result="THANK YOU FOR YOUR ORDER";
            Assert.assertEquals(result,cartpage.text());
            System.out.println("Sucessgull");
            logger.info("PASS");
       }
       catch (Exception e) {
        System.err.println("Test failed with error: " + e.getMessage());
        e.printStackTrace();
        String tname="TC03_Addtocart";
        captureScreen(tname);
           logger.info("FAIL");
    }
    }
}
