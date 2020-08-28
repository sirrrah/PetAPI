package com.ilabquality.api.stepImplementation;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPetAndGetById {

	Response response;
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;

	@Given("add pet payload")
	public void add_pet_payload() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://petstore.swagger.io/v2")
				.setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		requestSpec = given().spec(request).body("{\r\n" + 
				"  \"id\": 0,\r\n" + 
				"  \"category\": {\r\n" + 
				"    \"id\": 0,\r\n" + 
				"    \"name\": \"string\"\r\n" + 
				"  },\r\n" + 
				"  \"name\": \"" + getAutomatedName() + "\",\r\n" + 
				"  \"photoUrls\": [\r\n" + 
				"    \"string\"\r\n" + 
				"  ],\r\n" + 
				"  \"tags\": [\r\n" + 
				"    {\r\n" + 
				"      \"id\": 0,\r\n" + 
				"      \"name\": \"string\"\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"status\": \"available\"\r\n" + 
				"}");
	}

	@When("user calls add pet with POST HTTP request")
	public void user_calls_add_pet_with_post_http_request() {
		response = requestSpec.when().post("pet").then().spec(responseSpec).extract().response();
	}

	@Then("the API call processes successfully with status code {int}")
	public void the_api_call_processes_successfully_with_status_code(int code) {
		assertEquals(response.getStatusCode(), code);
	}

	@And("{string} in the response body is {string}")
	public void in_the_response_body_is(String status, String expected) {
		JsonPath json = new JsonPath(response.asString());
		assertEquals(expected, json.getString(status));
	}

	@And("pet object in add pet call is the same object in response")
	public void pet_object_in_add_pet_call_is_the_same_object_in_response() {
		JsonPath json = new JsonPath(response.asString());
		String pet = requestSpec.when().get("pet/" + json.getString("id")).then().assertThat().statusCode(200).log()
				.all().extract().response().asString();

		System.out.println("created pet : \n" + pet);
	}

	protected String getAutomatedName() {

		String chars = "abcdefghijklmnopqrstuvwxyz ";
		StringBuilder str = new StringBuilder();
		Random rand = new Random();

		while (str.length() < 10) {
			int index = (int) (rand.nextFloat() * chars.length());
			str.append(chars.charAt(index));
		}

		return str.toString().trim();
	}
}
