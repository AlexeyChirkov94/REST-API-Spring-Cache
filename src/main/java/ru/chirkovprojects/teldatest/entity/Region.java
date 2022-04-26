package ru.chirkovprojects.teldatest.entity;

import java.util.Objects;

public class Region {

    private Integer id;
    private String name;
    private String abbreviatedName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(id, region.id) &&
                Objects.equals(name, region.name) &&
                Objects.equals(abbreviatedName, region.abbreviatedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, abbreviatedName);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                '}';
    }

}
