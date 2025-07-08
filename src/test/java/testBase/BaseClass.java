package testBase;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import io.qameta.allure.Attachment;
import utilities.ExcelUtils;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;//log4j
	public Properties properties;
	public FileInputStream file;

	protected String firstName = BaseClass.randomString().toUpperCase();
	protected String lastName = BaseClass.randomString().toUpperCase();
	protected String email = BaseClass.randomString().toLowerCase() + "@gmail.com";
	protected String phone = BaseClass.randomNumber();
	protected String password = BaseClass.randomAlphaNumeric();

	@BeforeClass(groups = {"sanity","regression","master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		file=new FileInputStream("./src//test//resources//config.properties");
		properties=new Properties();
		properties.load(file);

		logger=LogManager.getLogger(this.getClass());
		logger.info("Running test on: " + br + " on OS: " + os);

		if(properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities=new DesiredCapabilities();

			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);

			}else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("Not a valid platform");
				return;
			}

			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;

			case "edge":
				capabilities.setBrowserName(Browser.EDGE.toString());
				break;
				
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;

			default:
				System.out.println("No valid browser found");
				return;
			}

			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}

		if(properties.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":
				driver=new ChromeDriver();
				break;

			case "edge":
				driver=new EdgeDriver();
				break;

			case "firefox":
				driver=new FirefoxDriver();
				break;

			default:
				System.out.println("Invalid Browser Name...");
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		driver.get(properties.getProperty("appUrl"));
		driver.manage().window().maximize();
	}

	@AfterClass(groups = {"sanity","regression","master"})
	public void tearDown() throws IOException, InterruptedException {
		//				String path=System.getProperty("user.dir")+"/testData/registeredAccounts.xlsx";
		//				ExcelUtils utils=new ExcelUtils(path);
		//				int rowCount;
		//				try {
		//					utils.setCellData("sheet4", 0, 0, "Email");
		//					utils.setCellData("sheet4", 0, 1, "Password");
		//					utils.setCellData("sheet4", 0, 2, "Res");
		//					utils.makeHeaderBold("sheet4");
		//					
		//					rowCount = utils.getTotalRows("sheet4")+1;
		//					utils.setCellData("sheet4", rowCount, 0, email);
		//					utils.setCellData("sheet4", rowCount, 1, password);
		//					utils.makeHeaderBold("sheet4");
		//				} catch (IOException e) {
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//				}
		driver.quit();
	}

	// Generate random string of 6 characters (alphabets only)
	public static String randomString() {
		return RandomStringUtils.secure().nextAlphabetic(5);
	}

	// Generate random 10-digit number (string format)
	public static String randomNumber() {
		return RandomStringUtils.secure().nextNumeric(10);	
	}

	// Generate random alphanumeric password of 8 characters
	public static String randomAlphaNumeric() {
		return RandomStringUtils.secure().nextAlphanumeric(8);
	}

	public static String captureScreen(String name) {
	    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    TakesScreenshot screenshot = (TakesScreenshot) driver;
	    File src = screenshot.getScreenshotAs(OutputType.FILE);

	    String relativePath = "screenshots/" + name + "_" + timestamp + ".png";
	    String fullPath = System.getProperty("user.dir") + "/" + relativePath;

	    try {
	        Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots")); // make dir if not exist
	        Files.copy(src.toPath(), Paths.get(fullPath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return relativePath; 
	}



	@Attachment(value = "Screenshot on Failure", type = "image/png")
	public static byte[] saveScreenshot() {
	    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
}

