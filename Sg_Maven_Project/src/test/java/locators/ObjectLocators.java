package locators;

import org.openqa.selenium.By;

public interface ObjectLocators {
	public static By obj_UserName_Edit = By.xpath("//input[@id='username']");
	public static By obj_Password_Edit = By.xpath("//input[@name='pwd']");
	public static By obj_Login_Btn = By.xpath("//a[@id='loginButton']");
	public static By obj_Login_HeaderText = By.id("headerContainer");
	public static By obj_TimetrackPage_Header = By.xpath("//td[@class='pagetitle']");
	public static By obj_Users_Menu = By.xpath("//div[text()='USERS']");
	public static By obj_AddUsers_Btn = By.xpath("//div[text()='Add User']");
	public static By obj_User_FirstName_Edit = By.xpath("//input[@name='firstName']");
	public static By obj_User_LastName_Edit = By.xpath("//input[@name='lastName']");
	public static By obj_User_Email_Edit = By.xpath("//input[@name='email']");
	public static By obj_User_UserName_Edit = By.xpath("//input[@name='username']");
	public static By obj_User_Password_Edit = By.xpath("//input[@name='password']");
	public static By obj_User_RetypePassword_Edit = By.xpath("//input[@name='passwordCopy']");
	public static By obj_CreateUser_Btn = By.xpath("//span[text()='Create User']");
	public static By obj_DeleteUser_Btn = By.xpath("//button[@id='userDataLightBox_deleteBtn']");
	public static By obj_StartExploringActiTime_Btn = By.xpath("//span[text()='Start exploring actiTIME']");
	public static By obj_Logout_Btn = By.xpath("//a[@id='logoutLink']");
	
	
	
}
