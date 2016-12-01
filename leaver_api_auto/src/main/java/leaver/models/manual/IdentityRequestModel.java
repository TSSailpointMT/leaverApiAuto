package leaver.models.manual;

import java.util.ArrayList;

import sailpoint.api.SailPointContext;
import sailpoint.api.Workflower;
import sailpoint.object.ApprovalItem;
import sailpoint.object.ApprovalSet;
import sailpoint.object.Identity;
import sailpoint.object.IdentityRequest;
import sailpoint.object.TaskResult;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkItem.State;
import sailpoint.object.WorkItemMonitor;
import sailpoint.object.WorkflowLaunch;
import sailpoint.object.WorkflowSummary;
import sailpoint.object.IdentityRequest.ExecutionStatus;
import sailpoint.object.WorkflowSummary.ApprovalSummary;
import sailpoint.tools.GeneralException;

public class IdentityRequestModel{
	
	private SailPointContext context;
	
	public IdentityRequestModel(SailPointContext context){
		this.context = context;
	}
	
	public WorkItem getWorkItem(WorkflowLaunch launch) throws GeneralException {
		WorkflowSummary workflowSummary = (WorkflowSummary) launch.getTaskResult().getAttribute("workflowSummary");
		ArrayList<ApprovalSummary> approvalSummaries = new ArrayList<>(workflowSummary.getInteractions());
		String workItemId = "";
		for(ApprovalSummary summary : approvalSummaries){
			workItemId = summary.getWorkItemId();
		}
		WorkItem workitem = context.getObjectById(WorkItem.class, workItemId);
		return workitem;
	}
	
	public IdentityRequest getIdentityRequest(WorkflowLaunch launch) throws GeneralException{
		WorkItem workItem = getWorkItem(launch);
		return context.getObjectById(IdentityRequest.class, workItem.getIdentityRequestId());
	}
	
	public void approveRequest(WorkItem workItem) throws GeneralException, InterruptedException{
		ApprovalSet approvals = workItem.getApprovalSet();
		for(ApprovalItem approval : approvals.getItems()){
			approval.approve();
		}
		workItem.setApprovalSet(approvals);
		workItem.setState(State.Finished);
		context.saveObject(workItem);
		context.commitTransaction();
		workItem.setState(WorkItem.State.Finished);

		Workflower wf = new Workflower(context);
		wf.process(workItem, true);
		
		Thread.sleep(1000);
		context.decache();
	}
	
}
