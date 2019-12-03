package data.dao.jpa;

import data.dao.models.User;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.UserDAO;
import data.entities.UsersEntity;
import net.sf.ehcache.CacheManager;
import org.modelmapper.ModelMapper;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


public class UserDAOJpa extends BaseDAOJpa implements UserDAO {
    @Override
    public void create(User user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setName(user.getName());
        usersEntity.setPass(user.getPass());
        em.persist(usersEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public User findByName(String name) {
        EntityManager em = getEntityManager();
        Cache cache = em.getEntityManagerFactory().getCache();
        if(cache.contains(UsersEntity.class, name)){
            System.out.println("User was taken from second-level cache");
        }
        em.getTransaction().begin();
        UsersEntity usersEntity = em.find(UsersEntity.class, name);
        if(usersEntity == null){
            return new User();
        }
        ModelMapper mapper = new ModelMapper();
        User user = new User();
        mapper.map(usersEntity, user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public UserDAOJpa(String persistenceUnit) {
        super(persistenceUnit);
    }

    public UserDAOJpa() {
        super();
    }
}
