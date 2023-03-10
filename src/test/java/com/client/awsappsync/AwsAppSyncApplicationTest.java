package com.client.awsappsync;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class AwsAppSyncApplicationTest {
    @Test
    public void givenGraphQuery_whenListEvents_thenReturnAllEvents() {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("query", "query MyQuery {"
                + "  allUsers {"
                + "      id"
                + "      name"
                + "  }"
                + "}");

        requestBody.put("variables", "");
        requestBody.put("operationName", "MyQuery");

        String bodyString = AppSyncClientHelper.getResponseBodySpec(requestBody)
                .bodyToMono(String.class).block();

        assertNotNull(bodyString);
        assertTrue(bodyString.contains("example.com"));
    }

    @Test
    public void givenGraphAdd_whenMutation_thenReturnIdNameDesc() {

        String queryString = "mutation MyMutation {"
                + "    addUser ("
                + "        userId:\"12345@gmail.com\", username:\"Person 12345\""
                + "    )"
                + "}";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", queryString);
        requestBody.put("variables", "");
        requestBody.put("operationName", "MyMutation");

        WebClient.ResponseSpec response = AppSyncClientHelper.getResponseBodySpec(requestBody);

        String bodyString = response.bodyToMono(String.class).block();

        assertNotNull(bodyString);
    }
}