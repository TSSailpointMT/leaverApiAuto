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
import sailpoint.api.SailPointFactory;
import sailpoint.api.TaskManager;
import sailpoint.api.WorkItemNotificationHandler;
import sailpoint.object.Identity;
import sailpoint.object.IdentitySnapshot;
import sailpoint.object.Link;
import sailpoint.object.QueryOptions;
import sailpoint.object.Rule;
import sailpoint.object.TaskResult;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkItemArchive;
import sailpoint.object.WorkflowLaunch;
import sailpoint.service.WorkItemResult;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Message;
import sailpoint.tools.Util;
import sailpoint.object.Filter;
import java.util.Iterator;

@Test
public class TVR9_CreateManualWorkItemTest extends Initializer{
	private String workflowName;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("TriumvirLeaver3.0")String workflowName){
		this.workflowName = workflowName;
	}
	
	public void createManualWorkItem() throws GeneralException, InterruptedException{
	
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();
		
		// here we will give the data to the manual workitem
		launchVariables.put(ProjectVariable.CREATE_MANUAL_WORKITEM.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "Alan Snow");
		
		// this date will be used to compare with the creation of the workitem
		Date dateBeforeCreation = new Date();
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName,"Brian Murray",Constant.PROJECT_VARS,launchVariables,null);
				
		//Obtain the List of Objects of the work Item of requester
		QueryOptions qo = new QueryOptions();
		qo.addFilter(Filter.eq("requester.name", "Brian Murray"));
		List<WorkItem> listWorkItem = context.getObjects(WorkItem.class, qo);
		
		
		Iterator<WorkItem> it = listWorkItem.iterator();
		
        // iteration on the workitem for the specific requester
        if (!it.hasNext())
        {
        	Assert.assertTrue(false);
        }
        else
        {
        	boolean workItemFound = false;
            while (it.hasNext())
            {
            	WorkItem wi = (WorkItem) it.next(); 
               	if( wi.getCreated().after(dateBeforeCreation))
                {
                   	Assert.assertTrue(true);
                 	workItemFound = true;
                	break;
                }
            }
            if (!workItemFound){
            	 Assert.assertTrue(false);
            }
        }
	}
}

	


