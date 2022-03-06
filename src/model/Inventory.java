package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Objects;

/**
 * Class to handle data of inventory system of parts
 */
public class Inventory {
    /**
     * Observable list of parts
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Observable list of products
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method for adding parts
     * @param newPart part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Method for adding products
     * @param newProduct product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Method for looking up a part
     * @param partId the id of a part looked up
     * @return returns null if nothing is found
     */
    public static Part lookupPart(int partId) {

        for (Part p : allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Method for looking up a product
     * @param productId the id of the product looked up
     * @return returns null if nothing is found
     */
    public static Product lookupProduct(int productId) {

        for (Product product2 : allProducts) {
            if (product2.getId() == productId) {
                return product2;
            }
        }
        return null;
    }

    /**
     * Method for looking up a part according to product name
     * @param partName the name of a product looked up
     * @return returns a product from observable list
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> namePart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part1 : allParts) {
            if (part1.getName().contains(partName)) {
                namePart.add(part1);
            }
        }

        if (Objects.equals(partName, "")) {
            failedSearchPrompt();
        }

        return namePart;
    }

    /**
     * Method for looking up a product according to product name
     * @param productName the name of a product looked up
     * @return returns the product name from observable list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> nameProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product product1 : allProducts) {
            if (product1.getName().contains(productName)) {
                nameProduct.add(product1);
            }
        }
        if (Objects.equals(productName, "")) {
            failedSearchPrompt();
        }

        return nameProduct;
    }

    /**
     * Method for updating a part
     * @param index used for indicating index of a selected row of part
     * @param selectedPart indicated the selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Method for updating a product
     * @param index shows index of a product
     * @param newProduct information for updated product
     */
    public static void updateProduct(int index, Product newProduct) {
        Product product1 = Inventory.lookupProduct(index);
        Inventory.deleteProduct(product1);
        Inventory.addProduct(newProduct);
    }

    /**
     * Method for deleting a part
     * @param selectedPart the part selected
     * @return returns false if nothing is found
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for deleting a product
     * @param selectedProduct the selected product
     * @return returns false if nothing is found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * Method for getting all parts from observable list
     * @return returns all parts from observable list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method for getting all products from observable list
     * @return returns all products from observable list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Method for generating a dialog box when a search has failed
     */
    public static void failedSearchPrompt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nothing was found.");
        alert.setHeaderText(null);
        alert.setContentText("Search produced no results.");

        alert.showAndWait();
    }

}