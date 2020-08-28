package com.ilabquality.api.stepImplementation;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SuccessMessageTest {

	Response response;
	RequestSpecification requestSpec;
	
	@Given("Fetch image payload")
	public void fetch_image_payload() {		
		RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://dog.ceo").build();
		requestSpec = given().spec(request);
	}

	@When("user calls fetchRandomImage with GET HTTP request method")
	public void user_calls_fetch_random_image_with_get_http_request_method() {
		response = requestSpec.when().get("api/breeds/image/random");
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
		JsonPath json = new JsonPath(response.asString());
		assertEquals(json.getString(key), value);
		System.out.println("Random dog image: " + json.getString("message"));
	}

	@And("the call processes successful with status code {int}")
	public void the_call_processes_successful_with_status_code(int code) {
		assertEquals(response.getStatusCode(), code);
	}

}
