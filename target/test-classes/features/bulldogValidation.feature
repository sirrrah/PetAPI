#Note: Each feature file should contain one feature
Feature: Validating if dog breeds have bulldogs

#Note: A feature may have different specific scenerios
Scenario: Verify that bulldog is on the list of all breeds
	Given Fetch breed list payload
	When user calls fetchAllBreeds with GET HTTP request method
	Then "message" in response body contains "bulldog"