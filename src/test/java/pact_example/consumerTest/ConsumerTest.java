package pact_example.consumerTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import junit.framework.Assert;
import pact_example.pact_example.consumer.ConsumerApplication;

public class ConsumerTest {
	
	//see: https://blog.testproject.io/2020/05/27/consumer-driven-contract-testing-using-pact-java/
	
	@Rule
	public PactProviderRule provider = new PactProviderRule("PersonProvider","localhost",8081, this); //create mock service on port 8081
	
	@Pact(consumer = "PersonConsumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		
		//client information we expect in pact
		DslPart resultsSet1 = new PactDslJsonBody()
				.stringType("id","38400000-8cf0-11bd-b23e-10b96e4ef00d")
				.stringType("name","Joel Henz")
				.asBody();
		
		DslPart resultsSet2 = new PactDslJsonBody()
				.stringType("name","Mani Henz2")
				.asBody();
		
		return builder
				.given("providerState1")
				.uponReceiving("set1...")
				.path("/api/v1/person/38400000-8cf0-11bd-b23e-10b96e4ef00d")
				.method("GET")
				.willRespondWith()
				.status(200)
				.headers(headers)
				.body(resultsSet1)
				
				.given("providerState2")
				.uponReceiving("set2...")
				.path("/api/v1/person")
				.method("POST")
				.body(resultsSet2)
				.willRespondWith()
				.status(200)
				.headers(headers)
				.body("1")
				.toPact();
	}
	
	@Test
	@PactVerification()
	public void searchPersonJoelHenzById() {
		System.out.println(this.provider.getUrl());
		System.out.println(this.provider.getPort());
		
		//set1
		String url = this.provider.getUrl()+"/api/v1/person/38400000-8cf0-11bd-b23e-10b96e4ef00d";
		//url = String.format(url, id);
		Response resp = RestAssured
				.given()
				.request(Method.GET, url);
		System.out.println("response from mockserver: "+resp.asString());
		assert resp.statusCode()==200;
		
		//set2
		String url2 = this.provider.getUrl()+"/api/v1/person";
		//url = String.format(url, id);
		String body = "{\r\n" + 
				"    \"name\":\"Mani Henz2\"\r\n" + 
				"}";
		
		Response resp2 = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.body(body)
				.request(Method.POST, url2);
		System.out.println("response from mockserver: "+resp2.asString());
		assert resp2.statusCode()==200;
	}

}
