package leaver.models.manual;

import java.util.HashMap;

import org.testng.Assert;

import leaver.var.Constant;

import sailpoint.api.SailPointContext;
import sailpoint.api.Workflower;
import sailpoint.object.Attributes;
import sailpoint.object.Workflow;
import sailpoint.object.WorkflowLaunch;
import sailpoint.tools.GeneralException;

public class WorkflowModel{

	private SailPointContext context;

	public WorkflowModel(SailPointContext context){
		this.context = context;
	}

	public WorkflowLaunch launchExistingWorkflow(String workflowName,
			String launcherName,
			HashMap<String,Object> defaultVariables,
			HashMap<String,Object> launchVariables,
			Attributes<String,Object> workflowVariables) throws GeneralException{

		Workflow workflow = context.getObjectByName(Workflow.class, workflowName);

		WorkflowLaunch workflowLaunch = new WorkflowLaunch();
		workflowLaunch.setLauncher(launcherName);
		workflowLaunch.setWorkflowName(workflow.getName());
		workflowLaunch.setWorkflowRef(workflow.getName());
		workflowLaunch.setCaseName(workflow.getName());
		workflowLaunch = setLaunchVariables(workflowLaunch, defaultVariables, launchVariables);

		Workflower workflower = new Workflower(context);
		return workflower.launch(workflowLaunch);
	}

	private WorkflowLaunch setLaunchVariables(WorkflowLaunch launcher,
											HashMap<String, Object> defaultVariables,
											HashMap<String, Object> variables){
		for(String variable : defaultVariables.keySet()){
			if(variables.containsKey(variable)){
				defaultVariables.put(variable, variables.get(variable));
			}
		}

		launcher.setVariables(defaultVariables);
		return launcher;
	}

	public void compareWorkflowStatus(WorkflowLaunch launch, String status){
		Assert.assertTrue(launch.getStatus().equals(status));
	}

}
