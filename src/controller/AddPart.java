package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class for the add part screen
 */
public class AddPart implements Initializable {
    /**
     * In-House radio button in add part screen
     */
    public RadioButton addPartInHouseRadio;
    /**
     * Outsourced radio button in add part screen
     */
    public RadioButton addPartOutsourcedRadio;
    /**
     * Text field for part id
     */
    public TextField addPartIdField;
    /**
     * Text field for part name
     */
    public TextField addPartNameField;
    /**
     * Text field for part stock amount
     */
    public TextField addPartInvField;
    /**
     * Text field for part price
     */
    public TextField addPartPriceField;
    /**
     * Text field for maximum amount of part
     */
    public TextField addPartMaxField;
    /**
     * Text field for machine ID of a part
     */
    public TextField addPartMachField;
    /**
     * Cancel button in add part screen
     */
    public Button addPartCancelButton;
    /**
     * Save button in add part screen
     */
    public Button addPartSaveButton;
    /**
     * Text field for minimum amount of a part
     */
    public TextField addPartMinField;
    /**
     * Label for machine ID text field in add part screen
     */
    public Label machIdLabel;
    /**
     * Toggle group for the two radio buttons in add part screen
     */
    public ToggleGroup addPart;

    /**
     * Method for initializing add part
     * @param url variable for URL
     * @param resourceBundle variable for resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartIdField.setDisable(true);
        addPartIdField.setPromptText("Auto Gen - Disabled");
    }

    /**
     * Method for handling save button action
     * @param actionEvent variable for handling action event
     * @throws IOException used for error handling
     */
    public void saveAddPartButtonAction(ActionEvent actionEvent) throws IOException {

        try {
            Random rand = new Random();

            int id;
            id = rand.nextInt(100);
            String name = addPartNameField.getText();
            int inv = Integer.parseInt(addPartInvField.getText());
            double price = Double.parseDouble(addPartPriceField.getText());
            int max = Integer.parseInt(addPartMaxField.getText());
            int min = Integer.parseInt(addPartMinField.getText());
            int mach = 0;
            boolean selectedInHouse;
            boolean selectedOutSourced;
            String companyName = null;

            selectedInHouse = addPartInHouseRadio.isSelected();
            selectedOutSourced = addPartOutsourcedRadio.isSelected();

            if (selectedInHouse) {
                mach = Integer.parseInt(addPartMachField.getText());
            } else if (selectedOutSourced) {
                companyName = addPartMachField.getText();
            }

            if (inv <= max && inv >= min) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Please Confirm Saving Part");
                alert.setHeaderText("Do You Want To Save Part?");
                alert.setContentText("Please confirm.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (selectedInHouse) {
                        Inventory.addPart(new InHouse(id, name, price, inv, min, max, mach));
                        System.out.println(("In-House item added"));
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 400);
                        stage.setScene(scene);
                        stage.show();
                    } else if (selectedOutSourced) {
                        Inventory.addPart(new OutSourced(id, name, price, inv, min, max, companyName));
                        System.out.println(("Outsourced item added"));
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 400);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }

            if (inv < min || inv > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Amount Error");
                alert.setHeaderText("Inventory Error Message");
                alert.setContentText("Inventory amount must be between minimum and maximum.");

                alert.showAndWait();
            }

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Minimum Greater Than Maximum");
                alert.setHeaderText("Min/Max Error Message");
                alert.setContentText("Minimum cannot be greater than maximum.");

                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println("Number exception");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText("One Or More Of The Fields Has An Invalid Entry");
            alert.setContentText("Please Input Valid Entry");

            alert.showAndWait();
        }
    }

    /**
     * Method for handling cancel button action
     * @param actionEvent variable for action events
     * @throws IOException for error handling
     */
    public void cancelToMainButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Please Confirm Cancellation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 400);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method for handling selection of InHouse radio button
     */
    public void addPartInHouseRadioAction() {
        if (addPartInHouseRadio.isSelected()) {
            machIdLabel.setText("Machine ID");
        }
    }

    /**
     * Method for handling selection of Outsourced radio button
     */
    public void addPartOutsourcedRadioAction() {
        if (addPartOutsourcedRadio.isSelected()) {
            machIdLabel.setText("Company Name");
        }
    }
}
