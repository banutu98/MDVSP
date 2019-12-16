package data.dao.jpa;

import data.dao.models.Customer;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.CustomersDAO;
import data.entities.CustomersEntity;
import data.mappers.CustomerMapper;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class CustomersDAOJpa extends BaseDAOJpa implements CustomersDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(CustomerMapper.fromModelToEntity(customer));
    }

    @Override
    public Customer read(int id) {
        CustomersEntity customersEntity = em.find(CustomersEntity.class, id);
        return CustomerMapper.fromEntityToModel(customersEntity);
    }

    @Override
    public void update(Customer customer) {
        em.persist(CustomerMapper.fromModelToEntity(customer));
    }

    @Override
    public void delete(Customer customer) {
        em.remove(em.find(CustomersEntity.class, customer.getCustomerId()));
    }

    @Override
    public List<Customer> readAll() {
        TypedQuery<CustomersEntity> query = em.createQuery("SELECT d from CustomersEntity d", CustomersEntity.class);
        List<CustomersEntity> customersEntity = query.getResultList();
        List<Customer> customers = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (CustomersEntity e : customersEntity) {
            Customer customer = new Customer();
            mapper.map(e, customer);
            customers.add(customer);
        }
        return customers;
    }

    public CustomersDAOJpa() {
        super();
    }
}
