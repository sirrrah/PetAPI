package com.ilabquality.api.stepImplementation;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BulldogSubbreeds {

	Response response;
	RequestSpecification requestSpec;

	@Given("Fetch bulldog breed list payload")
	public void fetch_bulldog_breed_list_payload() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://dog.ceo").build();
		requestSpec = given().spec(request);
	}

	@When("user calls bulldogBreeds with GET HTTP request method")
	public void user_calls_bulldog_breeds_with_get_http_request_method() {
		response = requestSpec.when().get("api/breed/bulldog/list");
	}

	@Then("response body lists an array of sub-breeds")
	public void response_body_lists_an_array_of_sub_breeds() {

		JsonPath json = new JsonPath(response.asString());

		System.out.println("Bulldog sub-breeds: " + json.getList("message"));

	}

	@Then("retrieve images of each sub-breed")
	public void retrieve_images_of_each_sub_breed() {

		JsonPath json = new JsonPath(response.asString());
		int breedSize = json.getInt("message.size()");

		for (int i = 0; i < breedSize; i++) {
			System.out.println(json.getString("message[" + i + "]") + " bulldogs : " + getImages(json.getString("message[" + i + "]")));
		}
	}

	private List<String> getImages(String subbreed) {
		
		Response onse = requestSpec.when().get("api/breed/bulldog/" + subbreed + "/images");
		JsonPath json = new JsonPath(onse.asString());
		
		return json.getList("message");
	}
}
