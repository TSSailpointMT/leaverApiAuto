package leaver.testcases;

import java.util.HashMap;
import java.util.List;

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

import sailpoint.object.Identity;
import sailpoint.object.Link;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;

public class TVR7_DeleteIdentityAccountsTest extends Initializer{
	private String workflowName;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("TriumvirLeaver3.0") String workflowName){
		this.workflowName = workflowName;
	}
	
	@Test
	public void deleteIdentity() throws GeneralException, InterruptedException{
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();

		// here we will give the data related to the Identity we will delete the accounts from
		launchVariables.put(ProjectVariable.DELETE_ACCOUNT.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "whenderson");
		
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
		
		//Verifying that the changes were made
		Identity testIdentity = context.getObjectByName(Identity.class, "whenderson");
		List<Link> links = testIdentity.getLinks();
		// if the links are empty, it means that the accounts of the identity were deleted, if not it means the account weren't deleted
		if (links.isEmpty()){
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
}
