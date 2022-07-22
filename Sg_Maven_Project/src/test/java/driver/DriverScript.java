package driver;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;
import methods.TaskModuleMethods;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static Datatable datatable = null;
	public static ReportUtils reports = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String screenshotLocation = null;
	public static long waitTimeout = 120;
	public static String controller = null;
	
	
	
	@BeforeSuite
	public void loadClassFiles() {			
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			userMethods = new UserModuleMethods();
			taskMethods = new TaskModuleMethods();
			datatable = new Datatable();
			reports = new ReportUtils();
			extent = reports.startExtentReport("TestReports", appInd.getPropData("buildName"));
			controller = System.getProperty("user.dir") + "\\ExecutionController\\Controller.xlsx";
		}catch(Exception e) {
			System.out.println("Exception in 'loadClassFiles()' method. " + e);
		}
	}
	
	
	@Test
	public void executeTestSuite() {
		String executeTest = null;
		String moduleName = null;
		Class cls = null;
		Object obj = null;
		Method test = null;
		int flag = 0;
		try {
			int mRows = datatable.getRowNumber(controller, "Modules");
			for(int i=0; i<mRows; i++) {
				String executeModule = datatable.getCellData(controller, "Modules", "ExecuteModule", i+1);
				if(executeModule.equalsIgnoreCase("Yes")) {
					moduleName = datatable.getCellData(controller, "Modules", "ModuleName", i+1);
					
					int tcRows = datatable.getRowNumber(controller, moduleName);
					
					for(int j=0; j<tcRows; j++) {
						executeTest = datatable.getCellData(controller, moduleName, "ExecuteTest", j+1);
						if(executeTest.equalsIgnoreCase("Yes")) {
							flag = 1;
							String scriptName = datatable.getCellData(controller, moduleName, "ScriptName", j+1);
							String className = datatable.getCellData(controller, moduleName, "ClassName", j+1);
							cls = Class.forName(className);
							obj = cls.newInstance();
							test = obj.getClass().getMethod(scriptName);
							String status = String.valueOf(test.invoke(obj));
							if(status.equalsIgnoreCase("True")) {
								System.out.println("The script '"+scriptName+"' was Passed");
							}else {
								System.out.println("The script '"+scriptName+"' was Failed");
							}
						}
					}
					
					if(flag==0) {
						System.out.println("Please select atleast one test script to execute from '"+moduleName+"' module");
					}
				}
			}
		}catch(Exception e) {
			System.out.println("Exception in 'executeTestSuite()' method. " + e);
		}finally {
			executeTest = null;
			moduleName = null;
			cls = null;
			obj = null;
			test = null;
		}
	}
}
