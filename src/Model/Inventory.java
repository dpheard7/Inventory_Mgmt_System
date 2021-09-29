package Model;

/** @author Damon Heard */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**
 * Model for Inventory class. Models an inventory for parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * Adds a .part to the inventory
     * @param newPart new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the inventory.
     * @param newProduct new product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part by part ID.
     * @param partIDSearch part ID searched for by user.
     * @return the specific part searched for
     */
    public static Part lookupPartID (int partIDSearch) {
        Part partFound = null;
        for (Part part : allParts) {
            if (part.getId() == partIDSearch) {
    partFound = part;
            }
        }
        return partFound;
    }

    /**
     * Searches for a product by product ID.
     * @param productIDSearch specific product ID.
     * @return the part searched for using the ID.
     */
    public static Product lookupProductID (int productIDSearch) {
        Product productFound = null;
        for (Product product : allProducts) {
            if (product.getId() == productIDSearch) {
                productFound = product;
            }
        }
        return productFound;
    }

    /** Searches for a part by full or partial name */
    /**
     * Searches for a part by part name.
     * @param partNameSearch specific part name.
     * @return part searched for using the part name.
     */
    public static ObservableList<Part> lookupPartName (String partNameSearch) {
        ObservableList<Part> searchPartsFound = FXCollections.observableArrayList();
        // try {
        for (Part part : allParts) {
            if (part.getName().contains(partNameSearch)) {
                searchPartsFound.add(part);
                }
            }
        /* } catch (NullPointerException nullField) {
            // Alert alert = new Alert(Alert.AlertType.ERROR);
            // alert.setContentText("Please enter a part name or ID.");
        } */
        return searchPartsFound;
    }

    /**
     * Searches for a product by full or partial name.
     * @param productNameSearch specific product name.
     * @return returns product searched for by name.
     */
    public ObservableList<Product> lookupProductName (String productNameSearch) {
        ObservableList<Product> searchProductsFound = FXCollections.observableArrayList();
        try {
            for (Product product : allProducts) {
                if (product.getName().contains(productNameSearch)) {
                    searchProductsFound.add(product);
                }
            }
        } catch (NullPointerException nullField) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a part name.");
        }
        return searchProductsFound;
    }

    /**
     * Updates selected part in the inventory.
     * @param index index of the specific part.
     * @param selectedPart the part to be updated by the user.
     */
    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates selected part in the inventory.
     * @param index index of the specific part.
     * @param selectedProduct the product to be updated by the user.
     */    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes selected part from parts list.
     * @param selectedPart part selected by user.
     * @return returns status of delete action.
     */
    public static boolean deletePart (Part selectedPart) {
        for(Part part : allParts) {
            if (allParts.contains(selectedPart)) {
                allParts.remove(selectedPart);
                }
            return true;
           }
        return false;
    }

    /**
     * Deletes selected product from parts list.
     * @param selectedProduct part selected by user.
     * @return returns status of delete action.
     */
    public static boolean deleteProduct (Product selectedProduct) {
        for(Product product : allProducts) {
            if (allProducts.contains(selectedProduct)) {
                allProducts.remove(selectedProduct);
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves all parts.
     * @return the full Parts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all products.
     * @return the full Products list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Retrieves parts filtered during a search action..
     * @return the filtered parts list.
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    /**
     * Retrieves products filtered during a search action..
     * @return the filtered products list.
     */    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }

}
