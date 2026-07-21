package com.platinum.model;

 

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class CustomDoubleDeserializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();

        // Check if the string is null, empty, or your specific override string (e.g., "N/A")
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
            return 0.0; // Or return null if your field is a Double wrapper
        }

        // Otherwise, parse it as a standard double
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0; // Fallback for other unparseable formats
        }
    }
}