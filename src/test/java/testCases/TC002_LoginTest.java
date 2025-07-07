package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	@Epic("User Management")
	@Feature("User Registration")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Registration Test")
	@Test(groups = {"regression","master"})
	public void loginTest() {
		try {
			logger.info("***TestCase TC002_LoginTest Started***");
			HomePage homePage=new HomePage(driver);
			homePage.clickOnMyAccount();
			logger.info("Click On MyAccount");
			homePage.clickLogin();
			logger.info("Click On Login Button");
			
			LoginPage loginPage=new LoginPage(driver);
			loginPage.setEmail(properties.getProperty("email"));
			loginPage.setPassword(properties.getProperty("password"));
			logger.info("Enter Email And Password");
			loginPage.clickOnLogin();
			logger.info("Press Login");
			MyAccountPage accountPage=new MyAccountPage(driver);
			boolean status=accountPage.status();
			if(status==true) {
				Assert.assertTrue(status);
			}else {
				Assert.fail();
			}
			
		} catch (Exception e) {
			logger.debug("Test Failed...");
			Assert.fail();
		}
		logger.info("******TestCase TC002_LoginTest Ended******");
	}

}
