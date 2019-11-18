package data.generators;

import com.github.javafaker.Faker;
import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jdbc.TripDAOJdbc;
import data.dao.jpa.DepotDAOJpa;
import data.dao.jpa.TripDAOJpa;
import data.dao.models.Depot;
import data.dao.models.Trip;
import data.dao.spec.DepotDAO;
import data.dao.spec.TripDAO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private static Faker faker = new Faker();

    private static DepotDAO depotDAO = new DepotDAOJpa();

    private static TripDAO tripDAO = new TripDAOJpa();

    private static final String DATA_DIR_PATH = "C:\\Users\\tatu georgian\\Desktop\\MDVSP\\src\\main\\resources\\generateddata\\";

    private static final String SEPARATOR = ";";

    private static List<Depot> generateDepots(int depotCount) {
        List<Depot> result = new ArrayList<>();

        for (int i = 0; i < depotCount; i++) {
            result.add(new Depot(faker.company().name(), faker.number().numberBetween(10, 100)));
        }

        return result;
    }

    private static List<Trip> generateTrips(int tripCount) {
        List<Trip> result = new ArrayList<>();
        final double longTripProb = 0.2;
        LocalTime currentTime = LocalTime.MIDNIGHT;
        final int MINUTES_IN_A_DAY = 60 * 24;

        for (int i = 0; i < tripCount; i++) {
            int duration;
            if (faker.number().randomDouble(5, 0, 1) < longTripProb) {
                duration = faker.number().numberBetween(60, 180);
            } else {
                duration = faker.number().numberBetween(5, 60);
            }
            int minutesToAdd = faker.number()
                    .numberBetween(MINUTES_IN_A_DAY / tripCount, MINUTES_IN_A_DAY / tripCount + 30);
            LocalTime startTime = currentTime.plusMinutes(minutesToAdd);
            LocalTime endTime = startTime.plusMinutes(duration);
            currentTime = currentTime.plusMinutes(minutesToAdd);
            result.add(new Trip(startTime, endTime, duration));
        }

        return result;
    }

    public static void writeToDatabase(int depotCount, int tripCount) {
        List<Depot> depots = generateDepots(depotCount);
        List<Trip> trips = generateTrips(tripCount);
        for (Depot depot : depots) {
            depotDAO.create(depot);
        }
        for (Trip trip : trips) {
            tripDAO.create(trip);
        }
    }

    public static void writeToFile(int depotCount, int tripCount, String file) {
        List<Depot> depots = generateDepots(depotCount);
        List<Trip> trips = generateTrips(tripCount);
        Path filePath = Paths.get(DATA_DIR_PATH, file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            Files.write(filePath, "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (Depot depot : depots) {
                Files.write(filePath, (depot.getName() + SEPARATOR + depot.getCapacity() + "\n")
                        .getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            Files.write(filePath, "-\n".getBytes(), StandardOpenOption.APPEND);
            for (Trip trip : trips) {
                Files.write(filePath, (trip.getDuration() + SEPARATOR + trip.getStartTime().format(formatter) + "\n")
                        .getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //writeToFile(5, 100, "test2.txt");
        writeToDatabase(5, 100);
    }
}
