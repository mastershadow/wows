package it.roccatello.wows.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo.BuilderConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import com.google.common.collect.Lists;

public class CustomRequestMappingHandler extends RequestMappingHandlerMapping {
  private BuilderConfiguration config;

  public CustomRequestMappingHandler() {
    super();
    this.config = new RequestMappingInfo.BuilderConfiguration();
    this.config.setPatternParser(new PathPatternParser());
  }

  @Override
  protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
    
    final RequestMappingInfo methodMapping = super.getMappingForMethod(method, handlerType);
    if (methodMapping == null) {
      return null;
    }

    final List<String> superclassUrlPatterns = new ArrayList<String>();
    boolean springPath = false;
    for (Class<?> clazz = handlerType; clazz != Object.class; clazz = clazz.getSuperclass()) {
      if (clazz.isAnnotationPresent(RequestMapping.class)) {
        if (springPath) {
          superclassUrlPatterns.add(clazz.getAnnotation(RequestMapping.class).value()[0]);
        } else {
          springPath = true;
        }
      }
    }
    
    if (!superclassUrlPatterns.isEmpty()) {
      
      final RequestMappingInfo superclassRequestMappingInfo = RequestMappingInfo
          .paths(StringUtils.join(Lists.reverse(superclassUrlPatterns), null))
          .options(this.config)
          .build();
      return superclassRequestMappingInfo.combine(methodMapping);
    } else
      return methodMapping;
  }
}
