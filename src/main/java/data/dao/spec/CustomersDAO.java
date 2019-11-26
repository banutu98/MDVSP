package data.dao.spec;

import data.dao.models.Customer;

import java.util.List;

public interface CustomersDAO {

    void create(Customer customer);

    Customer read(int id);

    void update(Customer customer);

    void delete(Customer customer);

    List<Customer> readAll();
}
