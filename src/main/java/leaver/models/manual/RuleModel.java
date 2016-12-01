package leaver.models.manual;

import org.testng.log4testng.Logger;

import sailpoint.api.SailPointContext;
import sailpoint.object.Rule;

public class RuleModel {
	
	private SailPointContext context;
	private Logger log = Logger.getLogger(RuleModel.class);
	
	public RuleModel(SailPointContext context){
		this.context = context;
	}
	
	public Rule createRule(Class ruleClass){
		String path = ruleClass.getProtectionDomain().getCodeSource().getLocation().getPath();
		Rule rule = new Rule();
		
		return rule;
	}
	
}
