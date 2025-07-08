package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.qameta.allure.Attachment;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import testBase.BaseClass;

public class ExtentReportUtility implements ITestListener{
	ExtentSparkReporter extentSparkReporter;
	ExtentReports extentReports;
	ExtentTest extentTest;

	String reportName;
	@Override
	public void onStart(ITestContext context) {
		//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		//		Date date=new Date();
		//		name=dateFormat.format(date);

		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName="Test-Report"+timestamp+".html";
		extentSparkReporter=new ExtentSparkReporter(".\\reports\\"+reportName);

		extentSparkReporter.config().setDocumentTitle("OpenCart Automation Report");
		extentSparkReporter.config().setReportName("OpenCart Functional Testing");
		extentSparkReporter.config().setTheme(Theme.DARK);


		extentReports=new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		extentReports.setSystemInfo("Application", "OpenCart");
		extentReports.setSystemInfo("Module", "Admin");
		extentReports.setSystemInfo("Sub-Module", "Customers");
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReports.setSystemInfo("Enviornment", "QA");

		String os=context.getCurrentXmlTest().getParameter("os");
		extentReports.setSystemInfo("Operating System", os);

		String browser=context.getCurrentXmlTest().getParameter("browser");
		extentReports.setSystemInfo("Browser", browser);

		List<String>groups=context.getCurrentXmlTest().getIncludedGroups();
		if(!groups.isEmpty()) {
			extentReports.setSystemInfo("Groups", groups.toString());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest=extentReports.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());//to display groups in reports

		extentTest.log(Status.PASS,result.getName()+ " Got Successfully Executed...");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest = extentReports.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());

		extentTest.log(Status.FAIL, result.getName() + " Got Failed...");
		extentTest.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String ssPath = BaseClass.captureScreen(result.getName());
			extentTest.addScreenCaptureFromPath(ssPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest=extentReports.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());//to display groups in reports

		extentTest.log(Status.SKIP,result.getName()+ " Got Skipped...");
		extentTest.log(Status.INFO,result.getThrowable().getMessage());
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();

		// Path to the Extent Report
		String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File report = new File(reportPath);

		// Open the report in browser
		try {
			Desktop.getDesktop().browse(report.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send report via email
		//		try {
		//			final String senderEmail = "rj101688@gmail.com"; // your email
		//			final String appPassword = "";     // your app password
		//			final String recipientEmail = "rahuljadhav.dev@gmail.com"; // receiver email
		//
		//			Properties props = new Properties();
		//			props.put("mail.smtp.host", "smtp.gmail.com");
		//			props.put("mail.smtp.port", "587");
		//			props.put("mail.smtp.auth", "true");
		//			props.put("mail.smtp.starttls.enable", "true");
		//
		//			Session session = Session.getInstance(props, new Authenticator() {
		//				protected PasswordAuthentication getPasswordAuthentication() {
		//					return new PasswordAuthentication(senderEmail, appPassword);
		//				}
		//			});
		//
		//			Message message = new MimeMessage(session);
		//			message.setFrom(new InternetAddress(senderEmail));
		//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		//			message.setSubject("Automation Test Report - Extent");
		//
		//			// Email Body
		//			BodyPart messageBodyPart = new MimeBodyPart();
		//			messageBodyPart.setContent("<h3>Hello,</h3><p>Test Execution Completed. Please find the attached Extent Report.</p>", "text/html");
		//
		//			// Attachment
		//			MimeBodyPart attachmentPart = new MimeBodyPart();
		//			attachmentPart.attachFile(report);
		//
		//			// Combine
		//			Multipart multipart = new MimeMultipart();
		//			multipart.addBodyPart(messageBodyPart);
		//			multipart.addBodyPart(attachmentPart);
		//			message.setContent(multipart);
		//
		//			// Send mail
		//			Transport.send(message);
		//			System.out.println("Test report emailed successfully!");
		//
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}


}
