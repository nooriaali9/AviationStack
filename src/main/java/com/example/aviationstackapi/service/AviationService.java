package com.example.aviationstackapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AviationService {

    @Value("${aviationstack.api.url}")
    private String apiUrl;

    @Value("${aviationstack.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AviationService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode getFlights(String query) {
        String url = String.format("%sflights?access_key=%s&%s", apiUrl, apiKey, query);
        String response =  restTemplate.getForObject(url, String.class);
        try {
            // Parse response into JsonNode
            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse API response", e);
        }
    }

    public JsonNode getAirlines(String airline) {
        String url = String.format("%sairlines?access_key=%s&%s", apiUrl, apiKey, airline);
        String response =  restTemplate.getForObject(url, String.class);
        try{
            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse API response", e);
        }
    }
}


