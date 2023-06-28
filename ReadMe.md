# POC Project for Executing Cucumber Scenarios in Parallel using TestNG

## Introduction
This test automation framework is built using Java, Maven, Selenium, TestNG, Cucumber, Rest Assured, Page Object Model, Explicit Waiting, Parallel Execution, Allure Reporting, Listeners, Logging, Capturing Screenshots, and Error Handling. It is designed to make it easy to write, execute and maintain automated tests for web applications.


## Prerequisites
To use this framework, you will need:

* JDK 11 or higher
* Maven 3.8.5 or higher
* Latest stable version of GIT
* Latest stable version of Allure
* An IDE (IntelliJ IDEA is preferred)

## IDE Plugins
The following plugins are required for this project and can be easily installed from the IntelliJ IDEA marketplace.

* [TestNG-XML](https://plugins.jetbrains.com/plugin/9556-create-testng-xml)
* [Cucumber-Java](https://plugins.jetbrains.com/plugin/7212-cucumber-for-java)
* [Gherkin](https://plugins.jetbrains.com/plugin/9164-gherkin)
* [Markdown](https://plugins.jetbrains.com/plugin/7793-markdown)
* [GIT](https://plugins.jetbrains.com/plugin/13173-git)
* [HOCON](https://plugins.jetbrains.com/plugin/10481-hocon)
* [Ideolog](https://plugins.jetbrains.com/plugin/9746-ideolog)


## Getting Started
1. Clone the repository to your local machine
2. Import the project into your IDE as a Maven project
3. Update the `reference.conf` and `application.conf` files with your execution enviroment, target browser, target URL and other necessary configurations
4. Execute the tests by running the `Cucumber_Test_Runner` configuration


## Writing Tests
Tests are written in Cucumber, which uses the Gherkin syntax for describing scenarios. Feature files, which contain the scenarios, are located in the ```src/test/resources/features/default``` directory. Step definitions, which implement the actions described in the scenarios, are located in the ```com/vh/caramel/automation/stepdefs``` directory. The Page Object Model is used to define the web elements and actions.

## Explicit Waiting
Explicit waiting is implemented to handle dynamic loading of web elements. It waits for a specific condition to be met before proceeding with the next step in the test.

## Listeners
Listeners are used to listen to events during the test execution, such as test start, test finish, and test failure. They are used to capture screenshots and error logs for debugging purposes.

## Reporting
Allure reporting is used to generate detailed test reports.

### How to install Allure locally
<sub>Open Windows PowerShell and Enter following commands</sub>

1. Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
2. irm get.scoop.sh | iex
3. scoop install allure

### Generate Allure report
To generate a report, run the following command after running the tests:
```bash
allure serve --port 4242
```
This will start a local web server on port 4242 and open the report in your default web browser.

### Allure Severity
To set severity, add `@severity=blocker` on top of Scenario on Feature in your .feature file.
If severity has wrong value it will be forced to normal (default).

Supported severity values:

1. blocker
2. critical
3. normal
4. minor
5. trivial

## Logging
Logging is implemented using Log4j. Log files are located in the `logs` directory. The log configuration file is `log4j2.xml` By default, the log level is set to **INFO**. However, the user can set more severe log level as necessary.<br>
**Please note that any changes made to the log level should not be pushed to the remote repository, as a severe log levels can slow down test execution time.**

To change the log level, open the `log4j2.xml` file and find the following line:
```xml
 <Root level="INFO">
    <AppenderRef ref="File"/>
    <AppenderRef ref="Console"/>
</Root>
```
Change the value of the level attribute to the desired log level (e.g. "debug", "warn", "error").

For example, to set the log level to debug:

```xml
 <Root level="DEBUG">
    <AppenderRef ref="File"/>
    <AppenderRef ref="Console"/>
</Root>
```

## Error Handling and Assertions
Error handling is implemented using try-catch blocks and assertions. In case of a test failure, screenshots and error logs will be captured for debugging purposes.

## Local Execution
Tests can be executed sequentially by running the `Cucumber_Test_Runner` configuration

## Cucumber Options
Cucumber provides several options that can be passed to on the command-line.

| Property                      | Description                                              | Values                               | Default    |
|-------------------------------|----------------------------------------------------------|--------------------------------------|------------|
| cucumber.ansi-colors.disabled | Enable or disable ANSI color output                      | true/false                           | false      |
| cucumber.execution.dry-run    | Enable or disable dry-run mode                           | true/false                           | false      |
| cucumber.execution.limit      | Limit the number of scenarios to execute (CLI only)      | number                               | n/a        |
| cucumber.execution.order      | Set the order in which scenarios are executed (CLI only) | lexical/reverse/random/random:[seed] | lexical    |
| cucumber.execution.wip        | Enable or disable WIP mode                               | true/false                           | false      |
| cucumber.features             | Comma-separated paths to feature files                   | file paths                           | n/a        |
| cucumber.filter.name          | Specify a regex to filter scenarios by name              | regex                                | n/a        |
| cucumber.filter.tags          | Specify a tag expression to filter scenarios by tags     | tag expression                       | n/a        |
| cucumber.glue                 | Comma-separated package names for step definitions       | package names                        | n/a        |
| cucumber.plugin               | Comma-separated plugin strings                           | plugin names                         | n/a        |
| cucumber.object-factory       | Specify an object factory class name                     | fully qualified class name           | n/a        |
| cucumber.snippet-type         | Specify the style of generated step definitions          | underscore/camelcase                 | underscore |


## Execute From CMD

Example:
```bash
mvn test -DTEST_ENV="DEFAULT" -DBROWSER_TYPE="FIREFOX" -Dcucumber.features="src/test/resources/features/default" -Dcucumber.filter.tags="@smoke"
```

## Parallel Execution
Test scenarios can be executed in parallel to speed up the execution time. This feature is supported by TestNG.
<span style="color:red">**Before execute tests in parallel it is important that each scenario should be independent of other scenarios.**</span>
<br>
To turn on parallel execution mode set `@DataProvider(parallel = true)` in the `TestRunner` class.
By default, it will be set to false,
and execute this command in terminal to run
```bash
mvn clean install -DTEST_ENV="DEFAULT" -DBROWSER_TYPE="CHROME" -Ddataproviderthreadcount=3
```


# Happy Automation :)