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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class for main screen
 */
public class MainScreen implements Initializable {

    /**
     * The product table in the main screen
     */
    public TableView<Product> productMainTable;
    /**
     * The part table in the main screen
     */
    public TableView<Part> partMainTable;
    /**
     * The button that deletes a part
     */
    public Button deleteMainPartButton;
    /**
     * Text field for searching for a part
     */
    public TextField partSearchField;
    /**
     * Text field for searching for a product
     */
    public TextField productSearchField;
    /**
     * Button for adding a part
     */
    public Button addMainPartButton;
    /**
     * Button for modifying a part
     */
    public Button modifyMainPartButton;
    /**
     * Button for deleting a product
     */
    public Button deleteMainProductButton;
    /**
     * Button for adding a product
     */
    public Button addMainProductButton;
    /**
     * Button for modifying a product
     */
    public Button modifyMainProductButton;
    /**
     * Button for exiting the program
     */
    public Button exitMainButton;
    /**
     * Column for part ID
     */
    public TableColumn<Part, Integer> leftTablePartIdCol;
    /**
     * Column for part name
     */
    public TableColumn<Part, String> leftTablePartNameCol;
    /**
     * Colunn for part stock amount
     */
    public TableColumn<Part, Integer> leftTableInvCol;
    /**
     * Column for part price
     */
    public TableColumn<Part, Double> leftTablePriceCol;
    /**
     * Colunn for product ID
     */
    public TableColumn<Product, Integer> rightTableProductIdCol;
    /**
     * Column for product name
     */
    public TableColumn<Product, String> rightTableProductNameCol;
    /**
     * Column for product stock amount
     */
    public TableColumn<Product, Integer> rightTableInvCol;
    /**
     * Colunn for product price
     */
    public TableColumn<Product, Double> rightTablePriceCol;

    /**
     * Method for initializing main screen
     * @param url url variable, never used
     * @param resourceBundle resourceBundle variable, never used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partMainTable.setItems(Inventory.getAllParts());

        leftTablePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        leftTablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        leftTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        leftTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productMainTable.setItems(Inventory.getAllProducts());

        rightTableProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rightTableProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        rightTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        rightTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Method for handling add part button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void addPartButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void modifyPartButtonAction(ActionEvent actionEvent) throws IOException {
        Part part = partMainTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((getClass().getResource("/View/ModifyPart.fxml")));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            ModifyPart controller = loader.getController();
            controller.sendDataToModifyPart(partMainTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(tableViewScene);
            stage.show();
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
     * Method for handling delete product button action
     */
    public void deleteProductButtonAction() {
        Product product = productMainTable.getSelectionModel().getSelectedItem();


        if (product != null && product.getAllAssociatedParts().isEmpty()) {
            System.out.println("No associated parts");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Please confirm deletion.");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                productMainTable.getItems().removeAll(productMainTable.getSelectionModel().getSelectedItem());
            }
        }

        if (product != null && !product.getAllAssociatedParts().isEmpty()) {
            System.out.println("Has associated parts");
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Cannot Delete Product");
            alert3.setHeaderText("Product Cannot Have Associated Parts");
            alert3.setContentText("Cannot delete product.");
            alert3.showAndWait();

        }
        if (product == null) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Nothing Selected");
            alert2.setHeaderText("Deletion Did Not Occur");
            alert2.setContentText("Nothing Was Selected.");
            alert2.showAndWait();
        }

    }

    /**
     * Method for handling add product button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void addProductButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for handling modify product button action
     * @param actionEvent variable for handling action event
     * @throws IOException for error handling
     */
    public void modifyProductButtonAction(ActionEvent actionEvent) throws IOException {
        Product product = productMainTable.getSelectionModel().getSelectedItem();

        if (product != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((getClass().getResource("/View/ModifyProduct.fxml")));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            ModifyProduct controller = loader.getController();
            controller.receiveDataToModifyProduct(productMainTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(tableViewScene);
            stage.show();
        }
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing Selected");
            alert.setHeaderText("Nothing Was Selected");
            alert.setContentText("Please select a product.");
            alert.showAndWait();
        }
    }

    /**
     * Method for handling delete part button action
     */
    public void deletePartButtonAction() {
        Part part = partMainTable.getSelectionModel().getSelectedItem();
        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Please confirm deletion.");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                partMainTable.getItems().removeAll(partMainTable.getSelectionModel().getSelectedItem());

            }
        }
        if (part == null) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Nothing Selected");
            alert2.setHeaderText("Deletion Did Not Occur");
            alert2.setContentText("Nothing Was Selected.");
            alert2.showAndWait();
        }

    }

    /**
     * Method for handling exit button action
     */
    public void exitMainButtonAction() {
        Stage stage = (Stage) exitMainButton.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        alert.getDialogPane().setContentText("Are you sure you want to exit?");

        alert.getDialogPane().setHeaderText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    /**
     * Method for handling part search field
     */
    public void partSearchFieldAction() {

        String q = partSearchField.getText();

        ObservableList<Part> partName = Inventory.lookupPart(q);
        if (partName.size() == 0) {
            try {
                int id = Integer.parseInt(q);
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
        partMainTable.setItems(partName);
        partSearchField.setText(Integer.toString(partName.size()));
        partSearchField.setText((""));

    }

    /**
     * Method for handling product search field
     */
    public void productSearchFieldAction() {
        String s = productSearchField.getText();

        ObservableList<Product> productName = Inventory.lookupProduct(s);
        if (productName.size() == 0) {
            try {
                int id = Integer.parseInt(s);
                Product p = Inventory.lookupProduct(id);
                if (p != null) {
                    productName.add(p);
                }
                if (p == null) {
                    Inventory.failedSearchPrompt();
                }
            } catch (NumberFormatException e) {
                Inventory.failedSearchPrompt();
            }

        }

        productMainTable.setItems(productName);
        productSearchField.setText(Integer.toString(productName.size()));
        productSearchField.setText((""));
    }

}






