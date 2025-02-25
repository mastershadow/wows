package it.roccatello.wows.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    HashMapConverter.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(Map<String, Object> attributes) {
    try {
      return objectMapper.writeValueAsString(attributes);
    } catch (final JsonProcessingException e) {
      log.error("JSON writing error", e);
    }

    return null;
  }

  @Override
  public Map<String, Object> convertToEntityAttribute(String json) {
    try {
      return objectMapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
      });
    } catch (final IOException e) {
      log.error("JSON reading error", e);
    }

    return null;
  }

}
