package presentation.backingBeans;

import data.dao.jdbc.SchemaMaangerDAOJdbc;
import data.dao.jdbc.UserDAOJdbc;
import data.dao.jpa.UserDAOJpa;
import data.dao.models.User;
import data.dao.spec.SchemaManagerDAO;
import data.dao.spec.UserDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "signup")
@ViewScoped
public class SignUpBean {

    private String name;
    private String firstPass;
    private String secondPass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstPass() {
        return firstPass;
    }

    public void setFirstPass(String firstPass) {
        this.firstPass = firstPass;
    }

    public String getSecondPass() {
        return secondPass;
    }

    public void setSecondPass(String secondPass) {
        this.secondPass = secondPass;
    }

    public String submit() {
        if (firstPass.equals(secondPass)) {
            UserDAO userDAO = getSessionBean().getUserDAO();
            User user = userDAO.findByName(name);
            if (name.equals(user.getName())) {
                return "userExists";
            }
            userDAO.create(new User(name, firstPass));
            SchemaManagerDAO schemaManagerDAO = new SchemaMaangerDAOJdbc();
            schemaManagerDAO.createSchema(name);
            return "login";
        }

        return "invalidPassword";
    }

    private SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SessionBean) facesContext.getApplication()
                .createValueBinding("#{sessionBean}").getValue(facesContext);
    }
}
