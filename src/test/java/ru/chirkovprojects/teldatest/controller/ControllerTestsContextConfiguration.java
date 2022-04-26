package ru.chirkovprojects.teldatest.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.chirkovprojects.teldatest.service.RegionService;

@Configuration
@ComponentScan(basePackages = "ru.chirkovprojects.teldatest.controller")
public class ControllerTestsContextConfiguration {

    @Bean
    public RegionService regionService(){
        return Mockito.mock(RegionService.class);
    }

}
