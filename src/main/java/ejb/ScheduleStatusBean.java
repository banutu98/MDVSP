package ejb;


import data.dao.spec.AssignmentDAO;
import data.entities.AssignmentsEntity;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Singleton
public class ScheduleStatusBean {

    @EJB
    private AssignmentDAO assignmentDAO;

    private Map<Integer, Set<Integer>> assignments = new HashMap<>();

    public Map<Integer, Set<Integer>> getAssignments() {
        return assignments;
    }

    private static final String DIR_PATH = "C:\\Users\\tatu georgian\\Desktop\\MDVSP\\src\\main\\resources\\pages";

    @Schedule(hour = "*", minute = "*", second = "*/10")
    public void createPage(){
        Date now = new Date();
        String currentTime = now.toString().replace(":", "-");
        String fileName = currentTime + ".html";
        Path path = Paths.get(DIR_PATH, fileName);
        try {
            Files.createFile(path);
            String content = new JSONObject(assignments).toString(2);
            Files.write(path, content.getBytes());
        }catch (IOException e){
            System.out.println("Could not create file");
        }
    }

    public void init(){
        for(AssignmentsEntity entity : assignmentDAO.findAll()){
            Set<Integer> assignedTripIds = assignments.getOrDefault(entity.getDriverId(), new HashSet<>());
            assignedTripIds.add(entity.getTripId());
            assignments.put(entity.getDriverId(), assignedTripIds);
        }
    }
}
