package sk.itsovy.projectSova7;

public class Car {
    private String brand;
    private String color;
    private char fuel;
    private String spz;
    private int price;

    public Car(String brand, String color, char fuel, String spz, int price) {
        this.brand = brand;
        this.color = color;
        this.fuel = fuel;
        this.spz = spz;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public char getFuel() {
        return fuel;
    }

    public void setFuel(char fuel) {
        this.fuel = fuel;
    }

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
