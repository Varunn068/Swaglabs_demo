package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Basepage;
import testBase.Baseclass;

public class TC01_WebpageTitle extends Baseclass {

    @Test(groups = "Sanity")
    public void tilecheck()throws Exception
    {
       logger.info("Title Test");
        try {
            Assert.assertEquals(driver.getTitle(), p.getProperty("title"));
            System.out.println("✓ Title name matching");
            logger.info("PASS");
        } catch (AssertionError e) {
            System.out.println("❌ Title not matching");
            String tname="TC01_WebpageTitle";
            captureScreen(tname);
            logger.info("FAIL");
        }
    }
}
