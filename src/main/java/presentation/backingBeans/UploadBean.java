package presentation.backingBeans;

import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jdbc.TripDAOJdbc;
import data.dao.models.Depot;
import data.dao.models.Trip;
import data.dao.spec.DepotDAO;
import data.dao.spec.TripDAO;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "uploadBean")
@ViewScoped
public class UploadBean {

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String goToDepotPage() {
        return "depots";
    }

    public String uploadListener() {

        List<Depot> depotList = new ArrayList<>();
        List<Trip> tripList = new ArrayList<>();
        if(file.getFileName() == null)
            return "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputstream(), StandardCharsets.UTF_8));
            String currentLine = null;
            while (!"-".equals(currentLine)) {
                currentLine = reader.readLine();
                String[] halves = currentLine.split(";");
                if (halves.length != 2) {
                    break;
                }
                depotList.add(new Depot(halves[0], Integer.parseInt(halves[1])));
            }

            while ((currentLine = reader.readLine()) != null) {
                String[] halves = currentLine.split(";");
                int duration = Integer.parseInt(halves[0]);
                int hour = Integer.parseInt(halves[1].split(":")[0]);
                int minutes = Integer.parseInt(halves[1].split(":")[1]);
                tripList.add(new Trip(LocalTime.of(hour, minutes), duration));
            }

            DepotDAO depotDAO = new DepotDAOJdbc();
            TripDAO tripDAO = new TripDAOJdbc();

            for (Depot depot : depotList) {
                depotDAO.create(depot);
            }

            for (Trip trip : tripList) {
                tripDAO.create(trip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "schedule";
    }

}
