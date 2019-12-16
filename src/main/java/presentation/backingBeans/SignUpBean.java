package presentation.backingBeans;

import data.dao.jpa.ResourceProducer;
import data.dao.jpa.UserDAOJpa;
import data.dao.models.User;
import data.dao.spec.SchemaManagerDAO;
import data.dao.spec.UserDAO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

@ManagedBean(name = "signup")
@ViewScoped
public class SignUpBean {

    private String name;
    private String firstPass;
    private String secondPass;

    @EJB
    private SchemaManagerDAO schemaManagerDAO;

    @EJB
    private UserDAO userDAO;

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
            User user = userDAO.findByName(name);
            if (name.equals(user.getName())) {
                return "userExists";
            }
            userDAO.create(new User(name, firstPass));
            schemaManagerDAO.createSchema(name);
            return "login?faces-redirect=true";
        }

        return "invalidPassword?faces-redirect=true";
    }

    private SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SessionBean) facesContext.getApplication()
                .createValueBinding("#{sessionBean}").getValue(facesContext);
    }

    public SignUpBean(){
        getSessionBean().setUserName("default_schema");
    }
}
