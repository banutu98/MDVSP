package presentation.backingBeans;

import data.dao.jdbc.DepotDAOJdbc;
import data.dao.models.Depot;
import data.dao.spec.DepotDAO;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "depotsBean")
@ViewScoped
public class DepotsBean {

    private List<Depot> depots;

    public void addListener() {
        depots.add(new Depot());
    }

    public void removeListener(){
        if(!depots.isEmpty()){
            depots.remove(depots.size() - 1);
        }
    }

    public DepotsBean() {
        depots = new ArrayList<>();
        depots.add(new Depot());
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    public String submitListener() {
        DepotDAO depotDAO = new DepotDAOJdbc();
        for (Depot depot : depots) {
            depotDAO.create(depot);
        }

        return "trips";
    }
}
