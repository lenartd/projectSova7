package sk.itsovy.projectSova7;

import java.util.List;

public interface carmethods {

    public void addCar( Car car);

    public List<Car> getCarsByPrice(int maxPrice);

    public List<Car> getCarsByBrand(String brand);

    public List<Car> getCarsByFuel(char fuel);

    public List<Car> getCarsByRegion(String spz);

    public void changeSPZ(String oldSPZ, String newSPZ);

    public void generateXML();

}