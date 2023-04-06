package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.GUI;

import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Application;
import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic.*;
import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Data.Serializator;
import com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.Model.User;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML private TextField username_textfield_fx;
    @FXML private TextField login_password_fx;
    @FXML private TextField register_username_textfield_fx;
    @FXML private TextField register_password_fx;
    @FXML private Label login_msgbox_fx;
    @FXML private Label registration_msglabel_fx;

    // user view
    @FXML private TableView<MenuItem> client_products_table_fx;
    @FXML private TableColumn<MenuItem, Integer> client_id;
    @FXML private TableColumn<MenuItem, String> client_title;
    @FXML private TableColumn<MenuItem, Double> client_rating;
    @FXML private TableColumn<MenuItem, Integer> client_calories;
    @FXML private TableColumn<MenuItem, Integer> client_protein;
    @FXML private TableColumn<MenuItem, Integer> client_fat;
    @FXML private TableColumn<MenuItem, Integer> client_sodium;
    @FXML private TableColumn<MenuItem, Integer> client_price;
    // searching
    @FXML private TextField client_search_name;
    @FXML private TextField client_search_price_max;
    @FXML private TextField client_search_calories_max;
    @FXML private TextField client_search_sodium_max;
    @FXML private TextField client_search_fats_max;
    @FXML private Label client_search_results;
    // ordering
    @FXML private TextField client_productToBeAdded;
    @FXML private Label client_current_menu;
    @FXML private Label client_order_price;

    // admin view
    @FXML private TableView<MenuItem> admin_products_table_fx;
    @FXML private TableColumn<MenuItem, Integer> admin_id;
    @FXML private TableColumn<MenuItem, String> admin_title;
    @FXML private TableColumn<MenuItem, Double> admin_rating;
    @FXML private TableColumn<MenuItem, Integer> admin_calories;
    @FXML private TableColumn<MenuItem, Integer> admin_protein;
    @FXML private TableColumn<MenuItem, Integer> admin_fat;
    @FXML private TableColumn<MenuItem, Integer> admin_sodium;
    @FXML private TableColumn<MenuItem, Integer> admin_price;
    @FXML private Label admin_msgbox;
    // for managing products
    @FXML private TextField admin_textfield_id;
    @FXML private TextField admin_textfield_title;
    @FXML private TextField admin_textfield_rating;
    @FXML private TextField admin_textfield_calories;
    @FXML private TextField admin_textfield_protein;
    @FXML private TextField admin_textfield_fat;
    @FXML private TextField admin_textfield_sodium;
    @FXML private TextField admin_textfield_price;
    // for creating a new menu
    @FXML private TextField admin_productToBeAdded_textfield;
    @FXML private TextField admin_productToBeAdded_name_textfield;
    @FXML private Label current_menu_fx;

    // for generating reports
    @FXML private TextField admin_report_interval_min;
    @FXML private TextField admin_report_interval_max;
    @FXML private TextField admin_filter_products_specific_day;
    // employee
    @FXML private TableView<Order> employee_orders_table_fx;
    @FXML private TableColumn<Order, Integer> employee_order_id;
    @FXML private TableColumn<Order, String> employee_order_username;
    @FXML private TableColumn<Order, Date> employee_order_date;
    @FXML private StackPane employee_client_interface;
    @FXML private AnchorPane client_employee_interface_client_pane;
    @FXML private AnchorPane client_employee_interface_employee_pane;
    @FXML private Label no_employee_authorization_msg;

    ObservableList<Order> observableOrderList = FXCollections.observableArrayList();


    ArrayList<MenuItem> currentItemsInBasket = new ArrayList<>();
    ArrayList<BaseProduct> currentItemsInNewMenu = new ArrayList<>();
    Serializator serializator = new Serializator();
    private final IDeliveryService deliveryService;
    String currentLoggedUser = "";
    String currentLoggedUserRole = "";

    int currentOrderPrice = 0;

    public Controller(IDeliveryService service) throws IOException, ClassNotFoundException {
        deliveryService = service;
        deliveryService.users = serializator.deserializeUsers();
        System.out.println("ALL USERS: ");
        for(User u: deliveryService.users) {
            System.out.println(u.getUsername() + " " + u.getRole() + " " + u.getPassword());
        }
        File checkIfPreviouslyLoggedIn = new File("loggedInUsername.ser");
        if(checkIfPreviouslyLoggedIn.length() != 0) {
            currentLoggedUser = serializator.deserializeCurrentlyLoggedUsername();
            currentLoggedUserRole = serializator.deserializeCurrentlyLoggedRole();
        }
        System.out.println("Currently logged in user: " + currentLoggedUser);
        System.out.println("Currently logged in user's role: " + currentLoggedUserRole);
    }

    /**
     * Method that checks user input and logs the user in
     * @throws IOException
     */
    @FXML
    private void logIn() throws IOException {
        login_msgbox_fx.setText("");
        String searchedUsername = username_textfield_fx.getText();
        String searchedPassword = login_password_fx.getText();
        for(User user: deliveryService.users) {
            if(user.getUsername().equals(searchedUsername)) {
                if(user.getPassword().equals(searchedPassword)) {
                    // persist logged user data
                    serializator.serializeCurrentlyLoggedUsername(user.getUsername());
                    serializator.serializeCurrentlyLoggedRole(user.getRole());
                    if(user.getRole().equals("admin")) {
                        switchToAdminPage();
                    }
                    else {
                        switchToEmployeePage();
                    }
                    return;
                }
                else {
                    login_msgbox_fx.setText("Incorrect password!");
                }
            }
        }
        login_msgbox_fx.setText("No user found.");
    }

    @FXML
    private void switchToAdminPage() throws IOException {
        Application.changeWindow("Admin.fxml");
    }

    /**
     * Method for importing the original products from a .csv file
     * @throws IOException
     */
    @FXML
    private void importBaseProducts() throws IOException {
        DeliveryService deliveryService = new DeliveryService();
        ObservableList<MenuItem> observableMenuItems = FXCollections.observableArrayList();
        List<BaseProduct> importedItems = deliveryService.importItems();
        observableMenuItems.addAll(importedItems);
        deliveryService.getMenuItems().addAll(importedItems);
        serializator.serializeMenuItems(deliveryService.getMenuItems());

        admin_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        admin_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        admin_rating.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        admin_calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        admin_protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        admin_fat.setCellValueFactory(new PropertyValueFactory<>("Fat"));
        admin_sodium.setCellValueFactory(new PropertyValueFactory<>("Sodium"));
        admin_price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        admin_products_table_fx.getItems().setAll(observableMenuItems);
    }

    /**
     * Method for deserializing products for the admin
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    private void adminRefreshProducts() throws IOException, ClassNotFoundException {
        // load current menu
        deliveryService.setMenuItems(serializator.deserializeMenuItems());
        deliveryService.setOrders(serializator.deserializeOrders());
        ObservableList<MenuItem> observableMenuItems = FXCollections.observableArrayList();
        observableMenuItems.addAll(deliveryService.getMenuItems());

        admin_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        admin_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        admin_rating.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        admin_calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        admin_protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        admin_fat.setCellValueFactory(new PropertyValueFactory<>("Fat"));
        admin_sodium.setCellValueFactory(new PropertyValueFactory<>("Sodium"));
        admin_price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        admin_products_table_fx.getItems().setAll(observableMenuItems);

    }

    /**
     * Method for deserializing all products for the client, from the delivery service
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    private void clientRefreshProducts() throws IOException, ClassNotFoundException {
        // load current menu
        employeeRefresh();
        deliveryService.setMenuItems(serializator.deserializeMenuItems());
        ObservableList<MenuItem> observableMenuItems = FXCollections.observableArrayList();
        observableMenuItems.addAll(deliveryService.getMenuItems());

        client_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        client_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        client_rating.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        client_calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        client_protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
        client_fat.setCellValueFactory(new PropertyValueFactory<>("Fat"));
        client_sodium.setCellValueFactory(new PropertyValueFactory<>("Sodium"));
        client_price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        client_products_table_fx.getItems().setAll(observableMenuItems);
    }

    /**
     * Method for adding a new product as the administrator
     * @throws IOException
     */
    @FXML
    private void adminAddProduct() throws IOException {
        if(admin_textfield_id.getText().isEmpty() || admin_textfield_fat.getText().isEmpty() || admin_textfield_price.getText().isEmpty()
        || admin_textfield_calories.getText().isEmpty() || admin_textfield_rating.getText().isEmpty()
        || admin_textfield_protein.getText().isEmpty() || admin_textfield_sodium.getText().isEmpty()
        || admin_textfield_title.getText().isEmpty()) {
            admin_msgbox.setText("Please fill all fields.");
        }
        else {
            int id = Integer.parseInt(admin_textfield_id.getText());
            String title = admin_textfield_title.getText();
            double rating = Double.parseDouble(admin_textfield_rating.getText());
            int calories = Integer.parseInt(admin_textfield_calories.getText());
            int protein = Integer.parseInt(admin_textfield_protein.getText());
            int fat = Integer.parseInt(admin_textfield_fat.getText());
            int sodium = Integer.parseInt(admin_textfield_sodium.getText());
            int price = Integer.parseInt(admin_textfield_price.getText());
            BaseProduct baseProduct = new BaseProduct(id, title, rating, calories, protein, fat, sodium, price);
            admin_products_table_fx.getItems().add(baseProduct);
            deliveryService.getMenuItems().add(baseProduct);
            serializator.serializeMenuItems(deliveryService.getMenuItems());
        }
    }

    /**
     * Method for editing a product as the administrator
     * @throws IOException
     */
    @FXML
    private void adminEditProduct() throws IOException {
        if(admin_textfield_id.getText().isEmpty()) {
            admin_msgbox.setText("Please enter an ID and data to be edited.");
        }
        else if (deliveryService.getMenuItems().isEmpty()) {
            admin_msgbox.setText("Please initialize data first.");
        }
        else {
            int index = Integer.parseInt(admin_textfield_id.getText());
            MenuItem productToBeEdited = adminFindProduct(index);
            if(!admin_textfield_title.getText().isEmpty()) {
                productToBeEdited.setTitle(admin_textfield_title.getText());
            }
            if(!admin_textfield_rating.getText().isEmpty()) {
                productToBeEdited.setRating(Double.parseDouble(admin_textfield_rating.getText()));
            }
            if(!admin_textfield_calories.getText().isEmpty()) {
                productToBeEdited.setCalories(Integer.parseInt(admin_textfield_calories.getText()));
            }
            if(!admin_textfield_protein.getText().isEmpty()) {
                productToBeEdited.setProtein(Integer.parseInt(admin_textfield_protein.getText()));
            }
            if(!admin_textfield_fat.getText().isEmpty()) {
                productToBeEdited.setFat(Integer.parseInt(admin_textfield_fat.getText()));
            }
            if(!admin_textfield_sodium.getText().isEmpty()) {
                productToBeEdited.setSodium(Integer.parseInt(admin_textfield_sodium.getText()));
            }
            if(!admin_textfield_price.getText().isEmpty()) {
                productToBeEdited.setPrice(Integer.parseInt(admin_textfield_price.getText()));
            }
            serializator.serializeMenuItems(deliveryService.getMenuItems());
            admin_msgbox.setText("Edited product!");
        }
    }

    /**
     * Method for searching for an item as the admin.
     * @param id ID of searched product.
     * @return
     */
    @FXML
    private MenuItem adminFindProduct(int id) {
        for(MenuItem menuItem: deliveryService.getMenuItems()) {
            if(menuItem.getId() == id) {
                return menuItem;
            }
        }
        return null;
    }

    /**
     * Method for removing a product as the administrator.
     * @throws IOException
     */
    @FXML
    private void adminRemoveProduct() throws IOException {
        if(admin_textfield_id.getText().isEmpty()) {
            admin_msgbox.setText("Please enter an ID of data to be removed.");
        }
        else if (deliveryService.getMenuItems().isEmpty()) {
            admin_msgbox.setText("Please initialize data first.");
        }
        else {
            int index = Integer.parseInt(admin_textfield_id.getText());
            MenuItem menuItemToBeRemoved = adminFindProduct(index);
            System.out.println(menuItemToBeRemoved.getTitle());
            admin_products_table_fx.getItems().remove(menuItemToBeRemoved);
            deliveryService.getMenuItems().remove(menuItemToBeRemoved);
            serializator.serializeMenuItems(deliveryService.getMenuItems());
            admin_msgbox.setText("Removed item!");
        }
    }

    /**
     * Method for adding a temporary product to the list of items to be added to the menu.
     */
    @FXML
    private void addProductToCurrentMenu() {
        if(!admin_productToBeAdded_textfield.getText().isEmpty()) {
            int index = Integer.parseInt(admin_productToBeAdded_textfield.getText());
            BaseProduct productAdded = (BaseProduct) adminFindProduct(index);
            currentItemsInNewMenu.add(productAdded);
            current_menu_fx.setText(current_menu_fx.getText() + " - " + productAdded.getTitle() + " - ");
            System.out.println("SIZE: " + currentItemsInNewMenu.size());
        }
        else {
            admin_msgbox.setText("Enter valid ID.");
        }
    }

    /**
     * Method for adding an item in the "basket" as the client.
     */
    @FXML
    private void addProductToCurrentOrder() {
        if(!client_productToBeAdded.getText().isEmpty()) {
            int index = Integer.parseInt(client_productToBeAdded.getText());
            MenuItem productAdded = adminFindProduct(index);
            currentItemsInBasket.add(productAdded);
            client_current_menu.setText(client_current_menu.getText() + " - " + productAdded.getTitle() + " - ");
            currentOrderPrice += productAdded.getPrice();
            String currentPrice = String.valueOf(currentOrderPrice);
            client_order_price.setText("Total: " + currentPrice);
            System.out.println("SIZE: " + currentItemsInBasket.size());
        }
        else {
            client_current_menu.setText("Enter valid ID.");
        }
    }

    /**
     * Method for filtering the products as the client.
     */
    @FXML
    private void searchProducts() {
        ArrayList<MenuItem> foundItems = new ArrayList<>();
        for(MenuItem menuItem : deliveryService.getMenuItems()) {
            if(menuItem.getTitle().contains(client_search_name.getText())) {
                if(menuItem.getPrice() <= Integer.parseInt(client_search_price_max.getText())) {
                    if(menuItem.getCalories() <= Integer.parseInt(client_search_calories_max.getText())) {
                        if(menuItem.getSodium() <= Integer.parseInt(client_search_sodium_max.getText())) {
                            if(menuItem.getFat() <= Integer.parseInt(client_search_fats_max.getText())) {
                                foundItems.add(menuItem);
                            }
                        }
                    }
                }
            }
        }
        for(MenuItem menuItem : foundItems) {
            client_search_results.setText(client_search_results.getText() + "- ID: " + menuItem.getId() + ", " + menuItem.getTitle() +" -");
        }
    }

    @FXML
    private void searchProducts1() {
        ArrayList<MenuItem> foundMenuItems  = deliveryService.getMenuItems()
                .stream()
                .filter(menuItem -> menuItem.getTitle().contains(client_search_name.getText()))
                .filter(menuItem -> menuItem.getPrice() <= Integer.parseInt(client_search_price_max.getText()))
                .filter(menuItem -> menuItem.getCalories() <= Integer.parseInt(client_search_calories_max.getText()))
                .filter(menuItem -> menuItem.getSodium() <= Integer.parseInt(client_search_sodium_max.getText()))
                .filter(menuItem -> menuItem.getFat() <= Integer.parseInt(client_search_fats_max.getText()))
                .collect(Collectors.toCollection(ArrayList<MenuItem>::new));
        System.out.println(foundMenuItems.size());
        for(MenuItem menuItem : foundMenuItems) {
            client_search_results.setText(client_search_results.getText() + "- ID: " + menuItem.getId() + ", " + menuItem.getTitle() +" -");
        }
    }

    /**
     * Method for making an order as the client.
     * @throws IOException
     */
    @FXML
    private void addOrder() throws IOException {
        deliveryService.setOrders(serializator.deserializeOrders());
        client_current_menu.setText("");
        Date date = new Date(System.currentTimeMillis());
        Order newOrder = new Order(deliveryService.getOrders().size() + 1, currentLoggedUser, date);
        deliveryService.addOrder(newOrder, currentItemsInBasket);
        serializator.serializeOrders((HashMap<Order, List<MenuItem>>) deliveryService.getOrders());
        observableOrderList.add(newOrder);
        // generate bill
        FileWriter bill = new FileWriter(currentLoggedUser + "'s Bill.txt");
        bill.write("Name of client: " + currentLoggedUser + "\n");
        bill.write("Ordered products: ");
        for(MenuItem menuItem : currentItemsInBasket) {
            bill.write(" - " + menuItem.getTitle() + " - ");
        }
        bill.write("\n");
        double total = currentOrderPrice;
        total = 1.19 * total;
        String totalAsString = String.format("%.2f", total);
        bill.write("Total: " + totalAsString + ", VAT included\n");
        bill.close();
        currentItemsInBasket.clear();
        client_order_price.setText("Ordered!");
        currentOrderPrice = 0;
    }

    /**
     * Method for creating a new menu as the administrator.
     * @throws IOException
     */
    @FXML
    private void addMenu() throws IOException {
        int id = deliveryService.getMenuItems().size() + 1;
        String title = admin_productToBeAdded_name_textfield.getText();
        double rating = 0;
        int calories = 0;
        int protein = 0;
        int fat = 0;
        int sodium = 0;
        int price = 0;
        for(BaseProduct baseProduct : currentItemsInNewMenu) {
            rating += baseProduct.getRating();
            calories += baseProduct.getCalories();
            protein += baseProduct.getProtein();
            fat += baseProduct.getFat();
            sodium += baseProduct.getSodium();
            price += baseProduct.getPrice();
        }
        rating = rating / currentItemsInNewMenu.size();
        CompositeProduct compositeProduct = new CompositeProduct(id, title, rating, calories, protein, fat, sodium, price, currentItemsInNewMenu);
        admin_products_table_fx.getItems().add(compositeProduct);
        deliveryService.addMenu(compositeProduct);
        serializator.serializeMenuItems(deliveryService.getMenuItems());
        currentItemsInNewMenu.clear();
    }

    /**
     * Method for initiating table with the new orders for the employees.
     */
    @FXML
    private void employeeRefresh() {
        // load employee table
        Property<ObservableList<Order>> orderListProperty = new SimpleObjectProperty<>(observableOrderList);
        employee_orders_table_fx.itemsProperty().bind(orderListProperty);
        employee_order_id.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        employee_order_username.setCellValueFactory(new PropertyValueFactory<>("Client_username"));
        employee_order_date.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
    }

    @FXML
    private void switchEmployeePane() {
        if(!currentLoggedUserRole.equals("employee")) {
            no_employee_authorization_msg.setText("You're not authorized.");
        }
        else {
            employee_client_interface.getChildren().get(employee_client_interface.getChildren().size() - 1).toBack();
        }
    }

    @FXML
    private void switchToClientPane() {
        employee_client_interface.getChildren().get(employee_client_interface.getChildren().size() - 1).toBack();
    }

    @FXML
    private void switchToEmployeePage() throws IOException {
        Application.changeWindow("UserEmployee.fxml");
    }



    @FXML
    private void generateReport() throws IOException {
        int min_time = Integer.parseInt(admin_report_interval_min.getText());
        int max_time = Integer.parseInt(admin_report_interval_max.getText());
        int day_of_the_week = 0;
        String day = admin_filter_products_specific_day.getText();
        if(day.equals("Monday")) {
            day_of_the_week = 1;
        }
        else if(day.equals("Tuesday")) {
            day_of_the_week = 2;
        }
        else if(day.equals("Wednesday")) {
            day_of_the_week = 3;
        }
        else if(day.equals("Thursday")) {
            day_of_the_week = 4;
        }
        else if(day.equals("Friday")) {
            day_of_the_week = 5;
        }
        else if(day.equals("Saturday")) {
            day_of_the_week = 6;
        }
        else if(day.equals("Sunday")) {
            day_of_the_week = 0;
        }

        System.out.println("Found");
        int finalDay_of_the_week = day_of_the_week;
        ArrayList<Order> foundOrders  = deliveryService.getOnlyOrderDetails().stream()
                        .filter(order -> order.getOrderDate().getHours() <= max_time && order.getOrderDate().getHours() >= min_time)
                        .filter(order -> order.getOrderDate().getDay() == finalDay_of_the_week)
                        .collect(Collectors.toCollection(ArrayList<Order>::new));
        FileWriter report = new FileWriter("Report.txt");
        report.write("Filtered orders:\n");

        report.write("\n");
        for(Order order : foundOrders) {
            report.write("Order: " + order.getOrderID() + ", Client: " + order.getClient_username() + ", Time: " + order.getOrderDate() + "\n");
        }
        report.close();
    }

    @FXML
    private void switchToRegistrationPage() throws IOException {
        Application.changeWindow("RegisterUser.fxml");
    }

    /**
     * Method for registering a new user.
     * @throws IOException
     */
    @FXML
    private void registerUser() throws IOException {
        User user = new User(register_username_textfield_fx.getText(), register_password_fx.getText(), "user");
        for(User u: deliveryService.users) {
            if(u.getUsername().equals(register_username_textfield_fx.getText())) {
                registration_msglabel_fx.setText("Username already in use.");
                return;
            }
        }
        deliveryService.users.add(user);
        serializator.serializeUsers(deliveryService.users);
        goBackToLoginPage();
    }

    @FXML
    private void goBackToLoginPage() throws IOException {
        Application.changeWindow("Login.fxml");
    }


}
