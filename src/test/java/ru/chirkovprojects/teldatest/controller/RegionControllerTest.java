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
import java.util.Collections;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerTestsContextConfiguration.class})
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    RegionService regionService;

    MockMvc mockMvc;

    RegionController regionController;

    @BeforeEach
    void setUp() {
        Mockito.reset(regionService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegionController(regionService))
                .build();
        regionController = webApplicationContext.getBean(RegionController.class);
    }

    @Test
    void showAllShouldReturnAllRegions() throws Exception {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");
        when(regionService.findAll()).thenReturn(Collections.singletonList(region));

        mockMvc.perform(get("/api/region"))
                .andExpect(status().is(200))
                .andExpect(content().json("[{\"id\": 1,\"name\":\"Saint-Peterburg\" ,\"abbreviatedName\":\"SPb\"}]"));
    }

    @Test
    void showUserShouldReturnRegion() throws Exception {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");
        when(regionService.findById(1)).thenReturn(region);

        mockMvc.perform(get("/api/region/1"))
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\": 1,\"name\":\"Saint-Peterburg\" ,\"abbreviatedName\":\"SPb\"}"));
    }

    @Test
    void addRegionShouldAddRegion() throws Exception {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");
        doNothing().when(regionService).save(region);

        mockMvc.perform(post("/api/region")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\": 1,\"name\":\"Saint-Peterburg\" ,\"abbreviatedName\":\"SPb\"}"))
                .andExpect(status().is(200));
    }

    @Test
    void updateRegionShouldUpdateRegion() throws Exception {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");
        doNothing().when(regionService).update(region);

        mockMvc.perform(put("/api/region")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\": 1,\"name\":\"Saint-Peterburg\" ,\"abbreviatedName\":\"SPb\"}"))
                .andExpect(status().is(200));
    }

    @Test
    void deleteRegionShouldDeleteRegion() throws Exception {
        doNothing().when(regionService).delete(1);

        mockMvc.perform(delete("/api/region/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

}