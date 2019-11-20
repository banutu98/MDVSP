package data.mappers;

import data.dao.models.Customer;
import data.entities.CustomersEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class CustomerMapper {

    public static Customer fromEntityToModel(CustomersEntity entity) {
        Customer customer = new Customer();
        customer.setCustomerId(entity.getCustomerId());
        customer.setName(entity.getName());
        customer.setTrip(TripMapper.fromEntityToModel(entity.getTrip()));
        return customer;
    }

    public static Set<Customer> fromEntitySetToModelSet(Set<CustomersEntity> entities) {
        return entities.stream().map(CustomerMapper::fromEntityToModel).collect(Collectors.toSet());
    }

    public static CustomersEntity fromModelToEntity(Customer customer) {
        CustomersEntity entity = new CustomersEntity();
        entity.setCustomerId(customer.getCustomerId());
        entity.setName(customer.getName());
        entity.setTrip(TripMapper.fromModelToEntity(customer.getTrip()));
        return entity;
    }

    public static Set<CustomersEntity> fromModelSetToEntitySet(Set<Customer> customers) {
        return customers.stream().map(CustomerMapper::fromModelToEntity).collect(Collectors.toSet());
    }
}
