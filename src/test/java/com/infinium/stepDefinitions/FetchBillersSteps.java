package com.infinium.stepDefinitions;

import com.infinium.api.FetchBillersEndpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FetchBillersSteps {

    private static final Logger logger = LogManager.getLogger(FetchBillersSteps.class);

    String xVpcKey;
    Response response;
    List<Response> consecutiveResponses = new ArrayList<>();

    @Given("the Fetch Billers API endpoint is available")
    public void the_fetch_billers_api_endpoint_is_available() {
        logger.info("Endpoint: Fetch Billers is ready to be tested.");
    }

    @Given("a valid x-vpc-key is provided")
    public void a_valid_x_vpc_key_is_provided() {
        xVpcKey = "e767222ca014e1b5ca769c0c74ec32f1f8324914d3c8ff01b95b37dd7e5e5cab327baa2de47886d449b5fc6d2ec216f8fdc53d6232907754e25e7bf051bca405";
    }
    
    @Given("no x-vpc-key is provided")
    public void no_x_vpc_key_is_provided() {
        xVpcKey = null;
    }

    @Given("an invalid x-vpc-key {string}")
    public void an_invalid_x_vpc_key(String invalidKey) {
        xVpcKey = invalidKey;
    }

    @When("I send a GET request to fetch billers")
    public void i_send_a_get_request_to_fetch_billers() {
        response = FetchBillersEndpoints.fetchBillers(xVpcKey);
        printResponseDetails(response);
    }
    
    @When("I send a POST request to fetch billers")
    public void i_send_a_post_request_to_fetch_billers() {
        response = FetchBillersEndpoints.fetchBillersPost(xVpcKey);
        printResponseDetails(response);
    }

    @When("I send a GET request to an invalid endpoint")
    public void i_send_a_get_request_to_an_invalid_endpoint() {
        response = FetchBillersEndpoints.fetchBillersInvalidUrl(xVpcKey);
        printResponseDetails(response);
    }

    @When("I send {int} consecutive GET requests to fetch billers")
    public void i_send_consecutive_get_requests(int count) {
        consecutiveResponses.clear();
        for (int i = 0; i < count; i++) {
            consecutiveResponses.add(FetchBillersEndpoints.fetchBillers(xVpcKey));
        }
    }

    @Then("the fetch billers response status code should be {int}")
    public void the_fetch_billers_response_status_code_should_be(int expectedStatusCode) {
        logger.info("Response Status: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @Then("every response status code should be {int}")
    public void every_response_status_code_should_be(int expectedStatusCode) {
        for (Response res : consecutiveResponses) {
            Assert.assertEquals(res.getStatusCode(), expectedStatusCode, "One of the consecutive responses failed.");
        }
    }

    @Then("the response should be in JSON format")
    public void the_response_should_be_in_json_format() {
        String contentType = response.getContentType();
        Assert.assertTrue(contentType.contains("application/json"), "Content-Type expected JSON but was " + contentType);
    }

    @Then("the billers list should be present")
    public void the_billers_list_should_be_present() {
        String responseBody = response.getBody().asString();
        // Just verify body is not null/empty. Logic for "list" is in specific step
        Assert.assertNotNull(responseBody, "Response body is null");
        Assert.assertFalse(responseBody.isEmpty(), "Response body is empty");
    }
    
    @Then("the response should contain a data array")
    public void the_response_should_contain_a_data_array() {
        try {
            // Check if 'data.billers' is a list
            List<Object> list = response.jsonPath().getList("data.billers");
             Assert.assertNotNull(list, "Response does not contain data.billers array");
        } catch (Exception e) {
             logger.error("Failed to find data.billers array. Body: " + response.getBody().asString());
             Assert.fail("Response expected to have data.billers array available");
        }
    }

    @Then("each biller should contain {string} and {string}")
    public void each_biller_should_contain_fields(String field1, String field2) {
        List<Map<String, Object>> billers = getBillersList();
        for (Map<String, Object> biller : billers) {
            Assert.assertTrue(biller.containsKey(field1), "Biller missing " + field1);
            Assert.assertTrue(biller.containsKey(field2), "Biller missing " + field2);
        }
    }

    @Then("the responses should be consistent")
    public void the_responses_should_be_consistent() {
        String firstBody = consecutiveResponses.get(0).getBody().asString();
        String secondBody = consecutiveResponses.get(1).getBody().asString();
        Assert.assertEquals(firstBody, secondBody, "Consecutive responses differ!");
    }

    @Then("no duplicate {string} values should be present")
    public void no_duplicate_values_should_be_present(String fieldName) {
        List<String> values = null;
        try {
            // Logic change: we fetch the list of billers first, then extract the field
            List<Map<String, Object>> billers = getBillersList();
            values = new ArrayList<>();
            for (Map<String, Object> b : billers) {
                if (b.get(fieldName) != null) {
                    values.add(String.valueOf(b.get(fieldName)));
                }
            }
        } catch (Exception ignored) {}
        
        Assert.assertNotNull(values, "Could not find list for field: " + fieldName);

        Set<String> uniqueValues = new HashSet<>(values);
        Assert.assertEquals(values.size(), uniqueValues.size(), "Duplicate " + fieldName + " found!");
    }

    @Then("the response time should be less than {int} milliseconds")
    public void the_response_time_should_be_less_than(int maxTime) {
        long time = response.getTime();
        logger.info("Response Time: " + time + "ms");
        System.out.println("Response Time: " + time + "ms");
        Assert.assertTrue(time < maxTime, "Response time " + time + "ms exceeded limit " + maxTime + "ms");
    }

    @Then("the billers list should be a valid array \\(empty or not)")
    public void the_billers_list_should_be_valid_array() {
        List<Map<String, Object>> list = getBillersList();
        Assert.assertNotNull(list, "Response is not a valid JSON array");
    }

    // Helper to safely extract list from data.billers
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getBillersList() {
        try {
            // Structure seen in logs: { "data": { "billers": [...] }, "success": true }
            Object billersNode = response.jsonPath().get("data.billers");
            if (billersNode instanceof List) {
                return (List<Map<String, Object>>) billersNode;
            }
        } catch (Exception ignored) {}

        String body = response.getBody().asString();
        logger.error("Could not parse as list. Body: " + body);
        Assert.fail("Failed to extract billers list from data.billers.");
        return null; // unreachable
    }


    private void printResponseDetails(Response response) {
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body:");
        response.prettyPrint();
    }
}
