package presentation.backingBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "menu")
@SessionScoped
public class MenuBean {

    public String goToDataView() {
        return "dataView";
    }

    public String goToDataEdit() {
        return "dataEdit";
    }

    public String goToSchedule() {
        return "schedule";
    }
}
