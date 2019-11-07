package data.dao.models;

public class Location {

    private int id;

    private double x;

    private double y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
