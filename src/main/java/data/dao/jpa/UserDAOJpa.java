package data.dao.jpa;

import data.dao.models.User;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.UserDAO;
import data.entities.UsersEntity;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.Cache;
import javax.persistence.EntityManager;

@Stateless
@Default
@Interceptors({LoggingInterceptor.class})
public class UserDAOJpa extends BaseDAOJpa implements UserDAO {

    @Inject
    public EntityManager em;

    @Override
    public void create(User user) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setName(user.getName());
        usersEntity.setPass(user.getPass());
        em.persist(usersEntity);
    }

    @Override
    public User findByName(String name) {
        Cache cache = em.getEntityManagerFactory().getCache();
        if(cache.contains(UsersEntity.class, name)){
            System.out.println("User was taken from second-level cache");
        }
        UsersEntity usersEntity = em.find(UsersEntity.class, name);
        if(usersEntity == null){
            return new User();
        }
        ModelMapper mapper = new ModelMapper();
        User user = new User();
        mapper.map(usersEntity, user);
        return user;
    }

    public UserDAOJpa() {
        super();
    }
}
