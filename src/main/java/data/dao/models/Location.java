package data.dao.models;

public class Location {

    private int locationId;

    private double x;

    private double y;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Location(){

    }

    public Location(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}
