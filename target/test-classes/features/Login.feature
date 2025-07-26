Feature: Login functionality

  Scenario: Login with valid credentials
    Given User launches the application
    When User navigates to Login page
    And User enters valid email and password
    And User clicks the login button
    Then User should be navigated to My Account page
