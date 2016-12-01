package leaver.var;

import java.util.HashMap;

import leaver.datamodels.ProjectVariable;

public class Constant {

	public static final HashMap<String,Object> PROJECT_VARS;
	public static final String HIDE_EMAIL_PROPERTY = "msExchHideFromAddressLists";
	
	static{
		PROJECT_VARS = new HashMap<String,Object>();
		PROJECT_VARS.put(ProjectVariable.CREATE_MANUAL_WORKITEM.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.DELETE_ACCOUNT.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.DELETE_ENTITLEMENTS.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.DISABLE_ACCOUNTS.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.HIDE_EMAIL.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.IDENTITY_NAME.getName(),"");
		PROJECT_VARS.put(ProjectVariable.MOVE_OU.getName(),"false");
		PROJECT_VARS.put(ProjectVariable.RESET_PASSWORD.getName(),"false"); 
		PROJECT_VARS.put(ProjectVariable.TAKE_SNAPSHOT.getName(),"false");
		PROJECT_VARS.put(ProjectVariable.OU.getName(),"");
		PROJECT_VARS.put(ProjectVariable.WORKITEM_OWNER.getName(),"");
	}
	
}
