package com.example.flyPJ.Config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfiguration {
    @Bean
    public ModelMapper initModelMapper() {
        return new ModelMapper();
    }
}

