package ejb;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.TransactionSynchronizationRegistry;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignmentInteceptor {

    @EJB
    private ScheduleStatusBean scheduleStatusBean;

    @EJB
    private TripCheckingBean tripCheckingBean;

    @Resource
    private TransactionSynchronizationRegistry registry;

    @AroundInvoke
    public Object assignToMap(InvocationContext ctx) throws Exception{
        Object result = ctx.proceed();
        if (Status.STATUS_ROLLEDBACK != registry.getTransactionStatus()){
            List<Integer> tripIds = (List)ctx.getParameters()[1];
            Integer driverId = (Integer)ctx.getParameters()[0];
            for (int tripId : tripIds) {
                if (tripCheckingBean.isAvailable(tripId)) {
                    Set<Integer> assignedIds = scheduleStatusBean.getAssignments().getOrDefault(driverId, new HashSet<>());
                    assignedIds.addAll(tripIds);
                    scheduleStatusBean.getAssignments().put(driverId, assignedIds);
                }
            }
        }

        return result;
    }
}
