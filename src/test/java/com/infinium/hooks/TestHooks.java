package com.infinium.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestHooks {
    private static final Logger logger = LogManager.getLogger(TestHooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("SCENARIO: " + scenario.getName());
        System.out.println("--------------------------------------------------------------------------------");
        logger.info("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("--------------------------------------------------------------------------------");
        String status = scenario.isFailed() ? "FAILED" : "PASSED";
        System.out.println("RESULT: " + status);
        System.out.println("--------------------------------------------------------------------------------\n");

        if (scenario.isFailed()) {
            logger.error("Scenario failed: " + scenario.getName());
        } else {
            logger.info("Scenario passed: " + scenario.getName());
        }
        
        try {
            // Wait 2 seconds between tests to avoid 429 Rate Limit Exceeded
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
