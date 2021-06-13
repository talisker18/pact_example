package providerTest;

import org.junit.runner.RunWith;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("PersonProvider")
@PactFolder("target/pacts")

public class ProviderTest {
	
	@State("providerState1")
	public void someMethod() {
		
	}
	
	@State("providerState2")
	public void someMethod2() {
		
	}
	
	@TestTarget
	public final Target target = new HttpTarget(8080); // Out-of-the-box implementation of Target; use port of live system, so the provider application must be running

}
