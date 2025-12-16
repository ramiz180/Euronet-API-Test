@FetchBillers @Regression
Feature: Fetch Billers Validation

  Scenario: EB-01 Fetch billers with valid auth
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And the billers list should be present

  Scenario: EB-02 Validate response format
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And the response should be in JSON format
    And the response should contain a data array

  Scenario: EB-03 Validate mandatory fields
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And each biller should contain "id" and "name"

  Scenario: EB-04 Multiple consecutive calls
    Given a valid x-vpc-key is provided
    When I send 5 consecutive GET requests to fetch billers
    Then every response status code should be 200

  Scenario: EB-05 Response consistency
    Given a valid x-vpc-key is provided
    When I send 2 consecutive GET requests to fetch billers
    Then the responses should be consistent

  Scenario: EB-06 Missing auth key
    Given no x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 401

  Scenario: EB-07 Invalid auth key
    Given an invalid x-vpc-key "INVALID_KEY_12345"
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 401

  Scenario: EB-08 Invalid HTTP method
    Given a valid x-vpc-key is provided
    When I send a POST request to fetch billers
    Then the fetch billers response status code should be 405

  Scenario: EB-09 Invalid endpoint
    Given a valid x-vpc-key is provided
    When I send a GET request to an invalid endpoint
    Then the fetch billers response status code should be 404

  Scenario: EB-10 Empty biller list check
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And the billers list should be a valid array (empty or not)

  Scenario: EB-11 Duplicate billers check
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And no duplicate "id" values should be present

  Scenario: EB-12 Response time check
    Given a valid x-vpc-key is provided
    When I send a GET request to fetch billers
    Then the fetch billers response status code should be 200
    And the response time should be less than 1000 milliseconds
