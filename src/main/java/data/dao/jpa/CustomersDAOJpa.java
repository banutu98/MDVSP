package data.dao.jpa;

import data.dao.models.Customer;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.CustomersDAO;
import data.entities.CustomersEntity;
import data.mappers.CustomerMapper;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAOJpa extends BaseDAOJpa implements CustomersDAO {

    @Override
    public void create(Customer customer) {
        persistToDatabase(customer);
    }

    private void persistToDatabase(Customer customer) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(CustomerMapper.fromModelToEntity(customer));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Customer read(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        CustomersEntity customersEntity = em.find(CustomersEntity.class, id);
        Customer customer = CustomerMapper.fromEntityToModel(customersEntity);
        em.getTransaction().commit();
        em.close();
        return customer;
    }

    @Override
    public void update(Customer customer) {
        persistToDatabase(customer);
    }

    @Override
    public void delete(Customer customer) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(CustomersEntity.class, customer.getCustomerId()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Customer> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<CustomersEntity> query = em.createQuery("SELECT d from CustomersEntity d", CustomersEntity.class);
        List<CustomersEntity> customersEntity = query.getResultList();
        List<Customer> customers = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (CustomersEntity e : customersEntity) {
            Customer customer = new Customer();
            mapper.map(e, customer);
            customers.add(customer);
        }
        em.getTransaction().commit();
        em.close();
        return customers;
    }

    public CustomersDAOJpa(String persistenceUnit) {
        super(persistenceUnit);
    }

    public CustomersDAOJpa() {
        super();
    }
}
