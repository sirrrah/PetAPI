Feature: Confirming a new pet has been successfully added and retrieve it by ID

Scenario: Add a new pet with an auto generated name and status available
	Given add pet payload
	When user calls add pet with POST HTTP request
	Then the API call processes successfully with status code 200
	And "status" in the response body is "available"
	And pet object in add pet call is the same object in response