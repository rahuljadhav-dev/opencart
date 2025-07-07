package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
/*
 *for every page object class we need constructor so thats why we declare it once in basepage and reuse in many.
 *reusable component for all poc.
 */
public class BasePage {
	WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

}
