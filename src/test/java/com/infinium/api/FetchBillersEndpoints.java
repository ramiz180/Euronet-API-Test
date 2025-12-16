package com.infinium.api;

import com.infinium.utils.SpecBuilder;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class FetchBillersEndpoints {

    public static Response fetchBillers(String xVpcKey) {
        // If xVpcKey is null, we do not add the header
        var request = given()
                .spec(SpecBuilder.getRequestSpec())
                .header("accept", "application/json, text/plain, */*")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("origin", "https://stan-admin-7.web.app")
                .header("priority", "u=1, i")
                .header("referer", "https://stan-admin-7.web.app/")
                .header("sec-ch-ua", "\"Chromium\";v=\"142\", \"Google Chrome\";v=\"142\", \"Not_A Brand\";v=\"99\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "cross-site")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
        
        if (xVpcKey != null) {
            request.header("x-vpc-key", xVpcKey);
        }

        return request.when().get(Routes.get_fetch_billers_url);
    }

    public static Response fetchBillersPost(String xVpcKey) {
         return given()
                .spec(SpecBuilder.getRequestSpec())
                .header("x-vpc-key", xVpcKey)
        .when()
                .post(Routes.get_fetch_billers_url);
    }

    public static Response fetchBillersInvalidUrl(String xVpcKey) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .header("x-vpc-key", xVpcKey)
        .when()
                .get(Routes.get_fetch_billers_url + "/invalid");
    }
}
