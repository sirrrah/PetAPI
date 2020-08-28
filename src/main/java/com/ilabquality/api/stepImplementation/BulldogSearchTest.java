package com.ilabquality.api.stepImplementation;

import static io.restassured.RestAssured.given;
import org.junit.Assert;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BulldogSearchTest {

	Response response;
	RequestSpecification requestSpec;
	
	@Given("Fetch breed list payload")
	public void fetch_breed_list_payload() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://dog.ceo").build();
		requestSpec = given().spec(request);
	}

	@When("user calls fetchAllBreeds with GET HTTP request method")
	public void user_calls_fetch_all_breeds_with_get_http_request_method() {
		response = requestSpec.when().get("api/breeds/list/all");
	}

	@Then("{string} in response body contains {string}")
	public void in_response_body_contains(String message, String expected) {
		
		JsonPath json = new JsonPath(response.asString());
		boolean bulldogs = json.getString(message).contains(expected);
		Assert.assertTrue(bulldogs);

		if (bulldogs) {
			System.out.println("\n" + expected + " breed found! " + json.getList("message.bulldog") + "\n");
		} else {
			System.out.println(expected + " breed does not exist in the list");
		}
		
	}
	
}
