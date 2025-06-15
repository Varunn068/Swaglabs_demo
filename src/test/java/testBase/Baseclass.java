package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
    @Parameters({"os","browser"})
    public void setup(@Optional("windows") String os,@Optional("chrome") String br) throws Exception {
        logger = (Logger) LogManager.getLogger(this.getClass());
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities=new DesiredCapabilities();

            //os
            if(os.equalsIgnoreCase("windows"))
            {
                capabilities.setPlatform(Platform.WIN11);
            }
            else if(os.equalsIgnoreCase("linux"))
            {
                capabilities.setPlatform(Platform.LINUX);

            }
            else if (os.equalsIgnoreCase("mac"))
            {
                capabilities.setPlatform(Platform.MAC);
            }
            else
            {
                System.out.println("No matching os");
                return;
            }

            //browser
            switch(br.toLowerCase())
            {
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                case "firefox": capabilities.setBrowserName("firefox"); break;
                default: System.out.println("No matching browser"); return;
            }

            driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        }


        if(p.getProperty("execution_env").equalsIgnoreCase("local"))
        {

            switch(br.toLowerCase())
            {
                case "chrome" : driver=new ChromeDriver(); break;
                case "edge" : driver=new EdgeDriver(); break;
                default : System.out.println("Invalid browser name.."); return;
            }
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