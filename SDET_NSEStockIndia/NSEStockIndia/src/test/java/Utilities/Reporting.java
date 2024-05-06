package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nifty50.testscases.BaseClass;

import freemarker.template.utility.CaptureOutput;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting extends BaseClass implements ITestListener{
	public ExtentHtmlReporter htmlReport;
	public ExtentReports xReport;
	public ExtentTest xTest;
	
	public void onStart(ITestContext testContext) {
		String dateStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "VerifyStockInformation"+ dateStamp+".html";
		htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"//reports//"+repName);
		htmlReport.config().setDocumentTitle("TestNG Program");
		htmlReport.config().setTheme(Theme.DARK);
		htmlReport.config().setAutoCreateRelativePathMedia(true);
		xReport=new ExtentReports();
		xReport.attachReporter(htmlReport);
		xReport.setSystemInfo("Tester Name :", "Prakash");
	}
	
	public void onFinish(ITestContext testContext) {
		xReport.flush();
	}
	public void onTestSuccess(ITestResult Tr) {
		xTest = xReport.createTest(Tr.getName());
		xTest.log(Status.PASS, "Test is passed");
		xTest.log(Status.PASS, MarkupHelper.createLabel(Tr.getName(), ExtentColor.GREEN));
		String path = null;
		try {
			driver = (WebDriver) Tr.getTestClass().getRealClass().getField("driver").get(Tr.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			path = captureScreenshot(driver,Tr.getName(),"PASSED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			xTest.addScreenCaptureFromPath(path, Tr.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onTestFailure(ITestResult Tr) {
		xTest = xReport.createTest(Tr.getName());
		xTest.log(Status.FAIL, "Test is failed");
		xTest.log(Status.FAIL, Tr.getThrowable());
		xTest.log(Status.FAIL, MarkupHelper.createLabel(Tr.getName(), ExtentColor.RED));
		String path = null;
		try {
			driver = (WebDriver) Tr.getTestClass().getRealClass().getField("driver").get(Tr.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			path = captureScreenshot(driver,Tr.getName(),"FAILED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			xTest.addScreenCaptureFromPath(path, Tr.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onTestSkip(ITestResult Tr) {
		xTest = xReport.createTest(Tr.getName());
		xTest.log(Status.SKIP, "Test is skipped");
		xTest.log(Status.SKIP, MarkupHelper.createLabel(Tr.getName(), ExtentColor.AMBER));
	}
}
