package it.roccatello.wows.controller.api;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.roccatello.wows.model.dto.DtoValidationRequest;
import it.roccatello.wows.model.dto.DtoValidationResponse;

public interface ReadWriteApi<T> extends ReadApi<T> {
  @GetMapping(value = { "/select" })
  List<T> select(HttpServletRequest request);

  @PostMapping("/save")
  T save(HttpServletRequest request, @RequestBody T data);

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable Long id);

  @PostMapping("/validate")
  DtoValidationResponse validate(HttpServletRequest request, @RequestBody DtoValidationRequest data);
}
