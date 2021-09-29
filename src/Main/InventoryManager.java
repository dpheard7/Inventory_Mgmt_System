package Main;

import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Damon Heard
 *
 * Javadocs located at : \C482 Software I\Javadocs
 *
 * The Inventory Manager is an application with which users can manage and internal inventory of parts, products, and
 * parts associated with specific products. This application also enables a user to keep track of inventory, add and/or
 * modify parts and products, and remove parts and products from the inventory.
 *
 * For future improvements, I suggest a feature which auto-populates parts associated with specific products based on a
 * predetermined model. For example, this program tracks parts used to manufacture books. Implementing my suggestion
 * would mean building a model which requires the book product to automatically populate the associated parts TableView
 * with a list of components necessary for the manufacture of this product. This will allow a hypothetical company to
 * improve their supply chain by more accurate inventory forecasting and therefore pre-ordering of parts.
 */
public class InventoryManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.setMinWidth(1188);
        primaryStage.setMinHeight(414);
        primaryStage.show();
    }

    /**
     * InventoryManager method which starts the application. Test data will be populated from here
     * @param args
     */
    public static void main(String[] args) {

        // Add Products
        Product product1 = new Product(1, "8x11 paper", 0.20, 100, 1, 200);
        Product product2 = new Product(2, "Hardcovers", 1.20, 100, 1, 200);
        Product product3 = new Product(3, "Dust Jacket", 0.75, 100, 1, 200);
        Product product4 = new Product(4, "Custom Bookmark", 2.50, 100, 1, 200);

        // Add In-House Parts
        InHousePart part5 = new InHousePart(5,"Ink Cartridge, B", 10.00, 25, 1, 100, 55);
        InHousePart part6 = new InHousePart(6,"Ink Cartridge, M", 10.00, 25, 1, 100, 56);
        InHousePart part7 = new InHousePart(7,"Ink Cartridge, C", 10.00, 25, 1, 100, 57);
        InHousePart part8 = new InHousePart(8,"Ink Cartridge, Y", 10.00, 25, 1, 100, 58);

        // Add Outsourced Parts
        OutsourcedPart part9 = new OutsourcedPart(9,"Promo Poster, A", 10.00, 25, 1, 100, "Tor Books");
        OutsourcedPart part10 = new OutsourcedPart(10,"Promo Poster, B", 10.00, 25, 1, 100, "Tor Books");
        OutsourcedPart part11 = new OutsourcedPart(11,"Promo Poster, C", 10.00, 25, 1, 100, "Tor Books");
        OutsourcedPart part12 = new OutsourcedPart(12,"Promo Poster, D", 10.00, 25, 1, 100, "Tor Books");

        Product book = new Product(50, "Winds of Winter", 19.99, 100, 1, 200);

        ObservableList<Part> list = FXCollections.observableArrayList();
        list.add(part10);
        list.add(part11);
        book.setAllAssociatedParts(list);

//        book.addAssociatedPart(part5);
//        book.addAssociatedPart(part9);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(book);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);
        Inventory.addPart(part11);
        Inventory.addPart(part12);

        launch(args);
    }
}
