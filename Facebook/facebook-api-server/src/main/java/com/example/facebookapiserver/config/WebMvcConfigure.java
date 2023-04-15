package com.example.facebookapiserver.config;
import com.example.facebookapiserver.config.support.SimpleOffsetPageableHandlerMethodArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

  @Bean
  public SimpleOffsetPageableHandlerMethodArgumentResolver simpleOffsetPageableHandlerMethodArgumentResolver() {
    return new SimpleOffsetPageableHandlerMethodArgumentResolver();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(simpleOffsetPageableHandlerMethodArgumentResolver());
  }

  private final String baseApiPath = "api";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.png", "/**/*.ttf", "/**/*.woff", "/**/*.woff2", "/manifest.json")
      .setCachePeriod(0)
      .addResourceLocations("classpath:/static/");
    registry
      .addResourceHandler("/", "/**")
      .setCachePeriod(0)
      .addResourceLocations("classpath:/static/index.html")
      .resourceChain(true)
      .addResolver(new PathResourceResolver() {
        @Override
        protected Resource getResource(String resourcePath, Resource location) {
          if (resourcePath.startsWith(baseApiPath) || resourcePath.startsWith(baseApiPath.substring(1))) {
            return null;
          }
          return location.exists() && location.isReadable() ? location : null;
        }
      });
  }

}