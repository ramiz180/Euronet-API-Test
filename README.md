# Euronet API Test Automation

Its an API test automation framework built with Rest Assured, Cucumber, and TestNG, designed for testing Euronet services.

1. Tech Stack

-Java 8+
-Maven
-Cucumber (BDD)
-Rest Assured
-TestNG
-Allure Report
-Log4j2

## Project Structure

```
Euronet-API-Test/
├── .allure/                  # Allure report data
├── src/
│   ├── main/                 # Main source code
│   └── test/                 # Test source code
│       ├── java/com/euronet/
│       │   ├── api/          # API client and request/response models
│       │   ├── hooks/        # Cucumber hooks for test lifecycle
│       │   ├── listeners/    # TestNG and custom listeners
│       │   ├── runners/      # Test runners for different test suites
│       │   ├── stepDefinitions/  # Cucumber step definitions
│       │   └── utils/        # Utility classes and helpers
│       └── resources/
│           ├── configs/      # Configuration files
│           ├── features/     # Gherkin feature files
│           └── allure.properties  # Allure configuration
├── target/                   # Compiled classes and test reports
├── pom.xml                  # Maven build configuration
└── testng.xml               # TestNG test suite configuration
```

## Prerequisites

- Java 8 or higher
- Maven 3.6.0 or higher
- Allure Commandline (for report generation)

## Setup Instructions

1. **Clone the repository**:(Since it is in local so currently it will be not needed)
   ```bash
   git clone <repository-url>
   cd Euronet-API-Test
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```


## Generating Allure Reports

1. First, ensure tests have been executed to generate the test results.

2. Generate the Allure report:
   ```bash
   mvn allure:serve
   ```
   This will generate the report and open it in your default web browser.

3. To generate the report without serving it:
   ```bash
   mvn allure:report
   ```
   The report will be generated in `target/site/allure-maven-plugin/`

## Configuration

The project uses the following configuration files:
- `src/test/resources/configs/` - Contains environment-specific configurations
- `allure.properties` - Allure reporting configuration

## Adding New API Test Cases

To add a new API test case, follow these simple steps:

1. **Create a Feature File**
   - Navigate to `src/test/resources/features/`
   - Create a new `.feature` file (e.g., `NewAPI.feature`)
   - Write Gherkin scenarios following the Given-When-Then format
   - Add tags like @smoke, @regression, etc. in the feature file(optional).

2. **Implement Step Definitions**
   - Go to `src/test/java/com/euronet/stepDefinitions/`
   - Create a new Java class (e.g., `NewAPISteps.java`)
   - Implement the step definitions for your feature file

3. **Add API Client Methods-API Logic / Endpoints** (if needed)
   - Go to `src/test/java/com/euronet/api/`
   - Add new request/response models if required
   - Add API client methods to interact with the endpoint
   - Refer the Endpoint from Routes class

4. **Run and Verify**
   ```bash
   mvn test -Dtest=TestRunner#yourTestName
   ```
   Replace `TestRunner` with your test runner class and `yourTestName` with your test method name.



## Test Execution

Tests are written in Gherkin format (`.feature` files) and implemented using Cucumber and Rest Assured. The framework supports:
- BDD-style test scenarios
- Data-driven testing
- Parallel test execution
- Reporting with Allure

## Dependencies

- **Core**: Rest Assured, TestNG, Cucumber
- **Reporting**: Allure
- **Utilities**: Lombok, Gson, Jackson, Apache Commons Lang3

## IDE Setup

### IntelliJ IDEA
1. Import as Maven project
2. Enable annotation processing (for Lombok)
3. Install Cucumber and Gherkin plugins

### VS Code/ Windsurf
1. Install Java Extension Pack
2. Install Cucumber (Gherkin) Full Support extension

## Troubleshooting

- **Build issues**: Run `mvn clean install -U` to update dependencies

## Contact
For any further assistance, please feel free to connect me
-Manabi Mondal
