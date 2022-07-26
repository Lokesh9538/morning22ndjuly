package reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import driver.DriverScript;

public class ReportUtils extends DriverScript{
	/**************************************
	 * Method Name	: startExtentReport()
	 * Purpose		: To initialize the ExtentReports object with all the folder structure
	 * 
	 * 
	 **************************************/
	public ExtentReports startExtentReport(String fileName, String buildName)
	{
		String resultPath = null;
		File objResultPath = null;
		File objScreenshotPath = null;
		File objExtentFile = null;
		try {
			resultPath = System.getProperty("user.dir")+ "\\Results\\" + buildName;
			
			objResultPath = new File(resultPath);
			if(!objResultPath.exists()) {
				objResultPath.mkdirs();
			}
			
			screenshotLocation = objResultPath + "\\screenshots";
			objScreenshotPath = new File(screenshotLocation);
			if(!objScreenshotPath.exists()) {
				objScreenshotPath.mkdirs();
			}
			
			objExtentFile = new File(resultPath + "\\" + fileName + ".html");
			if(objExtentFile.exists()) {
				objExtentFile.delete();
			}
				
			extent = new ExtentReports(resultPath + "\\" + fileName + ".html");
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", appInd.getPropData("Environment"));
			extent.addSystemInfo("Host Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+ "\\extent-config.xml"));
			return extent;
		}catch(Exception e) {
			System.out.println("Exception in 'startExtentReport()' method. " + e);
			return null;
		}finally {
			resultPath = null;
			objResultPath = null;
			objScreenshotPath = null;
		}
	}
	
	
	
	
	/**************************************
	 * Method Name	: endExtentReport()
	 * Purpose		: To end the ExtentReports object
	 * 
	 * 
	 **************************************/
	public void endExtentReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
			//extent.close();
		}catch(Exception e) {
			System.out.println("Exception in 'endExtentReport()' method. " + e);
		}
	}
	
	
	
	
	/**************************************
	 * Method Name	: captureScreenshot()
	 * Purpose		: To capture the screenshots
	 * 
	 * 
	 **************************************/
	public String captureScreenshot(WebDriver oBrowser) {
		File objSource = null;
		String strDestination = null;
		File objDestination = null;
		try {
			strDestination = screenshotLocation + "\\" + "screenshot_" + appInd.getDateTime("ddMMyyyyhhmmss") + ".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			objDestination = new File(strDestination);
			FileHandler.copy(objSource, objDestination);
			return strDestination;
		}catch(Exception e) {
			System.out.println("Exception in 'captureScreenshot()' method. " + e);
			return null;
		}
		finally {
			objSource = null;
			strDestination = null;
			objDestination = null;
		}
	}
	
	
	
	/**************************************
	 * Method Name	: writeReport()
	 * Purpose		: To write the report
	 * 
	 * 
	 **************************************/
	public void writeReport(WebDriver oBrowser, String status, String message) {
		try {
			switch(status.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, message);
					break;
				case "fail":
					test.log(LogStatus.FAIL, message +" : "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "warning":
					test.log(LogStatus.WARNING, message);
					break;
				case "exception":
					test.log(LogStatus.FATAL, message +" : "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "info":
					test.log(LogStatus.INFO, message);
					break;
				case "screenshot":
					test.log(LogStatus.PASS, message+" : "+test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				default:
					System.out.println("Invalid status '"+status+"' for the report");
			}
		}catch(Exception e) {
			System.out.println("Exception in 'writeReport()' method. " + e);
		}
	}
	
}
