package data.dao.jpa;

import data.dao.models.Customer;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.CustomersDAO;
import data.entities.CustomersEntity;
import data.mappers.CustomerMapper;

import javax.persistence.EntityManager;

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
        CustomersEntity customersEntity = em.find(CustomersEntity.class, id);
        Customer customer = CustomerMapper.fromEntityToModel(customersEntity);
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
}
