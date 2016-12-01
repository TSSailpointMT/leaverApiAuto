package leaver.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.spring.SpringStarter;
import sailpoint.tools.GeneralException;

public class Initializer {
	
	private SpringStarter ss;
	protected SailPointContext context;

	@BeforeSuite(alwaysRun = true)
	public void initEnvironment() {

		ss = new SpringStarter("iiqBeans");
		String[] suppressed = { "Task", "Request", "Heartbeat" };
		SpringStarter.setSuppressedServices(suppressed);
		
		ss.start();
	}
	
	/**
	 * Shutdown Spring Context
	 * 
	 */
	@AfterSuite(alwaysRun = true)
	public void shutdownEnvironment() {
		ss.close();
	}
	
	@Parameters({ "default-context-user" })
	@BeforeClass(alwaysRun = true)
	public void initContext( @Optional String defaultContextUser) throws GeneralException {
		System.out.println("Context created...");
		context = SailPointFactory.createContext();
		context.setUserName(defaultContextUser);
	}

	@AfterClass(alwaysRun = true)
	public void cleanupContext() throws GeneralException {
		SailPointFactory.releaseContext(context);
	}
	
	
	
}
