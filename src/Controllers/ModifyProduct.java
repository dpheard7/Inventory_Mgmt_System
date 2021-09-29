package Controllers;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller class to modify a product specified by the user.
 */
public class ModifyProduct implements Initializable {

    Stage stage;
    Parent scene;
    private Product productToModify;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField ModifyProductIDField;
    @FXML
    private TextField ModifyProductNameField;
    @FXML
    private TextField ModifyProductInventoryField;
    @FXML
    private TextField ModifyProductPriceField;
    @FXML
    private TextField ModifyProductMaxField;
    @FXML
    private TextField ModifyProductMinField;
    @FXML
    private TextField ModifyProductSearchField;
    @FXML
    private TableView<Part> ModifyProductPartsTable;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartID;
    @FXML
    private TableColumn<Part, String> ModifyProductPartName;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartInventory;
    @FXML
    private TableColumn<Part, Double> ModifyProductPartCost;
    @FXML
    private TableView<Part> ModifyProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> ModifyProductAssocPartID;
    @FXML
    private TableColumn<Part, String> ModifyProductAssocPartName;
    @FXML
    private TableColumn<Part, Integer> ModifyProductAssocPartInventory;
    @FXML
    private TableColumn<Part, Double> ModifyProductAssocCost;
    @FXML
    private Button AddAssocPartButton;

    /**
     * Disables Product ID field from user input.
     */
    private void idDisabled () {
        ModifyProductIDField.setEditable(false);
    }

    /**
     * Auto-populates text fields on Modify Product form.
     * @param selectedProduct selects product for modification
     */
    public void sendProduct (Product selectedProduct) {

        productToModify = selectedProduct;

        ModifyProductIDField.setText(String.valueOf(selectedProduct.getId()));
        ModifyProductNameField.setText(selectedProduct.getName());
        ModifyProductInventoryField.setText(String.valueOf(selectedProduct.getStock()));
        ModifyProductPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        ModifyProductMaxField.setText(String.valueOf(selectedProduct.getMax()));
        ModifyProductMinField.setText(String.valueOf(selectedProduct.getMin()));

        associatedParts = productToModify.getAllAssociatedParts();
        ModifyProductAssociatedPartsTable.setItems(associatedParts);
        ModifyProductAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductAssocPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductAssocCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Cancels actions on current Modify Part screen without saving.
     * @param event user clicks 'Cancel' button.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes selected part associated to the current product.
     * @param event user clicks 'Remove' button.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        Part assocPartToDelete = ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();

        if (ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to delete!");
            alert.show();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setContentText("Are you sure you want to delete product: " + assocPartToDelete.getName() + "?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if(confirm.get() == ButtonType.OK) {
            ModifyProductAssociatedPartsTable.getItems().remove(assocPartToDelete);
        }
    }

    /**
     * Searches for specific parts defined by the user.
     * @param event search parts action.
     */
    @FXML
    void onActionModifyProductsSearch(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String partSearchString = ModifyProductSearchField.getText();

        for (Part part : allParts) {

            if (part.getName().toLowerCase().contains(partSearchString.toLowerCase()) || String.valueOf(part.getId()).contains(partSearchString)) {
                filteredParts.add(part);
            }
        }

        if (filteredParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not found. Please try again.");
            alert.showAndWait();
        }
        ModifyProductPartsTable.setItems(filteredParts);
    }

    /**
     *
     * @param name
     * @param stock
     * @param max
     * @param min
     * @return status of validation.
     * @throws Exception
     */
    public boolean validateModifiedProduct(String name, int stock, int max, int min) throws Exception {

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name field is blank.");
            alert.showAndWait();
            throw new NullPointerException();
        }

        if (min <= 0 || min >= max || stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Stock minimum must be more than zero and less than stock maximum.");
            alert.showAndWait();
            throw new Exception();
        }
        return false;
    }

    /**
     * Saves item in inventory.
     * @param event user clicks 'Save' button.
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws Exception {

        try {
            int id = productToModify.getId();
            String name = ModifyProductNameField.getText();
            int stock = Integer.parseInt(ModifyProductInventoryField.getText());
            double price = Double.parseDouble(ModifyProductPriceField.getText());
            int max = Integer.parseInt(ModifyProductMaxField.getText());
            int min = Integer.parseInt(ModifyProductMinField.getText());

            int index = Inventory.getAllProducts().indexOf(productToModify);

            if (!validateModifiedProduct(name, stock, max, min)) {
                Product modifiedProduct = new Product(id, name, price, stock, min, max);
                for (Part part : associatedParts) {
                    modifiedProduct.addAssociatedPart(part);
                }
                Inventory.updateProduct(index, modifiedProduct);
            }

            /** Returns scene back to Main Screen */
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Illegal number format. Please review your entry and try again.");
            alert.showAndWait();
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A field is blank. Please try again.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid inventory. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Adds product to the inventory.
     * @param event user clicks 'Add Product' button.
     */
    @FXML
    void onActionAddAssocPart(ActionEvent event) {

        Part addAssociatedPart = ModifyProductPartsTable.getSelectionModel().getSelectedItem();

        if (ModifyProductPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No item selected.");
            alert.setContentText("Don't forget to select an item to associate!");
            alert.show();
        } else {
            associatedParts.add(addAssociatedPart);
        }
    }

    /**
     * Initializes and populates the Parts table on  Modify Product screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idDisabled();


        ModifyProductPartsTable.setItems(Inventory.getAllParts());
        ModifyProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyProductAssociatedPartsTable.setItems(associatedParts);
        ModifyProductAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductAssocCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModifyProductAssocPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
