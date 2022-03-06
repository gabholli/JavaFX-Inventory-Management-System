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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class for modify product screen
 */
public class ModifyProduct implements Initializable {

    /**
     * Product variable for selected product
     */
    Product selectedProduct;

    /**
     * Text field for product ID
     */
    public TextField modifyProductIdField;
    /**
     * Text field for product name
     */
    public TextField modifyProductNameField;
    /**
     * Text field for product stock amount
     */
    public TextField modifyProductInvField;
    /**
     * Text field for product price
     */
    public TextField modifyProductPriceField;
    /**
     * Text field for product maximum amount
     */
    public TextField modifyProductMaxField;
    /**
     * Text field for product minimum amount
     */
    public TextField modifyProductMinField;
    /**
     * Text field for part table search field
     */
    public TextField modifyProductSearchField;
    /**
     * Table view for part table in modify product screen
     */
    public TableView<Part> modifyProductTopTable;
    /**
     * Table view for associated part table in modify product screen
     */
    public TableView<Part> modifyProductBottomTable;
    /**
     * Button for removing associated part from associated parts table
     */
    public Button modifyProductRemoveButton;
    /**
     * Button for saving product
     */
    public Button modifyProductSaveButton;
    /**
     * Button for canceling product
     */
    public Button modifyProductCancelButton;
    /**
     * Button for adding part to associated parts table
     */
    public Button modifyProductAddButton;
    /**
     * Column for associated part price in associated part table
     */
    public TableColumn<Object, Object> bottomPriceCol;
    /**
     * Column for associated part stock amount in associated part table
     */
    public TableColumn<Object, Object> bottomInvCol;
    /**
     * Column for associated part name in associated part table
     */
    public TableColumn<Object, Object> bottomNameCol;
    /**
     * Column for associated part ID in associated part table
     */
    public TableColumn<Object, Object> bottomIdCol;
    /**
     * Column for part price in parts table
     */
    public TableColumn<Object, Object> topPriceCol;
    /**
     * Column for part stock amount in parts table
     */
    public TableColumn<Object, Object> topInvCol;
    /**
     * Column for part name in parts table
     */
    public TableColumn<Object, Object> topNameCol;
    /**
     * Column for part ID in parts table
     */
    public TableColumn<Object, Object> topIdCol;
    /**
     * Anchor pane for modify parts screen
     */
    public AnchorPane BottomIdCol;

    /**
     * Method for handling initialization of modify product screen
     * @param url variable for url
     * @param resourceBundle variable for resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductIdField.setDisable(true);

        modifyProductTopTable.setItems(Inventory.getAllParts());

        topIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        selectedProduct = new Product(0, null, 0.0, 0, 0, 0);

        bottomIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Method for receiving data from main screen
     * @param product variable for handling selected product
     */
    public void receiveDataToModifyProduct(Product product) {

        selectedProduct = product;
        try {
            modifyProductIdField.setText(Integer.toString(selectedProduct.getId()));
            modifyProductNameField.setText(selectedProduct.getName());
            modifyProductInvField.setText(Integer.toString(selectedProduct.getStock()));
            modifyProductPriceField.setText(Double.toString(selectedProduct.getPrice()));
            modifyProductMaxField.setText(Integer.toString(selectedProduct.getMax()));
            modifyProductMinField.setText(Integer.toString(selectedProduct.getMin()));
            modifyProductBottomTable.setItems(selectedProduct.getAllAssociatedParts());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("No product has been selected.");

            alert.showAndWait();
            System.out.println("No values selected");
        }


    }

    /**
     * Method for handling cancel button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void modifyProductCancelButtonAction(ActionEvent actionEvent) throws IOException {
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
     * Method for handling add button action
     */
    public void modifyProductAddButtonAction() {
        Part part = modifyProductTopTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            selectedProduct.addAssociatedPart(part);
            modifyProductBottomTable.setItems(selectedProduct.getAllAssociatedParts());
            System.out.println("Test");
        }
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing Selected");
            alert.setHeaderText("Nothing Was Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    public void modifyProductSearchFieldAction() {
        String s = modifyProductSearchField.getText();

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

        modifyProductTopTable.setItems(partName);
        modifyProductSearchField.setText(Integer.toString(partName.size()));
        modifyProductSearchField.setText((""));
    }

    /**
     * Method for handling remove button action
     */
    public void removeButtonAction() {
        Part part = modifyProductBottomTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("Please Confirm Removal");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedProduct.deleteAssociatedPart(part);
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

    /**
     * Method for handling save button action
     * @param actionEvent
     * @throws IOException
     */
    public void modifyProductSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            selectedProduct.setId(Integer.parseInt(modifyProductIdField.getText()));
            selectedProduct.setName((modifyProductNameField.getText()));
            selectedProduct.setStock(Integer.parseInt(modifyProductInvField.getText()));
            selectedProduct.setPrice(Double.parseDouble(modifyProductPriceField.getText()));
            selectedProduct.setMax(Integer.parseInt(modifyProductMaxField.getText()));
            selectedProduct.setMin(Integer.parseInt(modifyProductMinField.getText()));

            if (selectedProduct.getStock() >= selectedProduct.getMin() && selectedProduct.getStock() <= selectedProduct.getMax()) {
                Inventory.updateProduct(Integer.parseInt(modifyProductIdField.getText()), selectedProduct);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Please Confirm Saving Product");
                alert.setHeaderText("Do You Want To Save Product?");
                alert.setContentText("Please confirm.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 400);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            if (selectedProduct.getMin() > selectedProduct.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Minimum Greater Than Maximum");
                alert.setHeaderText("Min/Max Error Message");
                alert.setContentText("Minimum cannot be greater than maximum.");

                alert.showAndWait();
            }

            if (selectedProduct.getStock() < selectedProduct.getMin() || selectedProduct.getStock() > selectedProduct.getMax()) {
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
}