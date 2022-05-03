package ru.chirkovprojects.teldatest.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(cacheNames = "regions")
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "region", key="#id")
    public Region findById(Integer id) {

        return regionRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("There no region with id: " + id));
    }

    @Override
    @CachePut(cacheNames = "region", key="#region.getId()")
    @CacheEvict(cacheNames = "regions", allEntries = true)
    public Region update(Region region) {
        regionRepository.update(region);

        return region;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", allEntries = true),
            @CacheEvict(value="region", key="#id") })
    public void delete(int id) {
        regionRepository.delete(id);
    }

}
