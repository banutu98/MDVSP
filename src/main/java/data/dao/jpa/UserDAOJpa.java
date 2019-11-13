package data.dao.jpa;

import data.dao.models.User;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.UserDAO;
import data.entities.UsersEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;

public class UserDAOJpa extends BaseDAOJpa implements UserDAO {
    @Override
    public void create(User user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ModelMapper mapper = new ModelMapper();
        UsersEntity usersEntity = new UsersEntity();
        mapper.map(user, usersEntity);
        em.persist(usersEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public User findByName(String name) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        UsersEntity usersEntity = em.find(UsersEntity.class, name);
        ModelMapper mapper = new ModelMapper();
        User user = new User();
        mapper.map(user, usersEntity);
        em.close();
        return user;
    }
}
