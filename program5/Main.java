import java.sql.*;
import java.util.Scanner;
public class Main {
   // private static final String URL = "jdbc:mysql://103.167.127.37:3306/shopping_cart?useSSL=false&serverTimezone=UTC";
    //private static final String USER = "rootr"; 
    //private static final String PASSWORD = "rootr";
      private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";  
    private static final String PASSWORD = "root"; 
   
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
System.out.println("1. Add Product");
System.out.println("2. View Products");
System.out.println("3. Update Product");
System.out.println("4. Delete Product");
System.out.println("5. Exit");
System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
addProduct(connection, scanner);
                        break;
                    case 2:
viewProducts(connection);
                        break;
                    case 3:
updateProduct(connection, scanner);
                        break;
                    case 4:
deleteProduct(connection, scanner);
                        break;
                    case 5:
System.out.println("Exiting...");
                        break;
                    default:
System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
        } catch (SQLException e) {
e.printStackTrace();
        }
    }
    private static void addProduct(Connection connection, Scanner scanner) throws SQLException {
System.out.print("Enter product name: ");
        String name = scanner.nextLine();
System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
statement.setString(1, name);
statement.setDouble(2, price);
statement.setInt(3, quantity);
statement.executeUpdate();
System.out.println("Product added successfully.");
        }
    }
    private static void viewProducts(Connection connection) throws SQLException {
        String sql = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(sql)) {
System.out.println("Products in the Shopping Cart:");
            while (resultSet.next()) {
System.out.println("ID: " + resultSet.getInt("id") +
                                   ", Name: " + resultSet.getString("name") +
                                   ", Price: " + resultSet.getDouble("price") +
                                   ", Quantity: " + resultSet.getInt("quantity"));
            }
        }
    }
    private static void updateProduct(Connection connection, Scanner scanner) throws SQLException {
System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
scanner.nextLine(); // Consume newline
System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
System.out.print("Enter new product quantity: ");
        int quantity = scanner.nextInt();
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
statement.setString(1, name);
statement.setDouble(2, price);
statement.setInt(3, quantity);
statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated> 0) {
System.out.println("Product updated successfully.");
            } else {
System.out.println("Product not found.");
            }
        }
    }
    private static void deleteProduct(Connection connection, Scanner scanner) throws SQLException {
System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted> 0) {
System.out.println("Product deleted successfully.");
            } else {
System.out.println("Product not found.");
            }
        }
    }
}
