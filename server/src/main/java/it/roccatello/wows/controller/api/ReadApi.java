package it.roccatello.wows.controller.api;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.roccatello.wows.model.dto.DtoDataContent;
import it.roccatello.wows.model.dto.DtoDataFilter;

public interface ReadApi<T> {
  @GetMapping("/{id}")
  T id(HttpServletRequest request, @PathVariable Long id);

  @GetMapping("/count")
  Long count(HttpServletRequest request, @RequestParam(required = false) DtoDataFilter filter);

  @GetMapping(value = { "/list/{page}", "/list/{page}/{count}" })
  DtoDataContent<T> list(HttpServletRequest request, @PathVariable Integer page, @PathVariable Optional<Integer> count,
      @RequestParam(required = false) DtoDataFilter filter);

}
