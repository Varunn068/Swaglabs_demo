package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.loginpage;
import testBase.Baseclass;
import utilities.ExcelUtility;

import java.io.IOException;

public class TC02_LoginTest extends Baseclass {

    @Test(dataProvider = "loginData",groups ="Sanity")
    public void testLogin(String username, String password, String expectedResult)throws Exception {
              logger.info("TC02-logintest");
        try
        {
           loginpage Loginpage = new loginpage(driver);
           Loginpage.performLogin(username,password);
           Thread.sleep(5000);

            if (expectedResult.equalsIgnoreCase("success")) {
                // Verify successful login
                Assert.assertTrue(Loginpage.isLoginSuccessful(),
                        "Login should be successful for user: " + username);

                System.out.println("✓ Login successful for user: " + username);
                logger.info("login");
                // Logout after successful login
                Loginpage.logout();
                Thread.sleep(1000);

            } else if (expectedResult.equalsIgnoreCase("failure")) {
                // Verify login failure
                Assert.assertTrue(Loginpage.isErrorMessageDisplayed(),
                        "Error message should be displayed for user: " + username);

                String errorMsg = Loginpage.getErrorMessage();
                System.out.println("✓ Login failed as expected for user: " + username +
                        " - Error: " + errorMsg);
                captureScreen(username);
                logger.info("failed");
            }


        }
        catch (Exception e)
        {
            try {
                captureScreen("LoginTest");
            } catch (IOException ioException) {
                System.out.println("Failed to capture screenshot: " + ioException.getMessage());

            }

            Assert.fail("Test failed for user: " + username + " - " + e.getMessage());
        }
        }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        try {
            String excelPath = "C:\\Varun_Folder\\OpencartV1\\Testdata\\logindetails.xlsx";
            ExcelUtility excelReader = new ExcelUtility(excelPath, "Sheet1");
            Object[][] data = excelReader.getTestDataArray();
            excelReader.closeWorkbook();
            return data;
        } catch (IOException e) {
            System.out.println("Error reading Excel file: " + e.getMessage());
            // Return default test data if Excel file is not found
            return getDefaultTestData();
        }
    }

    private Object[][] getDefaultTestData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", "success"},
                {"locked_out_user", "secret_sauce", "failure"},
                {"problem_user", "secret_sauce", "success"},
                {"performance_glitch_user", "secret_sauce", "success"},
                {"invalid_user", "secret_sauce", "failure"},
                {"standard_user", "wrong_password", "failure"}
        };
    }

    }
