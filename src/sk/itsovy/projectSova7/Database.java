package sk.itsovy.projectSova7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements carmethods{

    private final String username = "root";
    private final String password = "root";
    private final String host =  "jdbc:mysql://localhost:3306/sova7?useSSL=false";

    private Connection getConnection()
    {
        Connection connection;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
            connection = DriverManager.getConnection(host, username, password);
            return connection;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConnection(Connection conn){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addCar(Car car) {
        try
        {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO cars(brand,color,fuel,spz,price) values(?,?,?,?,?)");
            stmt.setString(1,car.getBrand());
            stmt.setString(2,car.getColor());
            stmt.setString(3,Character.toString(car.getFuel()));
            stmt.setString(4,car.getSpz());
            stmt.setString(5,Integer.toString(car.getPrice()));

            stmt.executeUpdate();
            closeConnection(conn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCarsByPrice(int maxPrice) {
        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try
        {
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement("select * from cars where price<=?");
            st.setString(1, Integer.toString(maxPrice));
            rs = st.executeQuery();
            while (rs.next())
            {
                Car car = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel").charAt(0),rs.getString("spz"),rs.getInt("price"));
                cars.add(car);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByBrand(String brand) {
        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try
        {
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement("select * from cars where brand=?");
            st.setString(1, brand);
            rs = st.executeQuery();
            while (rs.next())
            {
                Car car = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel").charAt(0),rs.getString("spz"),rs.getInt("price"));
                cars.add(car);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByFuel(char fuel) {
        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try
        {
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement("select * from cars where fuel=? ");
            st.setString(1, Character.toString(fuel));
            rs = st.executeQuery();
            while (rs.next())
            {
                Car car = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel").charAt(0),rs.getString("spz"),rs.getInt("price"));
                cars.add(car);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByRegion(String spz) {
        Connection conn = getConnection();

        List <Car> cars = new ArrayList<>();
        try
        {
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement("select * from cars where spz like ?");
            st.setString(1,spz.substring(0,2) + '%');
            rs = st.executeQuery();
            while (rs.next())
            {
                Car car = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel").charAt(0),rs.getString("spz"),rs.getInt("price"));
                cars.add(car);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void changeSPZ(String oldSPZ, String newSPZ) {
        Connection conn = getConnection();
        try
        {
            PreparedStatement st = conn.prepareStatement("UPDATE cars SET spz=? WHERE spz like ?");
            st.setString(1,newSPZ);
            st.setString(2,oldSPZ);

            st.executeUpdate();
            closeConnection(conn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXML() {
        Connection conn = getConnection();
        List <Car> cars = new ArrayList<>();

        try
        {
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement("SELECT * FROM cars");
            rs = st.executeQuery();
            while (rs.next())
            {

                Car car = new Car(rs.getString("brand"),rs.getString("color"),rs.getString("fuel").charAt(0),rs.getString("spz"),Integer.valueOf(rs.getString("price")));
                cars.add(car);
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("cars");
            doc.appendChild(rootElement);

            for (int i = 0; i < cars.size(); i++)
            {
                Element car = doc.createElement("car");
                rootElement.appendChild(car);

                Element brand = doc.createElement("brand");
                brand.appendChild(doc.createTextNode(cars.get(i).getBrand()));
                car.appendChild(brand);

                Element color = doc.createElement("color");
                color.appendChild(doc.createTextNode(cars.get(i).getColor()));
                car.appendChild(color);

                Element fuel = doc.createElement("fuel");
                fuel.appendChild(doc.createTextNode(Character.toString(cars.get(i).getFuel())));
                car.appendChild(fuel);

                Element spz = doc.createElement("spz");
                spz.appendChild(doc.createTextNode(cars.get(i).getSpz()));
                car.appendChild(spz);

                Element price = doc.createElement("price");
                price.appendChild(doc.createTextNode(Integer.toString(cars.get(i).getPrice())));
                car.appendChild(price);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("carXML.xml"));
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
