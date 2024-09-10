package ru.itq.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itq.productservice.converter.StringToProductStatusConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final StringToProductStatusConverter stringToProductStatusConverter;

    public WebConfig(StringToProductStatusConverter stringToProductStatusConverter) {
        this.stringToProductStatusConverter = stringToProductStatusConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToProductStatusConverter);
    }
}
