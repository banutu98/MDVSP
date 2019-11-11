package data.dao.spec;

import data.dao.models.User;

public interface UserDAO {

    void create(User user);

    User findByName(String name);
}
