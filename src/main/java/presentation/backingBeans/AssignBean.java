package presentation.backingBeans;

import ejb.AssignmentBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ManagedBean(name = "assign")
@ViewScoped
public class AssignBean {

    private int driverId;
    private List<Integer> tripIds = new ArrayList<>();

    @EJB
    private AssignmentBean assignmentBean;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public List<Integer> getTripIds() {
        return tripIds;
    }

    public void setTripIds(List<Integer> tripIds) {
        this.tripIds = tripIds;
    }

    public void add() {
        tripIds.add(0);
    }

    public void remove() {
        if (!tripIds.isEmpty()) {
            tripIds.remove(tripIds.size() - 1);
        }
    }

    public String submit() {
        if (new HashSet<>(tripIds).size() < tripIds.size()) {
            return "duplicates?faces-redirect=true";
        }
        assignmentBean.assign(driverId, tripIds);
        return "assignmentSuccesful?faces-redirect=true";
    }
}
