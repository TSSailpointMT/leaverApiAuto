package leaver.testcases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import leaver.datamodels.ProjectVariable;
import leaver.models.manual.IdentityRequestModel;
import leaver.models.manual.ImportModel;
import leaver.models.manual.RuleModel;
import leaver.models.manual.WorkflowModel;
import leaver.util.Initializer;
import leaver.var.Constant;


import sailpoint.api.Differencer.IdentitySnapshotComparator;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.api.TaskManager;

import sailpoint.object.Identity;
import sailpoint.object.IdentitySnapshot;
import sailpoint.object.Link;
import sailpoint.object.QueryOptions;
import sailpoint.object.Rule;
import sailpoint.object.TaskResult;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Message;
import sailpoint.tools.Util;
import sailpoint.web.lcm.IdentityAccountSelection;
import sailpoint.object.AccountSelection;
import sailpoint.object.AccountSelection.AccountInfo;
import sailpoint.object.Filter;
import java.util.Iterator;


@Test
public class TVR1_DisableAccountsTest extends Initializer {
	
private String workflowName;
	

	@Parameters({ "workflowName" })
	@BeforeTest
	
	public void configure(@Optional ("TriumvirLeaver3.0")String workflowName){
		this.workflowName = workflowName;
	}
	
	public void disableAccounts() throws GeneralException, InterruptedException{
	
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();
		
		// here we will give the data to the disable accounts
		launchVariables.put(ProjectVariable.DISABLE_ACCOUNTS.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "whenderson");
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName,"System",Constant.PROJECT_VARS,launchVariables,null);
		
		//Checking if the operation needs approvals and approving if needed
		String launchStatus = launch.getStatus().toString();
		if (launchStatus == WorkflowLaunch.STATUS_APPROVING){
			IdentityRequestModel requestModel = new IdentityRequestModel(context);
			WorkItem workItem = requestModel.getWorkItem(launch);
			requestModel.approveRequest(workItem);
		 }
		
		//Verifying that the changes were made
		Identity whendersonIdentity = context.getObjectByName(Identity.class, "whenderson");
		List<Link> links = whendersonIdentity.getLinks();
		boolean allAccountAreDisable = true;
		for(Link link : links){
			if (link.getAttributes().get("IIQDisabled").equals(false)){
				allAccountAreDisable = false;
				break;
			}
				
		}
		if (allAccountAreDisable = true){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
	}
}


