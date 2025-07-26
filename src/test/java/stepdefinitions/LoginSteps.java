package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class LoginSteps extends BaseClass{
	HomePage home;
	LoginPage login;
	MyAccountPage account;

	@Given("User launches the application")
	public void user_launches_the_application() {
		String os = "windows";   // You can also use System.getProperty("os.name") if needed
		String browser = "chrome"; // or firefox/edge

		launchApplication(os, browser); 
	}

	@When("User navigates to Login page")
	public void user_navigates_to_login_page() {
		home = new HomePage(driver);
		home.clickOnMyAccount();
		logger.info("Clicked MyAccount");
		home.clickLogin();
		logger.info("Clicked Login");
	}

	@When("User enters valid email and password")
	public void user_enters_valid_email_and_password() {
		login = new LoginPage(driver);
		login.setEmail(properties.getProperty("email"));
		login.setPassword(properties.getProperty("password"));
		logger.info("Entered email and password");
	}

	@When("User clicks the login button")
	public void user_clicks_the_login_button() {
		login.clickOnLogin();
		logger.info("Clicked Login button");
	}

	@Then("User should be navigated to My Account page")
	public void user_should_be_navigated_to_my_account_page() {
		MyAccountPage account = new MyAccountPage(driver);
		Assert.assertTrue(account.status());

		driver.quit(); // Optional clean-up
	}

}
