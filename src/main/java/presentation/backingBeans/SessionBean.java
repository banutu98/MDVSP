package presentation.backingBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "session")
@SessionScoped
public class SessionBean {

    public static String userName;
}
