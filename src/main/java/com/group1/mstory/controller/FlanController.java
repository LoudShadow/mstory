package com.group1.mstory.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.group1.mstory.connectors.JdbcConnector;

@Configuration
@PropertySource("classpath:db.properties")
public class FlanController {
    @Autowired
    Environment env;

    Logger logger = LogManager.getLogger(JdbcConnector.class);
    String ERROR_MESSAGE = "Sorry, I was unable to connect to my processing. Please try again later.";

    public String sendPostRequest(String requestString){
        String response = "";
        String inputString = String.format("{\"input\":\"%s\"}", requestString);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(env.getProperty("spring.datasource.flanEndpoint")))
            .header("Content-Type", "application/json; charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString(inputString))
            .build();
        try{
            CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> httpResponse = futureResponse.get();
            response = httpResponse.body();
            response = response.substring(18,response.length()-7);

            return response;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted", ex);
            return ERROR_MESSAGE;
        } catch (Exception ex) {
            return ERROR_MESSAGE;
        }
    }
}
