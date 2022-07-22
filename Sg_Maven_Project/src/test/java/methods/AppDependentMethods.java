package methods;

import org.openqa.selenium.WebDriver;
import driver.DriverScript;
import locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/*******************************************
	 * method Name		: navigateURL()
	 * 
	 * 
	 *******************************************/
	public boolean navigateURL(WebDriver oBrowser, String URL) {
		try {
			oBrowser.navigate().to(URL);
			appInd.waitForElement(oBrowser, obj_Login_Btn, "Clickable", "", waitTimeout);
			reports.writeReport(oBrowser, "Screenshot", "The URL loaded successful");
			return appInd.compareValue(oBrowser, oBrowser.getTitle(), "actiTIME - Login");
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'navigateURL()' method. " + e);
			return false;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: loginToApp()
	 * 
	 * 
	 *******************************************/
	public boolean loginToApp(WebDriver oBrowser, String userType, String userName, String password) {
		boolean blnRes = false;
		try {
			appInd.setObject(oBrowser, obj_UserName_Edit, userName);
			appInd.setObject(oBrowser, obj_Password_Edit, password);
			appInd.clickObject(oBrowser, obj_Login_Btn);
			
			if(userType.equalsIgnoreCase("New")) {
				appInd.waitForElement(oBrowser, obj_StartExploringActiTime_Btn, "Clickable", "", waitTimeout);
				appInd.clickObject(oBrowser, obj_StartExploringActiTime_Btn);
			}
			appInd.waitForElement(oBrowser, obj_TimetrackPage_Header, "Text", "Enter Time-Track", waitTimeout);
			blnRes = appInd.verifyText(oBrowser, obj_TimetrackPage_Header, "Text", "Enter Time-Track");
			if(blnRes) {
				reports.writeReport(oBrowser, "Pass", "Login to actiTime was successful");
				reports.writeReport(oBrowser, "Screenshot", "The login was successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to login to actiTime application");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'loginToApp()' method. " + e);
			return false;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: logoutFromApp()
	 * 
	 * 
	 *******************************************/
	public boolean logoutFromApp(WebDriver oBrowser) {
		boolean blnRes = false;
		try {
			appInd.clickObject(oBrowser, obj_Logout_Btn);
			appInd.waitForElement(oBrowser, obj_Login_HeaderText, "Text", "Please identify yourself", waitTimeout);
			blnRes = appInd.verifyText(oBrowser, obj_Login_HeaderText, "Text", "Please identify yourself");
			if(blnRes) {
				reports.writeReport(oBrowser, "Pass", "Logout from the actiTime was successful");
				reports.writeReport(oBrowser, "Screenshot", "The logout was successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to Logout from the actiTime application");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'logoutFromApp()' method. " + e);
			return false;
		}
	}
}
