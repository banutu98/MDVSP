package ejb;

import data.dao.spec.AssignmentDAO;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateful
public class AssignmentBean {

    @EJB
    private TripCheckingBean tripCheckingBean;

    @EJB
    private ScheduleStatusBean scheduleStatusBean;

    @EJB
    private AssignmentDAO assignmentDAO;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void assign(int driverId, List<Integer> tripIds) {
        for (int tripId : tripIds) {
            if (tripCheckingBean.isAvailable(tripId)) {
                assignmentDAO.create(driverId, tripId);
            }
        }

        for (int tripId : tripIds) {
            if (tripCheckingBean.isAvailable(tripId)) {
                Set<Integer> assignedIds = scheduleStatusBean.getAssignments().getOrDefault(driverId, new HashSet<>());
                assignedIds.addAll(tripIds);
                scheduleStatusBean.getAssignments().put(driverId, assignedIds);
            }
        }
    }
}
