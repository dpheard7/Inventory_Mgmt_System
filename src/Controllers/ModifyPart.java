package Controllers;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class which is the controller for Modify Part form.
 */
public class ModifyPart implements Initializable {

    Stage stage;
    Parent scene;
    private Part partToModify;
    boolean isInHouse;

    @FXML
    private RadioButton ModifyInHouse;
    @FXML
    private RadioButton ModifyOutSourced;
    @FXML
    private TextField ModifyPartsIDField;
    @FXML
    private TextField ModifyPartsNameField;
    @FXML
    private TextField ModifyPartsInventoryField;
    @FXML
    private TextField ModifyPartsPriceField;
    @FXML
    private TextField ModifyPartsMaxField;
    @FXML
    private TextField ModifyPartsMachineIDField;
    @FXML
    private TextField ModifyPartsMinField;
    @FXML
    private Label ModifyPartSourceLabel;

    /**
     * Disables Parts ID field from user input.
     */
    private void idDisabled () {
        ModifyPartsIDField.setEditable(false);
    }

    /**
     * Method which reads text field values and auto-populates on Modify Part screen when a user selects a part to modify.
     * @param selectedPart selects part for modification.
     */
    public void sendPart (Part selectedPart) {
        partToModify = selectedPart;

        ModifyPartsIDField.setText(String.valueOf(selectedPart.getId()));
        ModifyPartsNameField.setText(selectedPart.getName());
        ModifyPartsInventoryField.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartsPriceField.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartsMaxField.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartsMinField.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHousePart) {
            ModifyInHouse.setSelected(true);
            ModifyPartsMachineIDField.setText(String.valueOf(((InHousePart) selectedPart).getMachineID()));
            ModifyPartSourceLabel.setText("Machine ID");
        } else {
            ModifyOutSourced.setSelected(true);
            ModifyPartsMachineIDField.setText(((OutsourcedPart)selectedPart).getCompanyName());
            ModifyPartSourceLabel.setText("Company Name");
        }
    }

    /**
     * Radio button to select product sourcing and to determine future actions dependent on part sourcing.
     * @param event sets part sourcing as 'In-house'.
     */
    @FXML
    void onActionInHouseRadio(ActionEvent event) {
        if (ModifyInHouse.isSelected()) {
            isInHouse = true;
            this.ModifyPartSourceLabel.setText("Machine ID");
        }
    }

    /**
     * Radio button to select product sourcing and to determine future actions dependent on part sourcing.
     * @param event selects part sourcing as 'Outsourced'.
     */
    @FXML
    void onActionOutsourcedRadio(ActionEvent event) {
        if (ModifyOutSourced.isSelected()) {
            isInHouse = false;
            this.ModifyPartSourceLabel.setText("Company Name");
        }
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
     * Validates parts according to specified logical instructions.
     * @param stock
     * @param max
     * @param min
     * @return status of validation.
     */
    public boolean validatePart(String name, int stock, int max, int min) throws Exception {

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
     * Method to save modified part with sourcing determined by to which sourcing radio button is selected.
     * @param event user clicks 'Save' button.
     * @throws IOException
     *
     * RUNTIME EXCEPTION: NumberFormatException was throwing because of an error parsing ModifyPartsMachineID field. Whenever
     * I tried to modify the company name for an outsourced product, the application threw this exception because I did
     * not differentiate which action would take place according to which radio button was selected. I moved the functions
     * written to parse the machine ID or accept the company name under their respective 'if' statements, which solved the
     * problem.
     * RUNTIME EXCEPTION: OutOfBoundsException and NullPointerException thrown when attempting to save modified product.
     * There was a discrepancy between variable names in declarations and implementations. Saving modified parts would
     * create a new part which would then overwrite the first part in the Tableview, instead of simply updating the
     * current part as intended. I changed all incorrect variables to 'selectedPart', which fixed the overwriting issue
     * but then created a new, but duplicate, part using the updated information. I then invoked deletePart from the Inventory
     * class to remove the original part.
     */

    /**
     * Method to save information entered in Modify Part form.
     * @param event user clicks 'Save' button.
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws Exception {

        try {
            int modID = partToModify.getId();
            String modName = ModifyPartsNameField.getText();
            int modStock = Integer.parseInt(ModifyPartsInventoryField.getText());
            double modPrice = Double.parseDouble(ModifyPartsPriceField.getText());
            int modMax = Integer.parseInt(ModifyPartsMaxField.getText());
            int modMin = Integer.parseInt(ModifyPartsMinField.getText());
            int index = Inventory.getAllParts().indexOf(partToModify);

            if (ModifyInHouse.isSelected()) {
                int modMachineID = Integer.parseInt(ModifyPartsMachineIDField.getText());
                if (!validatePart(modName, modStock, modMax, modMin)) {
                    InHousePart modifiedInHouse = new InHousePart(modID, modName, modPrice, modStock, modMin, modMax, modMachineID);
                    Inventory.updatePart(index, modifiedInHouse);
                }
            } else if (ModifyOutSourced.isSelected()) {
                String modCompanyName = ModifyPartsMachineIDField.getText();
                if (!validatePart(modName, modStock, modMax, modMin)) {
                    OutsourcedPart modifiedOutsourced = new OutsourcedPart(modID, modName, modPrice, modStock, modMin, modMax, modCompanyName);
                    Inventory.updatePart(index, modifiedOutsourced);
                }
            }

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
     * Initializes action to disable the Part ID button.
     * @param url
     * @param resourceBundle
     * RUNTIME EXCEPTION: ClassCastException here when trying to modify an outsourced part: "class Model.OutsourcedPart
     *  cannot be cast to class Model.InHousePart (Model.OutsourcedPart and Model.InHousePart are in unnamed module
     *  of loader 'app'. Originally the 'if' statements use the isInHouse boolean, but I changed those to determine
     *  whether the parts were instanceof InHouse or Outsourced, then nested the boolean in the if statements. This
     *  fixed the problem, and I was able to get the correct window (Modify Part) open.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idDisabled();
    }
}
