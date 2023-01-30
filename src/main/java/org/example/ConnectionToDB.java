package org.example;

import java.sql.*;

import static org.example.AllCities.cities;
import static org.example.AllCities.textElement;
import static org.example.LoginAndPassword.*;

public class ConnectionToDB {

    public static void main(String[] args) throws SQLException {
        createAndDeleteDB();
        connectToDB();
        putToDB();

    }

    public static void connectToDB() throws SQLException {
        try {
            DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("OK. DB WORKING!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void putToDB() throws SQLException {
        try {
            String city = "New York";
            for (int i = 0; i < cities.length; i++) {
                city = cities[i];

            }
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("insert into visacenter (city,message,date,time) values ('" + city + "','" + textElement + "',current_date(),current_timestamp())");
            System.out.println("Add to DB successfully" + "\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //delete table if she has id 490 and after create a new table with same name
    public static void createAndDeleteDB() {
        String sqlDeleteTable = "DROP TABLE visacenter";
        String sqlCreateTable = "CREATE TABLE visacenter(id INT auto_increment primary key, date DATE not null,time TIME not null, city VARCHAR(15) not null, message VARCHAR(80) not null)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();

            if (checkIdExists(connection, 400)) {
                statement.executeUpdate(sqlDeleteTable);
                System.out.println("DELETE DB");
                statement.executeUpdate(sqlCreateTable);
                System.out.println("CREATE A NEW DB WITH SANE NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static boolean checkIdExists(Connection connection, int id) {
        String sql = "SELECT 1 FROM visacenter WHERE id = " + id;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}


