package ejb;

import data.dao.spec.AssignmentDAO;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.*;

@Stateful
public class AssignmentBean {

    @EJB
    private TripCheckingBean tripCheckingBean;

    @EJB
    private AssignmentDAO assignmentDAO;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors({AssignmentInteceptor.class})
    public void assign(int driverId, List<Integer> tripIds) {
        for (int tripId : tripIds) {
            if (tripCheckingBean.isAvailable(tripId)) {
                assignmentDAO.create(driverId, tripId);
            }
        }
    }
}
