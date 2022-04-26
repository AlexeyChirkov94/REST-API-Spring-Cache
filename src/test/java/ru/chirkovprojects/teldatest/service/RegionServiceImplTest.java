package ru.chirkovprojects.teldatest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.chirkovprojects.teldatest.entity.Region;
import ru.chirkovprojects.teldatest.repository.RegionRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    RegionRepository regionRepository;

    @InjectMocks
    RegionServiceImpl regionService;

    @Test
    void createShouldAddRegionToDBIfArgumentIsRegion() {
        Region region = new Region();
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");

        when(regionRepository.findAllByName(region.getName())).thenReturn(Collections.emptyList());
        doNothing().when(regionRepository).save(region);

        regionService.save(region);

        verify(regionRepository).findAllByName(region.getName());
        verify(regionRepository).save(region);
    }

    @Test
    void createShouldTrowExceptionIfRegionWithSameNameAlreadyExist() {
        Region region = new Region();
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");

        when(regionRepository.findAllByName(region.getName())).thenReturn(Collections.singletonList(region));

        assertThatThrownBy(() -> regionService.save(region)).hasMessage("Region with same name already registered");

        verify(regionRepository).findAllByName(region.getName());
    }

    @Test
    void findAllShouldReturnListOfRegionsNoArguments() {
        Region region = new Region();
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");
        List<Region> regions = Collections.singletonList(region);

        when(regionRepository.findAll()).thenReturn(regions);

        regionService.findAll();

        verify(regionRepository).findAll();
    }

    @Test
    void findByIdShouldReturnRegionFromDBIfArgumentsIsRegionId() {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");

        when(regionRepository.findById(1)).thenReturn(Optional.of(region));

        regionService.findById(1);

        verify(regionRepository).findById(1);
    }

    @Test
    void findByIdShouldThrowExceptionIfNoUserWithWantedIdInDB() {
        when(regionRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionService.findById(100)).hasMessage("There no region with id: 100");

        verify(regionRepository).findById(100);
    }

    @Test
    void updateShouldUpdateRegionIfArgumentIsRegion() {
        Region region = new Region();
        region.setId(1);
        region.setName("Saint-Peterburg");
        region.setAbbreviatedName("SPb");

        doNothing().when(regionRepository).update(region);

        regionService.update(region);

        verify(regionRepository).update(region);
    }

    @Test
    void deleteShouldDeleteRegionIfArgumentIsRegionId() {
        doNothing().when(regionRepository).delete(1);

        regionService.delete(1);

        verify(regionRepository).delete(1);
    }

}