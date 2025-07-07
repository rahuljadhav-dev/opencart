package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement firstName;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement lastName;
	@FindBy(xpath="//input[@id='input-email']") WebElement email;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement telephone;
	@FindBy(xpath="//input[@id='input-password']") WebElement password;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement passwordConfirm;
	@FindBy(xpath="//label[normalize-space()='Yes']") WebElement newsletter;
	@FindBy(xpath="//input[@name='agree']") WebElement agree;
	@FindBy(xpath="//input[@value='Continue']") WebElement continue_button;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement cnfMsg;
	
	public void setFirstName(String firstName) {
		this.firstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		this.email.sendKeys(email);
	}
	
	public void setTelephone(String number) {
		telephone.sendKeys(number);
	}

	public void setPassword(String password) {
		this.password.sendKeys(password);
	}
	
	public void setConfirmPassword(String confirmPassword) {
		passwordConfirm.sendKeys(confirmPassword);
	}

	public void clickOnSubscribe() {
		newsletter.click();
	}

	public void clickOnAgree() {
		agree.click();
	}

	public void clickOnContinue() {
		continue_button.click();
	}
	
	public String getConfirmationMsg() {
		try {
			return cnfMsg.getText();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
