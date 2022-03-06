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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class for modify part screen
 */
public class ModifyPart implements Initializable {

    /**
     * Part variable for selected part
     */
    Part selectedPart;

    /**
     * Radio button for in-house part
     */
    public RadioButton modifyPartInHouseRadio;
    /**
     * Radio button for outsourced part
     */
    public RadioButton modifyPartOutsourcedRadio;
    /**
     * Text field for part ID
     */
    public TextField modifyPartIdField;
    /**
     * Text field for part name
     */
    public TextField modifyPartNameField;
    /**
     * Text field for part stock amount
     */
    public TextField modifyPartInvField;
    /**
     * Text field for part price
     */
    public TextField modifyPartPriceField;
    /**
     * Text field for part maximum amount
     */
    public TextField modifyPartMaxField;
    /**
     * Text field for part machine ID
     */
    public TextField modifyPartMachField;
    /**
     * Button for saving modified part
     */
    public Button modifyPartSaveButton;
    /**
     * Button for canceling modified part
     */
    public Button modifyPartCancelButton;
    /**
     * Text field for part minimum amount
     */
    public TextField modifyPartMinField;
    /**
     * Label of machine ID for machine ID text field
     */
    public Label modifyPartMachIdLabel;
    /**
     * Toggle group for modify part radio buttons
     */
    public ToggleGroup modifyPartToggle;

    /**
     * Method for initializing modify part screen
     * @param url variable for url
     * @param resourceBundle variable for resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartIdField.setDisable(true);
        modifyPartIdField.setText(modifyPartSaveButton.getId());
    }

    /**
     * Method for receiving data from main screen
     * @param part variable for relevant part selected
     */
    public void sendDataToModifyPart(Part part) {


        selectedPart = part;
        try {
            modifyPartIdField.setText(Integer.toString(selectedPart.getId()));
            modifyPartNameField.setText(selectedPart.getName());
            modifyPartInvField.setText(Integer.toString(selectedPart.getStock()));
            modifyPartPriceField.setText(Double.toString(selectedPart.getPrice()));
            modifyPartMaxField.setText(Integer.toString(selectedPart.getMax()));
            modifyPartMinField.setText(Integer.toString(selectedPart.getMin()));

            if (selectedPart instanceof InHouse) {
                modifyPartMachField.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
                modifyPartInHouseRadio.setSelected(true);
            }

            if (selectedPart instanceof OutSourced) {
                modifyPartMachField.setText((((OutSourced) selectedPart).getCompanyName()));
                modifyPartOutsourcedRadio.setSelected(true);
                modifyPartMachIdLabel.setText("Company Name");
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("No part has been selected.");

            alert.showAndWait();
            System.out.println("No values selected");
            modifyPartIdField.setText("Auto Gen - Disabled");

        }

    }

    /**
     * Method for handling modify part save button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void modifyPartSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try {

            int id = Integer.parseInt(modifyPartIdField.getText());
            String name = modifyPartNameField.getText();
            int inv = Integer.parseInt(modifyPartInvField.getText());
            double price = Double.parseDouble(modifyPartPriceField.getText());
            int max = Integer.parseInt(modifyPartMaxField.getText());
            int min = Integer.parseInt(modifyPartMinField.getText());
            int mach = 0;
            boolean selectedInHouse;
            boolean selectedOutSourced;
            String companyName = null;

            selectedInHouse = modifyPartInHouseRadio.isSelected();
            selectedOutSourced = modifyPartOutsourcedRadio.isSelected();

            if (selectedInHouse) {
                mach = Integer.parseInt(modifyPartMachField.getText());

            } else if (selectedOutSourced) {
                companyName = modifyPartMachField.getText();
            }

            if (inv >= min && inv <= max) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Please Confirm Saving Part");
                alert.setHeaderText("Do You Want To Save Part?");
                alert.setContentText("Please confirm.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (selectedInHouse) {
                        Inventory.addPart(new InHouse(id, name, price, inv, min, max, mach));
                        System.out.println(("In-House item modified"));
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 400);
                        stage.setScene(scene);
                        stage.show();
                        Inventory.getAllParts().remove(selectedPart);
                    } else if (selectedOutSourced) {
                        Inventory.addPart(new OutSourced(id, name, price, inv, min, max, companyName));
                        System.out.println(("Outsourced item modified"));
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 400);
                        stage.setScene(scene);
                        stage.show();
                        Inventory.getAllParts().remove(selectedPart);
                    }
                }
            }
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Minimum Greater Than Maximum");
                alert.setHeaderText("Min/Max Error Message");
                alert.setContentText("Minimum cannot be greater than maximum.");

                alert.showAndWait();
            }

            if (inv < min || inv > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Amount Error");
                alert.setHeaderText("Inventory Error Message");
                alert.setContentText("Inventory amount must be between minimum and maximum.");

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
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void modifyPartCancelButtonAction(ActionEvent actionEvent) throws IOException {
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
     * Method for handling selection of outsourced radio button
     */
    public void modifyPartOutsourcedRadioAction() {
        if (modifyPartOutsourcedRadio.isSelected()) {
            modifyPartMachIdLabel.setText("Company Name");
        }
    }

    /**
     * Method for handling selection of outsourced in-house radio button
     */
    public void modifyPartInHouseRadioAction() {
        if (modifyPartInHouseRadio.isSelected()) {
            modifyPartMachIdLabel.setText("Machine ID");
        }
    }
}
