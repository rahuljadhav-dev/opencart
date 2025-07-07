package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Epic("User Management")
	@Feature("User Registration")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Registration Test")
	@Test(description = "Verify user registration with valid data",groups = {"sanity","master"})
	public void verifyAccountRegistration() {
		logger.info("***Test Case TC001_AccountRegistrationTest Started");
		HomePage homePage=new HomePage(driver);
		homePage.clickOnMyAccount();
		logger.info("Clicked On MyAccount");
		homePage.clickOnRegister();
		logger.info("Clicked On Register");
		
		AccountRegistrationPage page=new AccountRegistrationPage(driver);
		page.setFirstName(firstName);
		page.setLastName(lastName);
		page.setEmail(email);
		page.setTelephone(phone);
		page.setPassword(password);
		page.setConfirmPassword(password);
		logger.info("Insert All The Fields");
		
		
		page.clickOnSubscribe();
		page.clickOnAgree();
		page.clickOnContinue();
		logger.info("Clicked On Buttons");
		
		String msg=page.getConfirmationMsg();
		
		if(msg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
			logger.info("Test Passed...");
		}else {
			logger.error("Failed...");
			logger.debug("Test Failed");
			Assert.assertTrue(false);
		}
		logger.info("***Test Case TC001_AccountRegistrationTest Ended***");
//		Assert.assertEquals(msg, "Your Account Has Been Created!");
		
	}

}
