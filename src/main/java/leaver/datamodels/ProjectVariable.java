package leaver.datamodels;

public enum ProjectVariable {
	CREATE_MANUAL_WORKITEM("createManualWorkItem"),
	DELETE_ACCOUNT("deleteAccounts"),
	DELETE_ENTITLEMENTS("deleteEntitlements"),
	DISABLE_ACCOUNTS("disableAccounts"),
	HIDE_EMAIL("hideEmail"),
	IDENTITY_NAME("identityName"),
	MOVE_OU("moveOu"),
	OU("ou"),
	WORKITEM_OWNER("owner"),
	RESET_PASSWORD("resetPassword"),
	TAKE_SNAPSHOT("takeSnapshot");
	
	private final String name;
	
	public String getName() {
		return name;
	}
	
	private ProjectVariable(String name){
		this.name = name;
	}
	
}
