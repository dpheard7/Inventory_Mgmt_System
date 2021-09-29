package Controllers;

import Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for main screen of Inventory Manager application.
 */
public class MainScreen implements Initializable {

    Stage stage;
    Parent scene;

    private static Part modifiedPart;
    public static Part getModifiedPart() {
        return modifiedPart;
    }

    private static Product modifiedProduct;
    public static Product getModifiedProduct() {
        return modifiedProduct;
    }

    public static int modifyPartIndex;

    public static int getModifyPartIndex() {
        return modifyPartIndex;
    }

    @FXML
    private Button PartsSearchButton;
    @FXML
    private TextField SearchPartsField;
    @FXML
    private TableView<Part> PartsTable;
    @FXML
    private TableColumn<Part, Integer> PartID;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Integer> PartInventory;
    @FXML
    private TableColumn<Part, Double> PartCost;
    @FXML
    private Button PartAdd;
    @FXML
    private Button PartsModify;
    @FXML
    private Button PartsDelete;
    @FXML
    private Button ProductsSearchButton;
    @FXML
    private TextField SearchProductsField;
    @FXML
    private TableView<Product> ProductsTable;
    @FXML
    private TableColumn<?, Integer> ProductID;
    @FXML
    private TableColumn<?, String> ProductName;
    @FXML
    private TableColumn<?, Integer> ProductInventory;
    @FXML
    private TableColumn<?, Double> ProductCost;
    @FXML
    private Button ProductsAdd;
    @FXML
    private Button ProductsModify;
    @FXML
    private Button ProductsDelete;
    @FXML
    private Button MainMenuExit;

    /**
     * Opens Add Part form for user to enter a new part.
     * @param event user clicks 'Add' button.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Retrieves the item selected on the main screen for modification
     */
    /**
     * RUNTIME EXCEPTION: NullPointerException experienced when attempting to click 'Modify' button without first
     * selecting an item to modify. Corrected by creating an alert to inform the user that they must first select a part
     * before attempting to modify.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        modifiedPart = PartsTable.getSelectionModel().getSelectedItem();

        if (PartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to modify!");
            alert.show();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ModifyPart.fxml"));
        scene = loader.load();

        ModifyPart MPController = loader.getController();
        MPController.sendPart(modifiedPart);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * AddProduct button switches scene to Add Product screen.
     * RUNTIME EXCEPTION: NullPointerException thrown when attempting to add product.
     * @param event action to add product.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddProduct.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /**
     * ModifyProduct button switches scene to Modify Product screen.
     * @param event action to modify product.
     * @throws IOException
     */    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        modifiedProduct = ProductsTable.getSelectionModel().getSelectedItem();

        System.out.println(modifiedProduct.getAllAssociatedParts().size() + " in associated parts.");

        if (ProductsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to modify!");
            alert.show();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ModifyProduct.fxml"));
        scene = loader.load();

        ModifyProduct MPController = loader.getController();
        MPController.sendProduct(modifiedProduct);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes product from the products table and generates a dialog window to confirm delete with the user. Contains
     * logic to ensure user can not delete a product with associated parts.
     * @param event Delete product button action
     */
    @FXML
    void onActionDeleteProductButton(ActionEvent event) {
        Product productToDelete = ProductsTable.getSelectionModel().getSelectedItem();

        if (ProductsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to delete!");
            alert.show();
        } else if (!productToDelete.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to delete product.");
            alert.setContentText("Products with associated parts cannot be deleted. First remove associated product and try again.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete");
            alert.setContentText("Are you sure you want to delete product: " + productToDelete.getName() + "?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if(confirm.get() == ButtonType.OK) {
                ProductsTable.getItems().remove(productToDelete);
            }
        }
    }

    /**
     * Deletes part from the parts table and generates a dialog window to confirm delete with the user
     * @param event Delete part button action
     */
    @FXML
    void onActionDeletePartButton(ActionEvent event) {
        Part partToDelete = PartsTable.getSelectionModel().getSelectedItem();

        if (PartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to delete!");
            alert.show();
        }

        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertDelete.setTitle("Confirm delete");
        alertDelete.setContentText("Are you sure you want to delete part: " + partToDelete.getName() + "?");
        Optional<ButtonType> confirm = alertDelete.showAndWait();
        if(confirm.get() == ButtonType.OK) {
            PartsTable.getItems().remove(partToDelete);
        }
    }

    /**
     * Searches the parts table for a user-specified item; user can enter full or partial name, or part ID.
     * @param event Search parts text field action
     */
    @FXML
    void onActionSearchPartsField(ActionEvent event) {
        if (SearchPartsField.getText().trim().isEmpty()) {
            PartsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Searches the products table for a user-specified item; user can enter full or partial name, or product ID.
     * @param event Search parts text field action
     */
    @FXML
    void onActionSearchProductsField(ActionEvent event) {
        if (SearchProductsField.getText().trim().isEmpty()) {
            ProductsTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Button to search the parts table for a user-specified item.
     * @param event Search parts button action
     */
    @FXML
    void onActionSearchPartsButton(ActionEvent event) {

        String partSearchString = SearchPartsField.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

         if (SearchPartsField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a part name or ID.");
            alert.show();
            PartsTable.setItems(allParts);
         }

         for (Part part: allParts) {

            if (part.getName().toLowerCase().contains(partSearchString.toLowerCase()) || String.valueOf(part.getId()).contains(partSearchString)) {
                filteredParts.add(part);
            }
         }

         for (Part part: allParts) {
            if (filteredParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Part not found. Please try again.");
                alert.showAndWait();
                SearchPartsField.clear();

            }
            PartsTable.refresh();
            PartsTable.setItems(allParts);
            break;
         }

        PartsTable.setItems(filteredParts);

        if (filteredParts.isEmpty()) {
            PartsTable.setItems(allParts);
        }
    }

    /**
     * Button to search the products table for a user-specified item
     * @param event Search products button action
     */
    @FXML
    void onActionSearchProductsButton(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        String productSearchString = SearchProductsField.getText();

        if (SearchProductsField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a product name or ID.");
            alert.show();
            ProductsTable.setItems(allProducts);
        }

        for (Product product: allProducts) {

            if (product.getName().toLowerCase().contains(productSearchString.toLowerCase()) || String.valueOf(product.getId()).contains(productSearchString)) {
                filteredProducts.add(product);
            }
        }

        for (Product product: allProducts) {
            if (filteredProducts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product not found. Please try again.");
                alert.showAndWait();
                SearchProductsField.clear();
            }
            ProductsTable.refresh();
            ProductsTable.setItems(allProducts);
            break;
        }

        ProductsTable.setItems(filteredProducts);

        if (filteredProducts.isEmpty()) {
            ProductsTable.setItems(allProducts);
        }
    }

        /* for (Product product : allProducts) {

            filteredProducts.add(product);

            if (product.getName().toLowerCase().contains(productSearchString.toLowerCase())) {
                filteredProducts.add(product);
            }
            if (product.getName().contains(productSearchString)) {
                filteredProducts.add(product);
            }
            if (String.valueOf(product.getId()).contains(productSearchString)) {
                filteredProducts.add(product);
            }
            if (!product.getName().toLowerCase().contains(productSearchString)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product not found.");
                alert.show();
                Inventory.getAllParts();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a product name.");
            }
        } ProductsTable.setItems(filteredProducts);
    } */

    /**
     * Exits the program.
     * @param event user clicks 'Exit' button.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartsTable.setItems(Inventory.getAllParts());
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ProductsTable.setItems(Inventory.getAllProducts());
        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
