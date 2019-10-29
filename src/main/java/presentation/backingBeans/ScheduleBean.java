package presentation.backingBeans;

import business.GreedySolver;
import data.dao.models.Depot;
import data.dao.models.Trip;
import javafx.util.Pair;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name="dtScheduleView")
@ViewScoped
public class ScheduleBean {

    public List<Pair<Trip, Depot>> getSchedule() {
        return schedule;
    }

    public ScheduleBean() {
        this.schedule = new GreedySolver().solve();
    }

    public void setSchedule(List<Pair<Trip, Depot>> schedule) {
        this.schedule = schedule;
    }

    private List<Pair<Trip, Depot>> schedule;

}
