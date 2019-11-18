package data.mappers;

import data.dao.models.Trip;
import data.entities.TripsEntity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public final class TripMapper {

    public static Trip fromEntityToModel(TripsEntity entity) {
        Trip trip = new Trip();
        trip.setId(entity.getTripId());
        trip.setDuration(entity.getDuration());
        int hour = Integer.parseInt(entity.getStartTime().split(":")[0]);
        int minutes = Integer.parseInt(entity.getStartTime().split(":")[1]);
        trip.setStartTime(LocalTime.of(hour, minutes));
        trip.setEndTime(trip.getStartTime().plusMinutes(entity.getDuration()));
        return trip;
    }

    public static TripsEntity fromModelToEntity(Trip trip) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TripsEntity entity = new TripsEntity();
        entity.setTripId(trip.getId());
        entity.setDuration(trip.getDuration());
        entity.setStartTime(trip.getStartTime().format(formatter));
        return entity;
    }

    public static Set<Trip> fromEntitySetToModelSet(Set<TripsEntity> entities) {
        return entities.stream().map(TripMapper::fromEntityToModel).collect(Collectors.toSet());
    }

    public static Set<TripsEntity> fromModelSetToEntitySet(Set<Trip> trips) {
        return trips.stream().map(TripMapper::fromModelToEntity).collect(Collectors.toSet());
    }
}
