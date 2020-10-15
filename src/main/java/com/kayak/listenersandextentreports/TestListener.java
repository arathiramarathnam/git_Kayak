package com.kayak.listenersandextentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.kayak.listenersandextentreports.ExtentManager;
import com.kayak.utilities.CommonUtilities;
import com.kayak.utilities.TestBase;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends TestBase implements ITestListener  {

    //Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); 

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " started!"));
        ExtentManager.extLogger = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(ExtentManager.extLogger);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
       System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
        try {
			ExtentManager.extLogger.log(Status.PASS, "Passed"+ExtentManager.extLogger.addScreenCaptureFromPath(oBroUti.takeScreenShotWebReturnPath(TestBase.sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
        } catch (IOException e) {
		}
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        test.get().fail(result.getThrowable());
        try {
			ExtentManager.extLogger.log(Status.FAIL, "Failed"+ExtentManager.extLogger.addScreenCaptureFromPath(oBroUti.takeScreenShotWebReturnPath(TestBase.sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
        } catch (IOException e) {
		}
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable());
        try {
			
			ExtentManager.extLogger.log(Status.SKIP,"SKIP: "+ ExtentManager.extLogger.addScreenCaptureFromPath(oBroUti.takeScreenShotWebReturnPath(TestBase.sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
		} catch (IOException e) {
		}
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }
    
}