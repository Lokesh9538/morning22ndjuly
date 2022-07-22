package methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/*******************************************
	 * method Name		: getDateTime()
	 * 
	 * 
	 *******************************************/
	public String getDateTime(String dateFormat) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(dt);
		}catch(Exception e) {
			System.out.println("Exception in 'getDateTime()' method. " + e);
			return null;
		}finally {
			dt = null;
			sdf = null;
		}
	}
	
	
	/*******************************************
	 * method Name		: clickObject()
	 * 
	 * 
	 *******************************************/
	public boolean clickObject(WebDriver oBrowser, By objBy) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				objEles.get(0).click();
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'clickObject()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	
	/*******************************************
	 * method Name		: setObject()
	 * 
	 * 
	 *******************************************/
	public boolean setObject(WebDriver oBrowser, By objBy, String strValue) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				objEles.get(0).sendKeys(strValue);
				reports.writeReport(oBrowser, "Pass", "The value '"+strValue+"' was entered in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'setObject()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: clearAndSetObject()
	 * 
	 * 
	 *******************************************/
	public boolean clearAndSetObject(WebDriver oBrowser, By objBy, String strValue) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				objEles.get(0).clear();
				objEles.get(0).sendKeys(strValue);
				reports.writeReport(oBrowser, "Pass", "The value '"+strValue+"' was entered in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'clearAndSetObject()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	
	/*******************************************
	 * method Name		: compareValue()
	 * 
	 * 
	 *******************************************/
	public boolean compareValue(WebDriver oBrowser, String actual, String expected) {
		try {
			if(actual.equalsIgnoreCase(expected)) {
				reports.writeReport(oBrowser, "Pass", "Both actual '"+actual+"' & expected '"+expected+"' values are matching");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "mis-match in both actual '"+actual+"' & expected '"+expected+"' values");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'compareValue()' method. " + e);
			return false;
		}
	}
	
	
	
	
	/*******************************************
	 * method Name		: verifyText()
	 * 
	 * 
	 *******************************************/
	public boolean verifyText(WebDriver oBrowser, By objBy, String strObjectType, String expected) {
		List<WebElement> objEles = null;
		String actual = null;
		Select oSelect = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				switch(strObjectType.toLowerCase()) {
					case "text":
						actual = objEles.get(0).getText();
						break;
					case "value":
						actual = objEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSelect = new Select(objEles.get(0));
						actual = oSelect.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeReport(oBrowser, "Fail", "Invalid object type '"+strObjectType+"'");
						return false;
				}
				
				if(compareValue(oBrowser, actual, expected)) return true;
				else return false;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyText()' method. " + e);
			return false;
		}finally {
			objEles = null;
			oSelect = null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: verifyElementPresent()
	 * 
	 * 
	 *******************************************/
	public boolean verifyElementPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was present in the DOM");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyElementPresent()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: verifyElementNotPresent()
	 * 
	 * 
	 *******************************************/
	public boolean verifyElementNotPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was still present in the DOM");
				return false;
			}else {
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was not found in the DOM successful");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyElementNotPresent()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: verifyOptionalElement()
	 * 
	 * 
	 *******************************************/
	public boolean verifyOptionalElement(WebDriver oBrowser, By objBy) {
		List<WebElement> objEles = null;
		try {
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyOptionalElement()' method. " + e);
			return false;
		}finally {
			objEles = null;
		}
	}
	
	
	
	
	/*******************************************
	 * method Name		: waitForElement()
	 * 
	 * 
	 *******************************************/
	public boolean waitForElement(WebDriver oBrowser, By objBy, String waitCondition, String text, long waitTimeout) {
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, waitTimeout);
			switch(waitCondition.toLowerCase()) {
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
					break;
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "presence":
					oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, text));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, text));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				default:
					System.out.println("Invalid wait condition '"+waitCondition+"'");
			}
			return true;
		}catch(Exception e) {
			System.out.println("Exception in 'waitForElement()' method. " + e);
			return false;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: launchBrowser()
	 * 
	 * 
	 *******************************************/
	public WebDriver launchBrowser(String browserName) {
		WebDriver oBrowser = null;
		try {
			switch(browserName.toLowerCase()) {
				case "chrome":
					System.setProperty("webdriver.chrome.driver", "E:\\JavaWorkSpace\\MavenDemoWorkSpace\\Sg_Maven_Project\\Library\\driver\\chromedriver.exe");
					oBrowser = new ChromeDriver();
					break;
				case "edge":
					System.setProperty("webdriver.edge.driver", "E:\\JavaWorkSpace\\MavenDemoWorkSpace\\Sg_Maven_Project\\Library\\driver\\msedgedriver.exe");
					oBrowser = new EdgeDriver();
					break;
				default:
					reports.writeReport(oBrowser, "Fail", "Invalid browser name '"+browserName+"' specified. Please specified the valid browser name");
			}
			
			if(oBrowser != null) {
				oBrowser.manage().window().maximize();
				reports.writeReport(oBrowser, "Pass", "The '"+browserName+"' browser has launched successful");
				return oBrowser;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to launch the '"+browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'launchBrowser()' method. " + e);
			return null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: getPropData()
	 * 
	 * 
	 *******************************************/
	public String getPropData(String keyName) {
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\Configuration\\masterData.properties");
			prop = new Properties();
			prop.load(fin);
			return prop.getProperty(keyName);
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in 'getPropData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				prop = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception in 'getPropData()' method. " + e);
				return null;
			}
		}
	}
}
