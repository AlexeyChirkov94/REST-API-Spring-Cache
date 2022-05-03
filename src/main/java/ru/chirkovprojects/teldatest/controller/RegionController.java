package ru.chirkovprojects.teldatest.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chirkovprojects.teldatest.entity.Region;
import ru.chirkovprojects.teldatest.service.RegionService;
import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping()
    public List<Region> getAll() {
        return regionService.findAll();
    }

    @GetMapping("/{id}")
    public Region getOne(@PathVariable("id") int id) {
        return regionService.findById(id);
    }

    @PostMapping()
    public void addRegion(@RequestBody Region region) {
        regionService.save(region);
    }

    @PutMapping()
    public Region update(@RequestBody Region region) {
        return regionService.update(region);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        regionService.delete(id);
    }

}
