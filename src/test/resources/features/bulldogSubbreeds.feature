Feature: Get bulldog sub-breeds and their images
Scenario: Retrieve all sub-breeds for bulldogs and their respective images
	Given Fetch bulldog breed list payload
	When user calls bulldogBreeds with GET HTTP request method
	Then response body lists an array of sub-breeds
	And retrieve images of each sub-breed