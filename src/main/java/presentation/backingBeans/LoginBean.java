package presentation.backingBeans;

import data.dao.jdbc.SchemaMaangerDAOJdbc;
import data.dao.jdbc.UserDAOJdbc;
import data.dao.models.User;
import data.dao.spec.SchemaManagerDAO;
import data.dao.spec.UserDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {

    private String name;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String login() {
        UserDAO userDAO = new UserDAOJdbc();

        User user = userDAO.findByName(name);

        if (pass.equals(user.getPassword())) {
            getSessionBean().setUserName(name);
            return "upload";
        }

        return "errorLogin";
    }

    private SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SessionBean) facesContext.getApplication()
                .createValueBinding("#{sessionBean}").getValue(facesContext);
    }

    public String goToSignup() {
        return "signup";
    }
}
