package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class MyAccountPage extends BasePage{

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement myAccount;
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement logout;

	public boolean status() {
		if(myAccount.isDisplayed()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void clickLogout() {
		logout.click();
	}

}
