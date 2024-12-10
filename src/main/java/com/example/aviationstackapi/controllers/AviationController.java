package com.example.aviationstackapi.controllers;

import com.example.aviationstackapi.service.AviationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AviationController {

    private final AviationService aviationService;
    private final ObjectMapper objectMapper;
    public AviationController(AviationService aviationService) {
        this.aviationService = aviationService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/api/flights")
    public ObjectNode fetchFlights(@RequestParam String query) {
        JsonNode rawData = aviationService.getFlights(query);

        // Create a new ObjectNode for the formatted response
        ObjectNode formattedResponse = objectMapper.createObjectNode();
        formattedResponse.put("summary", "Filtered flight data for your query.");

        // Extract the 'data' array from rawData
        ArrayNode flightsArray = objectMapper.createArrayNode();
        if (rawData.has("data")) {
            for (JsonNode flight : rawData.get("data")) {
                ObjectNode flightInfo = objectMapper.createObjectNode();
                flightInfo.put("flight_number", flight.path("flight").path("number").asText());
                flightInfo.put("departure_airport", flight.path("departure").path("airport").asText());
                flightInfo.put("arrival_airport", flight.path("arrival").path("airport").asText());
                flightInfo.put("status", flight.path("flight_status").asText());
                flightInfo.put("scheduled_departure", flight.path("departure").path("scheduled").asText());
                flightInfo.put("scheduled_arrival", flight.path("arrival").path("scheduled").asText());
                flightsArray.add(flightInfo);
            }
        }

        formattedResponse.set("data", flightsArray);
        return formattedResponse;
    }

    @GetMapping("/api/airlines")
    public ObjectNode fetchAirlines(@RequestParam String query) {
        JsonNode rawData = aviationService.getAirlines(query);

        // Create a new ObjectNode for the formatted response
        ObjectNode formattedResponse = objectMapper.createObjectNode();
        formattedResponse.put("summary", "Filtered Airline data for your query.");

        // Extract the 'data' array from rawData
        ArrayNode airlinesArray = objectMapper.createArrayNode();
        if (rawData.has("data")) {
            for (JsonNode airline : rawData.get("data")) {
                ObjectNode airlineInfo = objectMapper.createObjectNode();
                airlineInfo.put("name", airline.path("airline_name").asText());
                airlineInfo.put("iata_code", airline.path("iata_code").asText());
                airlineInfo.put("icao_code", airline.path("icao_code").asText());
                airlineInfo.put("country", airline.path("country_name").asText());

                airlinesArray.add(airlineInfo);
            }
        }

        formattedResponse.set("data", airlinesArray);
        return formattedResponse;
    }
}


