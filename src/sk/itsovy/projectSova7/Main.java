package sk.itsovy.projectSova7;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Database db = new Database();
        Car mycar = new Car("Volkswagen", "black", 'D', "TV321DM", 1600);
        //db.addCar(mycar);

        //List<Car> cars = db.getCarsByPrice(2000);

        List<Car> cars = db.getCarsByBrand("Volkswagen");

        for (int i = 0; i < cars.size(); i++)
        {
            System.out.println(cars.get(i).getBrand());
            System.out.println(cars.get(i).getColor());
        }


        //db.getCarsByBrand("škoda");

        //db.getCarsByFuel('P');

        //db.getCarsByRegion("TV");

        //db.changeSPZ("TV321DM", "KS265RT");

        db.generateXML();
    }
}
