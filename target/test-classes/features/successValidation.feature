Feature: Validating requests of random dog breed search

Scenario: Verify that a successful message is returned when a user searches for random breeds
	Given Fetch image payload
	When user calls fetchRandomImage with GET HTTP request method
	Then "status" in response body is "success"
	And the call processes successful with status code 200