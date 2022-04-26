package ru.chirkovprojects.teldatest.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import ru.chirkovprojects.teldatest.entity.Region;
import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RegionRepository {

    @Insert("INSERT into regions(name,abbreviatedName) VALUES(#{name}, #{abbreviatedName})")
    void save(Region region);

    @Select("select * from regions")
    List<Region> findAll();

    @Select("select * from regions where id = #{id}")
    Optional<Region> findById(@Param("id") Integer id);

    @Select("select * from regions where name = #{name}")
    List<Region> findAllByName(@Param("name") String name);

    @Update("UPDATE regions SET name=#{name}, abbreviatedName =#{abbreviatedName} WHERE id =#{id}")
    void update(Region region);

    @Delete("DELETE FROM regions WHERE id =#{id}")
    void delete(int id);

}
