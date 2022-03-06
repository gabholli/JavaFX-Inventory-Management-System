package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class for handling add product screen
 */
public class AddProduct implements Initializable {
    /**
     * Product variable used to create an instance of product
     */
    Product productAdd = new Product(0, "name", 0.0, 0, 0, 0);
    /**
     * Anchor pane for add product screen
     */
    public AnchorPane addProductAddButton;
    /**
     * Text field for product ID
     */
    public TextField addProductIdField;
    /**
     * Text field for product name
     */
    public TextField addProductNameField;
    /**
     * Text field for product stock amount
     */
    public TextField addProductInvField;
    /**
     * Text field for product price
     */
    public TextField addProductPriceField;
    /**
     * Text field for product maximum amount
     */
    public TextField addProductMaxField;
    /**
     * Text field for product minimum amount
     */
    public TextField addProductMinField;
    /**
     * Text field for searching for parts to add
     */
    public TextField addProductSearchField;
    /**
     * Table view for list of parts to add
     */
    public TableView<Part> addProductTopTable;
    /**
     * Table view for list of associated parts of product
     */
    public TableView<Part> addProductBottomTable;
    /**
     * Button to remove selected associated part
     */
    public Button addProductRemoveButton;
    /**
     * Button to save product added
     */
    public Button addProductSaveButton;
    /**
     * Column of part ID for parts table
     */
    public TableColumn<Object, Object> topPartCol;
    /**
     * Column of associated part stock for associated parts table
     */
    public TableColumn<Object, Object> bottomInvCol;
    /**
     * Column of associated part price for associated parts table
     */
    public TableColumn<Object, Object> bottomPriceCol;
    /**
     * Column for associated part name for associated parts table
     */
    public TableColumn<Object, Object> bottomNameCol;
    /**
     * Column for associated part ID for associated parts table
     */
    public TableColumn<Object, Object> bottomPartCol;
    /**
     * Column for part price for parts table
     */
    public TableColumn<Object, Object> topPriceCol;
    /**
     * Column for part stock amount for parts table
     */
    public TableColumn<Object, Object> topInvCol;
    /**
     * Column for part name for parts table
     */
    public TableColumn<Object, Object> topNameCol;
    /**
     * Cancel button for add product screen
     */
    public Button addProductCancelButton;
    /**
     * Add button for add product screen
     */
    public Button addProductTableAddButton;

    /**
     * Method used to handle initialization of screen
     * @param url variable for url
     * @param resourceBundle variable for resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Random rand = new Random();
        int id = rand.nextInt(100);
        addProductIdField.setText(String.valueOf(id));

        addProductIdField.setDisable(true);
        addProductIdField.setPromptText("Auto Gen - Disabled");

        addProductTopTable.setItems(Inventory.getAllParts());

        topPartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productAdd = new Product(0, null, 0.0, 0, 0, 0);

        bottomPartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Method for handling cancel button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void addProductCancelButtonAction(ActionEvent actionEvent) throws IOException {
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
     * Method for handling part table search field action
     */
    public void searchTopTableFieldAction() {
        String s = addProductSearchField.getText();


        ObservableList<Part> partName = Inventory.lookupPart(s);
        if (partName.size() == 0) {
            try {
                int id = Integer.parseInt(s);
                Part p = Inventory.lookupPart(id);
                if (p != null) {
                    partName.add(p);
                }
                if (p == null) {
                    Inventory.failedSearchPrompt();
                }
            } catch (NumberFormatException e) {
                Inventory.failedSearchPrompt();
            }
        }

        addProductTopTable.setItems(partName);
        addProductSearchField.setText(Integer.toString(partName.size()));
        addProductSearchField.setText((""));
    }

    /**
     * Method for handling add button action
     */
    public void addProductTableAddButtonAction() {
        Part part = addProductTopTable.getSelectionModel().getSelectedItem();
        if (part != null) {
            productAdd.addAssociatedPart(part);
            addProductBottomTable.setItems(productAdd.getAllAssociatedParts());
        }
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing Selected");
            alert.setHeaderText("Nothing Was Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();

        }
    }

    /**
     * Method for handling save button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void addProductSaveButtonAction(ActionEvent actionEvent) throws IOException {

        try {
            productAdd.setId(Integer.parseInt(addProductIdField.getText()));
            productAdd.setName((addProductNameField.getText()));
            productAdd.setStock(Integer.parseInt(addProductInvField.getText()));
            productAdd.setPrice(Double.parseDouble(addProductPriceField.getText()));
            productAdd.setMax(Integer.parseInt(addProductMaxField.getText()));
            productAdd.setMin(Integer.parseInt(addProductMinField.getText()));

            if (productAdd.getStock() >= productAdd.getMin() & productAdd.getStock() <= productAdd.getMax()) {
                Inventory.updateProduct(Integer.parseInt(addProductIdField.getText()), productAdd);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Please Confirm Saving Product");
                alert.setHeaderText("Do You Want To Save Product?");
                alert.setContentText("Please confirm.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 400);
                    stage.setScene(scene);
                    stage.show();
                }
            }

            if (productAdd.getMin() > productAdd.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Minimum Greater Than Maximum");
                alert.setHeaderText("Min/Max Error Message");
                alert.setContentText("Minimum cannot be greater than maximum.");

                alert.showAndWait();
            }

            if (productAdd.getStock() < productAdd.getMin() || productAdd.getStock() > productAdd.getMax()) {
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
     * Method for remove button action
     */
    public void removeButtonAction() {
        Part part = addProductBottomTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("Please confirm removal.");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                productAdd.deleteAssociatedPart(part);
            }
        }
        if (part == null) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Nothing Selected");
            alert2.setHeaderText("Nothing Was Selected");
            alert2.setContentText("Please select an associated part.");
            alert2.showAndWait();
        }
    }

}
