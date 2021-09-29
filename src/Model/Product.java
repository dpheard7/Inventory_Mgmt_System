package Model;

/**
 *
 * @author Damon Heard
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for products in inventory. Also links associated parts.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product instance.
     * @param id product ID
     * @param name product name
     * @param price product price
     * @param stock product inventory level
     * @param min product minimum acceptable inventory
     * @param max product maximum acceptable inventory
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
     * Adds an associated part to the Associated Parts list
     * @param addedPart part associated with specified product.
     */
    public void addAssociatedPart(Part addedPart) {
        associatedParts.add(addedPart);
    }

    public void setAllAssociatedParts(ObservableList<Part> list) {
        associatedParts.setAll(list);
    }

    /**
     * Deletes an associated part from the Associated Parts list.
     * @param partToDelete part for deletion.
     * @return status of part deletion action.
     */
    public boolean deleteAssociatedPart(Part partToDelete) {
        associatedParts.remove(partToDelete);
        return true;
    }

    /** @return  */
    /**
     * Retrieves associated parts list.
     * @return list of parts associated with specified product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Retrieves ID of specified product.
     * @return product ID.
     */
    public int getId() {
        return id;
    }

    /** @param id S */
    /**
     * Sets product ID
     * @param id product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves name of specified product
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets product name
     * @param name product name.
     */
    public void setName(String name) {
        this.name = name;
    }

     /**
     * Retrieves product price.
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets product price.
     * @param price product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves product's inventory level.
     * @return product stock/inventory level.
     */
    public int getStock() {
        return stock;
    }

    /** @param stock  */
    /**
     * Sets product's stock level.
     * @param stock product's current inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

     /**
     * Retrieves product's minimum inventory level.
     * @return product minimum inventory level.
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets product's minimum stock level.
     * @param min product minimum inventory level.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Retrieves product's maximum inventory level.
     * @return product maximum inventory level.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets product's maximum stock level.
     * @param max product maximum inventory level.
     */
    public void setMax(int max) {
        this.max = max;
    }


}




