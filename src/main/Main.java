package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * This the main class for the application.
 *
 * FUTURE ENHANCEMENT One possible future enhancement for this
 * program involves creating a separate field for a short detailed
 * description for each of the parts and products.
 *
 * RUNTIME ERROR For the delete button in the parts table in the main screen,
 * the wrong error message would generate when no row was selected. Structuring
 * the delete button method into two if statements corrected the issue.
 *
 * Please note: when a search is initiated for the part and product
 * table in the main screen, the tables will not repopulate automatically.
 * To repopulate the tables, please enter an empty search, and press Enter.
 *
 * Javadoc is located the top directory of project.
 */
public class Main extends Application {
    /**
     * This method of the main class sets the stage for the application.
     *
     * @param stage The parameter used for setting the stage.
     */
    @Override

    public void start(Stage stage) throws Exception {

        addTestData();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    /**
     * This method establishes test data for troubleshooting usability.
     */
    private void addTestData() {
        InHouse i = new InHouse(1, "Wheel", 29.99, 5, 1, 10, 37);
        Inventory.addPart(i);

        OutSourced o = new OutSourced(2, "Seat", 19.99, 15, 1, 20, "Bicycle Company");
        Inventory.addPart(o);

        Product p = new Product(2, "Dirt Bike", 99.99, 2, 1, 3);
        Inventory.addProduct(p);

        Product q = new Product(21, "City Bike", 199.99, 2, 1, 5);
        Inventory.addProduct(q);
    }

    /**
     * This is where the initial launching of the application occurs.
     *
     * @param args standard string array used in main method.
     */
    public static void main(String[] args) {

        launch(args);
    }
}
