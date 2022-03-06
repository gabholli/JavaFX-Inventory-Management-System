package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for products
 */
public class Product {
    /**
     * Observable list for associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The id of a product
     */
    private int id;
    /**
     * The name of a product
     */
    private String name;
    /**
     * The price of a product
     */
    private double price;
    /**
     * The stock amount of a product
     */
    private int stock;
    /**
     * The minimum amount of a product
     */
    private int min;
    /**
     * The maximum amount of a product
     */
    private int max;

    /**
     * Constructor for Product class
     *
     * @param id    the product ID
     * @param name  the product name
     * @param price the product price
     * @param stock the amount of stock of a product
     * @param min   the minimum amount of a product
     * @param max   the maximum amount of a product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for product ID
     *
     * @param id the product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for product name
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for product name
     *
     * @param name the product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for product price
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for product price
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for stock amount for product
     * @return the stock amount for a product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for stock amount for product
     * @param stock the stock amount for a product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for minimum amount of a product
     * @return the minimum amount of a product
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for minimum amount of a product
     * @param min the minimum amount of a product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for maximum amount of a product
     * @return the maximum amount of a product
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for maximum amount of a product
     * @param max the maximum amount of a product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method for adding an associated part to a proudct
     * @param part part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Method for deleting an associated part from a product
     * @param selectedAssociatedPart the selected associated part
     * @return false if nothing is selected
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for getting all associated parts
     * @return returns associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
