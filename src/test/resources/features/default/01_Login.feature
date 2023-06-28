@regression1
Feature: Login Test

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: User should not be able to login with invalid credential
    When I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    And I click on login button
    Then System should display '<errorMsg>' Error Message

    Examples:
      | username        | password     | errorMsg                                                                  |
      |                 |              | Epic sadface: Username is required                                        |
      |                 | test123      | Epic sadface: Username is required                                        |
      | test            |              | Epic sadface: Password is required                                        |
      | test            | test123      | Epic sadface: Username and password do not match any user in this service |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |

  Scenario Outline: User should be able to login with valid credential
    When I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    And I click on login button
    Then I should be able to login successfully

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | problem_user            | secret_sauce |
      | performance_glitch_user | secret_sauce |


  Scenario: I should be able to login as a Standard User
    Then I log in as standard user


  Scenario: I should be able to login as any type of user
    Then I log in as 'problem_user' user