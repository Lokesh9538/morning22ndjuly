package methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;
import locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/*******************************************
	 * method Name		: createUser()
	 * 
	 * 
	 *******************************************/
	public String createUser(WebDriver oBrowser, Map<String, String> objData) {
		String userName = null;
		try {
			appInd.clickObject(oBrowser, obj_Users_Menu);
			appInd.waitForElement(oBrowser, obj_AddUsers_Btn, "Clickable", "", waitTimeout);
			appInd.clickObject(oBrowser, obj_AddUsers_Btn);
			appInd.waitForElement(oBrowser, obj_CreateUser_Btn, "Clickable", "", waitTimeout);
			
			String FN = objData.get("FirstName");
			String LN = objData.get("LastName");
			
			appInd.setObject(oBrowser, obj_User_FirstName_Edit, FN);
			appInd.setObject(oBrowser, obj_User_LastName_Edit, LN);
			appInd.setObject(oBrowser, obj_User_Email_Edit, objData.get("Email"));
			appInd.setObject(oBrowser, obj_User_UserName_Edit, objData.get("User_UserName"));
			appInd.setObject(oBrowser, obj_User_Password_Edit, objData.get("User_Password"));
			appInd.setObject(oBrowser, obj_User_RetypePassword_Edit, objData.get("User_RetypePWd"));
			reports.writeReport(oBrowser, "Screenshot", "The new user details are entered successful");
			appInd.clickObject(oBrowser, obj_CreateUser_Btn);
			userName = LN+", "+FN;
			appInd.waitForElement(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"), "Text", userName, waitTimeout);
			
			if(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"))) {
				reports.writeReport(oBrowser, "Pass", "User '"+userName+"' created successful");
				reports.writeReport(oBrowser, "Screenshot", "The new user '"+userName+"' was created successful");
				return userName;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to create the user '"+userName+"'.");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'createUser()' method. " + e);
			return null;
		}
	}
	
	
	
	/*******************************************
	 * method Name		: deleteUser()
	 * 
	 * 
	 *******************************************/
	public boolean deleteUser(WebDriver oBrowser, String userName) {
		try {
			appInd.clickObject(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"));
			appInd.waitForElement(oBrowser, obj_DeleteUser_Btn, "Clickable", "", waitTimeout);
			Thread.sleep(2000);
			appInd.clickObject(oBrowser, obj_DeleteUser_Btn);
			Thread.sleep(2000);
			oBrowser.switchTo().alert().accept();
			Thread.sleep(2000);
			
			if(appInd.verifyElementNotPresent(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"))) {
				reports.writeReport(oBrowser, "Pass", "The user '"+userName+"' was deleted successful");
				reports.writeReport(oBrowser, "Screenshot", "The user '"+userName+"' was deleted successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Failed to delete the user '"+userName+"'");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'deleteUser()' method. " + e);
			return false;
		}
	}
}
