package sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null;
        Statement statement = null;

        Class.forName("org.h2.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test", "", "");
            statement = connection.createStatement();
            statement.execute("DROP TABLE product IF EXISTS");
            statement.execute("DROP TABLE tax IF EXISTS");
            statement.execute("CREATE TABLE product(id INT PRIMARY KEY, name VARCHAR(50), description VARCHAR(150), type VARCHAR(50), price NUMERIC(20,2))");
            statement.execute("INSERT INTO product VALUES(1, '1 Gallon Milk', 'From cows grown with no RBST', 'GROCERY', '4.00')");
            statement.execute("INSERT INTO product VALUES(2, 'MacBook Pro M1 64GB', 'MacBook Pro M1 64GB, 1TB SDD', 'NON-GROCERY', '2500.00')");
            statement.execute("INSERT INTO product VALUES(3, 'Bread', 'Sourdough no sugar added', 'GROCERY', '5.00')");
            statement.execute("CREATE TABLE tax(id INT PRIMARY KEY, location VARCHAR(150), rate NUMERIC(20,2), type VARCHAR(50) )");
            statement.execute("INSERT INTO tax VALUES(1, 'San Jose, CA', '0.00', 'GROCERY')");
            statement.execute("INSERT INTO tax VALUES(2, 'San Jose, CA', '0.09', 'NON-GROCERY')");
//            ResultSet rs = statement.executeQuery("SELECT p.id, p.name, p.price, t.rate, SUM(p.price * t.rate), p.price * t.rate AS taxed " +
//                    "FROM product p " +
//                    "JOIN tax t " +
//                    "ON p.type=t.type " +
//                    "WHERE p.id IN(1,2) AND t.location='San Jose, CA'");
            ResultSet rs = statement.executeQuery("SELECT SUM(price + taxed) as total FROM (SELECT p.id, p.name, p.price, t.rate, p.price * t.rate AS taxed "
                    + "FROM product p "
                    + "JOIN tax t "
                    + "ON p.type=t.type "
                    + "WHERE p.id IN(1,2,3) AND t.location='San Jose, CA') ");

            while (rs.next()) {
                System.out.println("Total (including taxes): " + rs.getString("total"));
//                System.out.println("id-> " + rs.getInt("id")
//                        + ", name-> " + rs.getString("name")
//                        + ", price-> " + rs.getString("price")
//                        + ", taxed-> " + rs.getString("taxed"));
            }
            statement.close();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public int findMissing(int[] input) {
        int  n = input.length;
        int maxSum = (n * (n + 1) / 2);
        int sum = 0;
        for (int i = 1; i < 101; i++) {
            sum += input[i];
        }
        return maxSum - sum;
    }

}

/**
 San Jose, CA
 milk
 macbook
 */


/**

 SELECT * FROM product

 Product
 - id
 - name
 - description
 - type (Grocery, Hot food, non-perishables)
 - price

 Tax
 - id
 - location
 - tax rate
 - type

 Type
 Grocery,
 Hot food,
 non-perishables


 Santa Clara, CA - 0.09 - non-perishable
 Santa Clara, CA - 0.0 - grocery


 */
