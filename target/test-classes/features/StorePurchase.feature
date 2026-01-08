@Regression @StorePurchase
Feature: Store Purchase API
  As a user
  I want to purchase store items securely
  So that payment and fraud scenarios are handled correctly

  Background:
    Given the Store Purchase API is available

  @HappyPath
  Scenario: Verify Direct Purchase (Happy Path)
    Given I prepare a valid store purchase request
    And I set productId as "store_1000"
    And I set amount as 999
    And I set quantity as 1
    And I set inventoryId as 839
    And I set denominationId as 1
    And I set redirect URL
    And I set PhonePe payment mode as "NetBanking"
    And I set PhonePe target app as "com.phonepe.app"
    And I set additional parameters
    When I submit the store purchase request
    Then the response status code should be 200
    And the response should contain redirect URL

  @Security
  Scenario: Verify Unauthorized Access with Invalid Token
    Given I prepare a valid store purchase request
    And I set an invalid authorization token
    When I submit the store purchase request
    Then the response status code should be 400 or 401

  @Fraud
  Scenario: Verify Amount Manipulation
    Given I prepare a valid store purchase request
    And I set amount as 1
    When I submit the store purchase request
    Then the response status code should be 400

  @Fraud
  Scenario: Verify Quantity Manipulation
    Given I prepare a valid store purchase request
    And I set quantity as -1
    When I submit the store purchase request
    Then the response status code should be 400

  @Security
  Scenario: Verify Missing API Key
    Given I prepare a valid store purchase request
    And I remove API key
    When I submit the store purchase request
    Then the response status code should be 400 or 401

  @Security @Idempotency
  Scenario: Prevent duplicate store purchase request
    Given I prepare a valid store purchase request
    When I submit the store purchase request
    And I immediately submit the same store purchase request again
    Then the second response status code should be 409 or 400

  @Validation
  Scenario: Reject purchase with unsupported country code
    Given I prepare a valid store purchase request
    And I set country code as "US"
    When I submit the store purchase request
    Then the response status code should be 400

  @Security
  Scenario: Reject redirect URL from untrusted domain
    Given I prepare a valid store purchase request
    And I set redirect URL as "https://evil-site.com/callback"
    When I submit the store purchase request
    Then the response status code should be 400

  @Validation
  Scenario: Reject unsupported PhonePe payment mode
    Given I prepare a valid store purchase request
    And I set PhonePe payment mode as "Crypto"
    When I submit the store purchase request
    Then the response status code should be 400

  @Security @Abuse
  Scenario: Block excessive store purchase attempts
    Given I prepare a valid store purchase request
    When I submit the store purchase request multiple times rapidly
    Then the response status code should be 429 or 400

  @Validation @PhoneNumber
  Scenario: Reject phone number with invalid length
    Given I prepare a valid store purchase request
    And I set phone number as "98765"
    When I submit the store purchase request
    Then the response status code should be 400

  @Validation @PhoneNumber
  Scenario: Reject phone number containing alphabets
    Given I prepare a valid store purchase request
    And I set phone number as "98AB543210"
    When I submit the store purchase request
    Then the response status code should be 400

  @Validation @PhoneNumber
  Scenario: Reject phone number with special characters
    Given I prepare a valid store purchase request
    And I set phone number as "98765@3210"
    When I submit the store purchase request
    Then the response status code should be 400

  @Validation @PhoneNumber
  Scenario: Reject phone number with country code prefix
    Given I prepare a valid store purchase request
    And I set phone number as "+919876543210"
    When I submit the store purchase request
    Then the response status code should be 400

  @Security @Fraud @PhoneNumber
  Scenario: Detect rapid repeated purchase attempts using same phone number
    Given I prepare a valid store purchase request
    And I set phone number as "8765432138"
    When I submit the store purchase request multiple times rapidly
    Then the response status code should be 429 or 400
