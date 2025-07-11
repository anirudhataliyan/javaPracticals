import java.sql.*;
import java.util.Scanner;

public class PreparedStatementJDBC { 
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/PIET";
        String username = "root";
        String password = "Hk2005Hk@";

        try (Scanner scanner = new Scanner(System.in)) {
            try {
                // Load MySQL JDBC driver
                //Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                System.out.println("Database connected!");

                // Create students table if it doesn't exist
                String createTableQuery = "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100), " +
                        "age INT)";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(createTableQuery);
                System.out.println("Table 'students' is ready.");

                // User input
                System.out.print("Enter name: ");
                String name = scanner.nextLine();

                System.out.print("Enter age: ");
                int age = scanner.nextInt();

                // Insert using PreparedStatement
                String insertQuery = "INSERT INTO students (name, age) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery);
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.executeUpdate();
                System.out.println("Student inserted using PreparedStatement.");

                // Retrieve using ResultSet
                String selectQuery = "SELECT * FROM students";
                ResultSet rs = conn.createStatement().executeQuery(selectQuery);

                System.out.println("\nStudent Records:");
                while (rs.next()) {
                    System.out.printf("ID: %d | Name: %s | Age: %d\n",
                            rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
                }

                // Close resources
                rs.close();
                ps.close();
                stmt.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
