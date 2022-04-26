package ru.chirkovprojects.teldatest.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.chirkovprojects.teldatest.entity.Region;
import ru.chirkovprojects.teldatest.repository.RegionRepository;
import ru.chirkovprojects.teldatest.service.exception.EntityAlreadyExistException;
import ru.chirkovprojects.teldatest.service.exception.EntityDontExistException;
import java.util.List;

@Service
 public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public void save(Region region) {
        if (!regionRepository.findAllByName(region.getName()).isEmpty()) {
            throw new EntityAlreadyExistException("Region with same name already registered");
        } else {
            regionRepository.save(region);
        }
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    @Cacheable("regions")
    public Region findById(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("There no region with id: " + id));
    }

    @Override
    public void update(Region region) {
        regionRepository.update(region);
    }

    @Override
    public void delete(int id) {
        regionRepository.delete(id);
    }

}
