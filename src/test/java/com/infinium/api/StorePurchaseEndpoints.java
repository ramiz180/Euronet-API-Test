package com.infinium.api;

import com.infinium.utils.SpecBuilder;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class StorePurchaseEndpoints {

    public static Response makeStorePurchase(String token, String apiKey, Object payload) {
        var request = given()
                .spec(SpecBuilder.getRequestSpec())
                .header("accept", "application/json, text/plain, */*")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("appversion", "500")
                .header("content-type", "application/json")
                .header("countrycode", "IN")
                .header("origin", "https://stage-store.getstan.app")
                .header("platform", "web")
                .header("priority", "u=1, i")
                .header("referer", "https://stage-store.getstan.app/")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-site")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        if (apiKey != null) {
            request.header("x-api-key", apiKey);
        }

        if (token != null) {
            request.header("authorization", "Bearer " + token);
        }

        return request
                .body(payload)
                .when()
                .post(Routes.store_purchase_url);
    }
}
