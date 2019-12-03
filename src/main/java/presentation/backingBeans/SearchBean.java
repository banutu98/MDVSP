package presentation.backingBeans;


import data.dao.jpa.TripDAOJpa;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.TripDAO;
import data.entities.AssociationsEntity;
import data.entities.CustomersEntity;
import data.entities.LocationsEntity;
import data.entities.TripsEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ManagedBean(name = "search")
@ViewScoped
public class SearchBean {

    private String[] selectedFilters;
    private String name;
    private double locationX;
    private double locationY;
    private Date time;

    public String[] getSelectedFilters() {
        return selectedFilters;
    }

    public void setSelectedFilters(String[] selectedFilters) {
        this.selectedFilters = selectedFilters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String searchListener() {
        TripDAOJpa tripDAO = new TripDAOJpa();
        EntityManager em = tripDAO.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TripsEntity> criteriaQuery = cb.createQuery(TripsEntity.class);
        Root<TripsEntity> fromTrips = criteriaQuery.from(TripsEntity.class);
        criteriaQuery = criteriaQuery.select(fromTrips);
        for (String filter : selectedFilters) {
            if ("name".equals(filter)) {
                Subquery<CustomersEntity> subquery = criteriaQuery.subquery(CustomersEntity.class);
                Root<CustomersEntity> fromCustomers = subquery.from(CustomersEntity.class);
                subquery.select(fromCustomers).where(cb.equal(fromCustomers.get("name"), name));
                criteriaQuery = criteriaQuery.where(cb.in(fromTrips.get("tripId")).value(subquery));
            } else if ("location".equals(filter)) {
                Subquery<AssociationsEntity> subquery = criteriaQuery.subquery(AssociationsEntity.class);
                Root<AssociationsEntity> fromAssociations = subquery.from(AssociationsEntity.class);
                subquery.select(fromAssociations.get("startLocation")).where(cb.and(cb.equal(fromAssociations.get("startLocation").get("x"), locationX),
                        cb.equal(fromAssociations.get("startLocation").get("y"), locationY)));
                criteriaQuery = criteriaQuery.where(cb.in(fromTrips.get("tripId")).value(subquery));
            } else if ("time".equals(filter)) {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(time);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime localTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                String dbTime = localTime.format(formatter);
                criteriaQuery = criteriaQuery.where(cb.equal(fromTrips.get("startTime"), dbTime));
            }
        }

        TypedQuery<TripsEntity> typedQuery = em.createQuery(criteriaQuery);
        getResultBean().setEntities(typedQuery.getResultList());
        return "searchResult";
    }

    private SearchResultBean getResultBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SearchResultBean) facesContext.getApplication()
                .createValueBinding("#{searchResult}").getValue(facesContext);
    }
}
