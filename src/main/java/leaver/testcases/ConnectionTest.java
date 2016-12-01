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
/*
import leaver.test.datamodels.ProjectVariable;
import leaver.test.models.manual.IdentityRequestModel;
import leaver.test.models.manual.ImportModel;
import leaver.test.models.manual.RuleModel;
import leaver.test.models.manual.WorkflowModel;
import leaver.test.util.Initializer;
import leaver.test.var.Constant;
*/
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

public class ConnectionTest extends Initializer{

	private String workflowName;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("test_workFlow") String workflowName){
		this.workflowName = workflowName;
	}
	
	@Test
	public void testConnection() throws GeneralException, IOException, SAXException{
		TaskManager taskManager = new TaskManager(context);
		HashMap<String, Object> args = new HashMap<String, Object>();
		
		TaskResult diffCheck = taskManager.runSync("T3 Diff Check", args);
		List<Message> messages = diffCheck.getMessages();
		System.out.println("Printing messages...");
		for(Message message : messages){;
			System.out.println(message.getKey());
			for(Object obj : message.getParameters()){
				if(obj != null)
					System.out.println(obj.toString());
			}
		}
	}

	public void runRule() throws Exception {
		Rule rule = context.getObjectByName(Rule.class, "T3 Diff Rule");
		String result = context.runRule(rule, new HashMap<String, Object>()).toString();
		System.out.println(result);
	}

	public void importTest() throws Exception{
		ImportModel importModel = new ImportModel(context);
		importModel.importFile("./iiq/config/Identity/IdentityWithADAndLDAPAccount.xml");
	}
	
	public void hideEmailTest() throws GeneralException, InterruptedException{
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();
		launchVariables.put(ProjectVariable.HIDE_EMAIL.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "testIdentity");
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName, 
																	"System", 
																	Constant.PROJECT_VARS,
																	launchVariables, 
																	null);
		
		//Checking if the operation needs approvals and approving if needed
		workflowModel.compareWorkflowStatus(launch, WorkflowLaunch.STATUS_APPROVING);
		IdentityRequestModel requestModel = new IdentityRequestModel(context);
		WorkItem workItem = requestModel.getWorkItem(launch);
		requestModel.approveRequest(workItem);
		
		//Verifying that the changes were made
		Identity testIdentity = context.getObjectByName(Identity.class, "testIdentity");
		List<Link> links = testIdentity.getLinks();
		for(Link link : links){
			if(link.getApplicationName().equals("AD")){
				Assert.assertTrue((boolean) link.getAttributes().get(Constant.HIDE_EMAIL_PROPERTY));
			}
		}
	}
}
