package leaver.testcases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import leaver.datamodels.ProjectVariable;
import leaver.models.manual.IdentityRequestModel;
import leaver.models.manual.WorkflowModel;
import leaver.util.Initializer;
import leaver.var.Constant;

import sailpoint.api.SailPointFactory;
import sailpoint.object.Identity;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;

public class TVR11_ResetPasswordForIdentityTest extends Initializer{

	private String workflowName;
	private String passwordBefore;
	private String passwordAfter;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("TriumvirLeaver3.0") String workflowName){
		this.workflowName = workflowName;
	}
	
	public String getIdentityPassword() throws GeneralException, InterruptedException{
		//Get the context for the Identity which password will be reset
		SailPointFactory.releaseContext(context);
		context = SailPointFactory.createContext();
		context.setUserName("Bob Fields");
		
		//Get the password of the Identity
		Identity identity = context.getObjectByName(Identity.class, "Bob Fields");
		String password = identity.getPassword();
		
		//Release the context to go as the spadmin
		SailPointFactory.releaseContext(context);
		context = SailPointFactory.createContext();
		context.setUserName("spadmin");
		
		return password;
	}
	
	@Test
	public void resetPassword() throws GeneralException, InterruptedException{
		// Get the password before the reset
		passwordBefore = this.getIdentityPassword();
		
		// Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();
		
		// here we will give the data to the password reset
		launchVariables.put(ProjectVariable.RESET_PASSWORD.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "Bob Fields");
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName, "System", Constant.PROJECT_VARS, launchVariables, null);
		
		//Checking if the operation needs approvals and approving if needed
		String launchStatus = launch.getStatus().toString();
		if (launchStatus == WorkflowLaunch.STATUS_APPROVING){
			IdentityRequestModel requestModel = new IdentityRequestModel(context);
			WorkItem workItem = requestModel.getWorkItem(launch);
			requestModel.approveRequest(workItem);
		}	
		
		// Get the password after the reset
		passwordAfter = this.getIdentityPassword();
		
		// Compare the passwords, if they are the same it means the password wasnt' reset, if not, the password was reset 
		if (passwordBefore.equals(passwordAfter)){
			Assert.assertTrue(false);
		} else {
			Assert.assertTrue(true);
		}
	}
}
