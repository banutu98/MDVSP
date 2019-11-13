package data.dao.spec;

import presentation.backingBeans.SessionBean;

import javax.faces.context.FacesContext;

public abstract class BaseDAO {

    protected SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SessionBean) facesContext.getApplication()
                .createValueBinding("#{sessionBean}").getValue(facesContext);
    }
}
