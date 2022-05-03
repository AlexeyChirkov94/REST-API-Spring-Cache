package ru.chirkovprojects.teldatest.service;

import ru.chirkovprojects.teldatest.entity.Region;
import java.util.List;

public interface RegionService {

    void save(Region region);

    List<Region> findAll();

    Region findById(Integer id);

    Region update(Region region);

    void delete(int id);

}
