package ru.chirkovprojects.teldatest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.chirkovprojects.teldatest.entity.Region;
import ru.chirkovprojects.teldatest.service.RegionService;
import ru.chirkovprojects.teldatest.service.exception.EntityAlreadyExistException;
import ru.chirkovprojects.teldatest.service.exception.EntityDontExistException;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerTestsContextConfiguration.class})
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    RegionService regionService;

    MockMvc mockMvc;

    GlobalExceptionHandler globalExceptionHandler;
    RegionController regionController;

    @BeforeEach
    void setUp() {
        Mockito.reset(regionService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegionController(regionService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        globalExceptionHandler = webApplicationContext.getBean(GlobalExceptionHandler.class);
        regionController = webApplicationContext.getBean(RegionController.class);
    }

    @Test
    void addUserShouldShowExceptionMessageIfRegionServiceRaiseException() throws Exception {
        Region region = new Region();
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPB");
        Exception exception = new EntityAlreadyExistException("Region with same name already registered");

        doThrow(exception).when(regionService).save(region);

        mockMvc.perform(post("/api/region")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "    \"name\": \"Saint-Peterburg\",\n" +
                        "    \"abbreviatedName\": \"SPB\"" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Region with same name already registered\"}"));
    }

    @Test
    void showUserShouldThrowExceptionMessageIfUserNotExist() throws Exception {
        Exception exception = new EntityDontExistException("There no region with id: 100");

        when(regionService.findById(100)).thenThrow(exception);

        mockMvc.perform(get("/api/region/100"))
                .andExpect(status().is(404))
                .andExpect(content().json("{\"message\": \"There no region with id: 100\"}"));
    }

}
