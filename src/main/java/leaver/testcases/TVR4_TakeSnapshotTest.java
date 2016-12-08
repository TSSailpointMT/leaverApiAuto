package leaver.testcases;

import java.util.Date;
import java.util.HashMap;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import leaver.datamodels.ProjectVariable;
import leaver.models.manual.WorkflowModel;
import leaver.util.Initializer;
import leaver.var.Constant;

import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;

import sailpoint.object.Filter;
import sailpoint.object.IdentitySnapshot;
import sailpoint.object.QueryOptions;
import java.util.Iterator;

public class TVR4_TakeSnapshotTest extends Initializer{
	private String workflowName;

	@Parameters({ "workflowName" })
	@BeforeTest
	public void configure(@Optional ("TriumvirLeaver3.0") String workflowName){
		this.workflowName = workflowName;
	}
	
	@Test
	public void takeSnapshot() throws GeneralException, InterruptedException{
		//Setting up the variables for this case...
		HashMap<String,Object> launchVariables = new HashMap<String,Object>();

		// here we will give the data to the snapshot
		launchVariables.put(ProjectVariable.TAKE_SNAPSHOT.getName(), true);
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "Ann Parson");
		
		// this date will be used to compare with the creation of the snapshot
		Date dateBeforeCreation = new Date();
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName, "System", Constant.PROJECT_VARS, launchVariables, null);
		
		// new QO to go through the snapshots 
        QueryOptions qo = new QueryOptions();
        qo.setOrderBy("created");
        qo.setOrderAscending(false);
       
        // filtering for the Identity
        Filter f = Filter.eq("identityName", "Ann Parson");
        qo.addFilter(f);
        Iterator it = context.search(IdentitySnapshot.class, qo); //you can change the class in order to get other objects
       
        // iteration on the snapshot for the specific Identity
        if (!it.hasNext())
        {
        	// there is no snapshots 
            Assert.assertTrue(false);
        }
        else
        {
        	boolean snapshotFound = false;
            while (it.hasNext())
            {
                IdentitySnapshot is = (IdentitySnapshot) it.next();  
               
                // if the date of the snapshot if before the date we started the test, in means we found the new snapshot
                if( is.getCreated().after(dateBeforeCreation))
                {
                    // snapshot found
                	Assert.assertTrue(true);
                	// we will set this as true to let the next if know that we found the snapshot
                	snapshotFound = true;
                	break;
                }
            }
            // if isTrue = false it means that there are no new snapshot, if isTrue = true is means there is a new snapshot
            if (!snapshotFound){
            	 Assert.assertTrue(false);
            }
        }
	}
}