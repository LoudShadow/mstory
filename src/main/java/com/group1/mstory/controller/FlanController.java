package com.group1.mstory.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

@Component
public class FlanController {


    public String sendPostRequest(String requestString){
        String response = "";
        String inputString = String.format("{\"input\":\"%s\"}", requestString);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/postRequest"))
            .header("Content-Type", "application/json; charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString(inputString))
            .build();
        System.out.println(request.method());
        try{
            CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> httpResponse = futureResponse.get();
            response = httpResponse.body();
            System.out.println(response);
            return response;
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        
        return "";

        
    }
}
