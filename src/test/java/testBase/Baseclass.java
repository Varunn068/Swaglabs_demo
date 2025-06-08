package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

public class Baseclass {

    static public WebDriver driver;
    public Properties p;
    public Logger logger;

    @BeforeClass
    @Parameters({"browser"})
    public void setup(String br) throws Exception {
        logger = (Logger) LogManager.getLogger(this.getClass());
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        switch (br.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Invalid browser name. Defaulting to Chrome.");
                driver = new ChromeDriver();
                break;
        }

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        // Set implicit wait using modern approach
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(p.getProperty("appURL"));
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void captureScreen(String tname) throws IOException {
        // Fix timestamp format - avoid using forward slashes in filenames
        String timeStamp = new SimpleDateFormat("ddMMyyyy-HHmmss").format(new Date());

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File destination = new File(System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png");

        // Remove duplicate line - only need to copy once
        FileUtils.copyFile(screenshot, destination);
    }
}