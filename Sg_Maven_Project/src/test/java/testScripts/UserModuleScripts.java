package testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/*******************************************
	 * method Name		: TS_LoginLogout()
	 * Test Case #		: TS_101
	 * 
	 * 
	 *******************************************/
	public boolean TS_LoginLogout() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		try {
			test = extent.startTest("TS_LoginLogout");
			objData = datatable.getExcelData("UserModule", "InputData", "TS_101");
			oBrowser = appInd.launchBrowser(appInd.getPropData("Browser"));
			strStatus+= appDep.navigateURL(oBrowser, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password"));
			strStatus+= appDep.logoutFromApp(oBrowser);
			
			if(strStatus.contains("false")) {
				reports.writeReport(oBrowser, "Fail", "The test script 'TS_LoginLogout()' was failed");
				return false;
			}else {
				reports.writeReport(oBrowser, "Pass", "The test script 'TS_LoginLogout()' was passed");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'TS_LoginLogout()' method. " + e);
			return false;
		}finally {
			oBrowser.close();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	
	/*******************************************
	 * method Name		: TS_Create_DeleteUser()
	 * Test Case #		: TS_102
	 * 
	 * 
	 *******************************************/
	public boolean TS_Create_DeleteUser() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		try {
			test = extent.startTest("TS_Create_DeleteUser");
			objData = datatable.getExcelData("UserModule", "InputData", "TS_102");
			oBrowser = appInd.launchBrowser(appInd.getPropData("Browser"));
			strStatus+= appDep.navigateURL(oBrowser, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password"));
			String userName = userMethods.createUser(oBrowser, objData);
			strStatus+= userMethods.deleteUser(oBrowser, userName);
			strStatus+= appDep.logoutFromApp(oBrowser);
			
			if(strStatus.contains("false")) {
				reports.writeReport(null, "Fail", "The test script 'TS_Create_DeleteUser()' was failed");
				return false;
			}else {
				reports.writeReport(null, "Pass", "The test script 'TS_Create_DeleteUser()' was passed");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'TS_Create_DeleteUser()' method. " + e);
			return false;
		}finally {
			oBrowser.close();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	
	/*******************************************
	 * method Name		: TS_Create_Login_DeleteUser()
	 * Test Case #		: TS_103
	 * 
	 * 
	 *******************************************/
	public boolean TS_Create_Login_DeleteUser() {
		WebDriver oBrowser1 = null;
		WebDriver oBrowser2 = null;
		Map<String, String> objData = null;
		String strStatus = null;
		try {
			test = extent.startTest("TS_Create_Login_DeleteUser");
			objData = datatable.getExcelData("UserModule", "InputData", "TS_103_1");
			oBrowser1 = appInd.launchBrowser(appInd.getPropData("Browser"));
			strStatus+= appDep.navigateURL(oBrowser1, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser1, "Existing", objData.get("UserName"), objData.get("Password"));
			String userName = userMethods.createUser(oBrowser1, objData);
			
			//Login with newly created user
			objData = datatable.getExcelData("UserModule", "InputData", "TS_103_2");
			oBrowser2 = appInd.launchBrowser(appInd.getPropData("Browser"));
			strStatus+= appDep.navigateURL(oBrowser2, appInd.getPropData("URL"));
			boolean blnRes = appDep.loginToApp(oBrowser2, "New", objData.get("UserName"), objData.get("Password"));
			if(blnRes) {
				reports.writeReport(oBrowser2, "Pass", "Able to login with newly created user '"+objData.get("UserName")+"'");
			}else {
				reports.writeReport(oBrowser2, "Fail", "Login with newly created user '"+objData.get("UserName")+"' was failed");
				return false;
			}
			oBrowser2.close();
			oBrowser2 = null;
			
			strStatus+= userMethods.deleteUser(oBrowser1, userName);
			strStatus+= appDep.logoutFromApp(oBrowser1);
			
			if(strStatus.contains("false")) {
				reports.writeReport(null, "Fail", "The test script 'TS_Create_Login_DeleteUser()' was failed");
				return false;
			}else {
				reports.writeReport(null, "Pass", "The test script 'TS_Create_Login_DeleteUser()' was passed");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser1, "Exception", "Exception in 'TS_Create_Login_DeleteUser()' method. " + e);
			return false;
		}finally {
			oBrowser1.close();
			oBrowser1 = null;
			reports.endExtentReport(test);
		}
	}
}
