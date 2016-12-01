package leaver.testcases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

import sailpoint.api.TaskManager;
import sailpoint.object.Identity;
import sailpoint.object.Link;
import sailpoint.object.Rule;
import sailpoint.object.TaskResult;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Message;
import sailpoint.tools.Util;

import sailpoint.object.Filter;
import sailpoint.object.IdentitySnapshot;
import sailpoint.object.QueryOptions;
import java.util.Iterator;

public class TVR3_RemoveEntitlementForIdentityTest extends Initializer{
	private String workflowName;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("TriumvirLeaver3.0") String workflowName){
		this.workflowName = workflowName;
	}
	
	@Test
	public void removeEntitlement() throws GeneralException, InterruptedException{
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();

		// here we will give the data related to the Identity we will delete the Entitlements from
		launchVariables.put(ProjectVariable.DELETE_ENTITLEMENTS.getName(), true);
		//launchVariables.put(ProjectVariable., "AcctsPayableAccess");
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "Victor Fuzz");
		
		// this date will be used to compare with the creation of the snapshot
		Date dateBeforeCreation = new Date();
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName, "System", Constant.PROJECT_VARS, launchVariables, null);
		
		/*
		 * pending validation
		 * */
	}
}
