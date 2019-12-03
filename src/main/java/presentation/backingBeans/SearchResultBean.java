package presentation.backingBeans;

import data.entities.DepotsEntity;
import data.entities.TripsEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "searchResult")
@SessionScoped
public class SearchResultBean {

    private List<TripsEntity> entities;

    public List<TripsEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<TripsEntity> entities) {
        this.entities = entities;
    }
}
