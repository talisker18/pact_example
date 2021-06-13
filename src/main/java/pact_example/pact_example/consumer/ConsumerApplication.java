package pact_example.pact_example.consumer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class ConsumerApplication {
	public static String idOfJoelHenz;
	public static String port ="8080";

	public static void main(String[] args) {
		ConsumerApplication.sendNewPerson("Joel Henz");
		ConsumerApplication.sendNewPerson("Hans Hase");
		ConsumerApplication.sendNewPerson("John Doe");
		
		JSONArray persons =ConsumerApplication.getAllPersons(ConsumerApplication.port);
		
		//search JSONObject with name = "Joel Henz" and get its id
		Iterator<JSONObject> objectIterator =  persons.iterator();
		idOfJoelHenz=null;
		
		while(objectIterator.hasNext()) {
			JSONObject jsonObj= objectIterator.next();
			System.out.println("json object in json array: "+jsonObj.toString());
			if(jsonObj.get("name").equals("Joel Henz")) {
				idOfJoelHenz=(String) jsonObj.get("id");
			}
			String id = (String) jsonObj.get("id");
		}
		
		//search Joel Henz with API, by id
		System.out.println("id of Joel Henz: "+idOfJoelHenz);
		ConsumerApplication.getPersonById(idOfJoelHenz);

	}
	
	public static void sendNewPerson(String name) {
		String url = "http://localhost:8080/api/v1/person";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		JSONObject jsonBody = new JSONObject(map);
		String httpBody = jsonBody.toString();
		Response resp = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.body(httpBody)
				.request(Method.POST, url);
		System.out.println(resp.statusCode());
	}
	
	public static JSONArray getAllPersons(String port) {
		String url = "http://localhost:"+port+"/api/v1/person";
		Response resp = RestAssured
				.given()
				.request(Method.GET, url);
		System.out.println("\nhttp response of getAllPersons: "+resp.asString());
		JSONParser parser = new JSONParser();
		JSONArray jsonArray=null;
		try {
			jsonArray = (JSONArray) parser.parse(resp.asString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public static void getPersonById(String id) {
		String url = "http://localhost:8080/api/v1/person/%s";
		url = String.format(url, id);
		Response resp = RestAssured
				.given()
				.request(Method.GET, url);
		System.out.println("\nhttp response of getPersonById: "+resp.asString());
	}

}
