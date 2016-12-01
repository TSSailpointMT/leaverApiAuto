package leaver.datamodels;

public final class WorkflowVariable {
	
	private final ProjectVariable var;
	private final WorkflowVariableType type;
	private final String value;

	public WorkflowVariable(ProjectVariable var,
					WorkflowVariableType type,
					String value){
		this.var = var;
		this.type = type;
		this.value = value;
	}

	public ProjectVariable getVar() {
		return var;
	}

	public String getValue() {
		return value;
	}
	
	public WorkflowVariableType getType(){
		return type;
	}
	
}
