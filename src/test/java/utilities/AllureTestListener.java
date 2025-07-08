package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import testBase.BaseClass;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
//        Object testClass = result.getInstance();
        WebDriver driver = BaseClass.driver; // or ((BaseClass) testClass).getDriver();

        if (driver != null) {
            BaseClass.saveScreenshot(); // This will attach the screenshot to Allure
        }
    }

    @Override
    public void onTestStart(ITestResult result) {}
    @Override
    public void onTestSuccess(ITestResult result) {}
    @Override
    public void onTestSkipped(ITestResult result) {}
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {}
    @Override
    public void onStart(ITestContext context) {}
    @Override
    public void onFinish(ITestContext context) {}
}
