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
import utilities.LoginDataDrivenTest;

public class TC003_DataDrivenTest extends BaseClass{
	@Epic("User Management")
	@Feature("User Registration")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Registration Test")
	@Test(dataProvider = "loginData",dataProviderClass =LoginDataDrivenTest.class,groups = {"data driven","master"})
	public void dataDrivenTest(String email,String password,String res) {
		try {
			logger.info("***TestCase TC003_DataDrivenTest Started***");
			HomePage homePage=new HomePage(driver);

			homePage.clickOnMyAccount();
			logger.info("Click On MyAccount");
			homePage.clickLogin();
			logger.info("Click On Login Button");

			LoginPage loginPage=new LoginPage(driver);
			loginPage.setEmail(email);
			loginPage.setPassword(password);
			logger.info("Enter email and password :"+email+"/"+password);
			loginPage.clickOnLogin();
			logger.info("Click Login Button");

			MyAccountPage accountPage=new MyAccountPage(driver);
			boolean flag=accountPage.status();

			if(res.equalsIgnoreCase("Valid")) {
				logger.info("With Valid Credentials");
				if(flag==true) {
					accountPage.clickLogout();
					logger.info("Click Login Button");
					Assert.assertTrue(true);
				}else {
					Assert.assertTrue(false);
				}
			}

			if(res.equalsIgnoreCase("Invalid")) {
				logger.info("Without Valid Credentials");
				if(flag==true) {
					accountPage.clickLogout();
					logger.info("Click Logout Button");
					Assert.assertTrue(false);
				}else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}	
		logger.info("***TestCase TC003_DataDrivenTest Ended***");
	}
}
