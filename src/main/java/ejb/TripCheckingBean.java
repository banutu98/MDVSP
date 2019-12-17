package ejb;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;

@Stateless
public class TripCheckingBean {

    @EJB
    private ScheduleStatusBean statusBean;

    public boolean isAvailable(int tripId){
       for(Set<Integer> tripIds : statusBean.getAssignments().values()){
           for(Integer assignedTripId : tripIds){
               if(assignedTripId.equals(tripId)){
                   return false;
               }
           }
       }

       return true;
    }
}
