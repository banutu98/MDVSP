package data.mappers;

import data.dao.models.Driver;
import data.entities.DriversEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class DriverMapper {

    public static DriversEntity fromModelToEntity(Driver driver) {
        DriversEntity entity = new DriversEntity();

        entity.setName(driver.getName());
        entity.setCarModel(driver.getCarModel());
        entity.setDriverId(driver.getDriverId());
        entity.setAssignedCustomers(CustomerMapper.fromModelSetToEntitySet(driver.getAssignedCustomers()));

        return entity;
    }

    public static Set<DriversEntity> fromModelSetToEntitySet(Set<Driver> drivers) {
        return drivers.stream().map(DriverMapper::fromModelToEntity).collect(Collectors.toSet());
    }

    public static Driver fromEntityToModel(DriversEntity entity) {
        Driver driver = new Driver();
        driver.setDriverId(entity.getDriverId());
        driver.setCarModel(entity.getCarModel());
        driver.setName(entity.getName());
        driver.setAssignedCustomers(CustomerMapper.fromEntitySetToModelSet(entity.getAssignedCustomers()));

        return driver;
    }

    public static Set<Driver> fromEntitySetToModelSet(Set<DriversEntity> entities) {
        return entities.stream().map(DriverMapper::fromEntityToModel).collect(Collectors.toSet());
    }


}
