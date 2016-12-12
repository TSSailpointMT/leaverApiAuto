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
import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.hxtt.a.c;

import leaver.datamodels.ProjectVariable;
import leaver.models.manual.IdentityRequestModel;
import leaver.models.manual.ImportModel;
import leaver.models.manual.RuleModel;
import leaver.models.manual.WorkflowModel;
import leaver.util.Initializer;
import leaver.var.Constant;

import sailpoint.api.TaskManager;
import sailpoint.object.Identity;
import sailpoint.object.IdentityEntitlement;
import sailpoint.object.Link;
import sailpoint.object.Rule;
import sailpoint.object.TaskResult;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Message;
import sailpoint.tools.Util;
import sailpoint.web.lcm.EntitlementsRequestBean.EntitlementIdentity;
import workday.com.Integration_Document_FieldObjectIDType;
import sailpoint.object.Application;
import sailpoint.object.Attributes;
import sailpoint.object.Entitlement;
import sailpoint.object.Filter;
import sailpoint.object.IdentitySnapshot;
import sailpoint.object.QueryOptions;
import java.util.Iterator;

public class TVR3_RemoveEntitlementForIdentityTest extends Initializer{
	private String workflowName;
	private Object link;

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
		launchVariables.put(ProjectVariable.IDENTITY_NAME.getName(), "Bob Fields");
		
		//configuring and launching the workflow
		WorkflowModel workflowModel = new WorkflowModel(context);
		WorkflowLaunch launch = workflowModel.launchExistingWorkflow(workflowName, "System", Constant.PROJECT_VARS, launchVariables, null);
		
		
		// new QO to go through the entitlements 
        QueryOptions qo = new QueryOptions();
        qo.setOrderBy("created");
        qo.setOrderAscending(false);
        Filter f = Filter.eq("displayName", "Bob Fields");
        qo.addFilter(f);
		
      //Verifying that the changes were made
        List<IdentityEntitlement> listEntitlements= context.getObjects(IdentityEntitlement.class, qo);
		
        if (listEntitlements.isEmpty()){
       		Assert.assertTrue(true);
       	}
       	else{
       		Assert.assertTrue(false);
       	}
		
	}

}
