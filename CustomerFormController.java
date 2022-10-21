/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Customer;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class CustomerFormController implements Initializable {

    @FXML private TableView<Customer> customerTable;
    
    @FXML private TableColumn<Customer, Integer> customerID;
    @FXML private TableColumn<Customer, String> cName;
    @FXML private TableColumn<Customer, String> cAddress;
    @FXML private TableColumn<Customer, String> cPostal;
    @FXML private TableColumn<Customer, String> cPhone;
    @FXML private TableColumn<Customer, Integer> cDivision;
    
    private Connection c;
    private ResultSet rs;
    private PreparedStatement pst;
    
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    /**
     * This method changes scenes to the appointment table.
     * 
     * @param event
     * @throws Exception 
     */
    public void appointmentTable(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method shows the amount of customers there are per country.
     * 
     * @param event
     * @throws Exception 
     */
    public void customersPerCountry(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerPerCountry.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.load();

        CustomerPerCountryController controller = loader.getController();
        controller.getData(customerList);

        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method changes scenes to the Add Customer form.
     * 
     * @param event
     * @throws Exception 
     */
    public void addCustomer(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method changes scenes to the Update Customer form.
     * 
     * @param event
     * @throws Exception 
     */ 
    public void updateCustomer(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.load();

            UpdateCustomerController controller = loader.getController();
            controller.getData(customerTable.getSelectionModel().getSelectedItem());

            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
    }
    
    /**
     * This method deletes a selected customer.
     * 
     * @param event
     * @throws Exception 
     */
    public void deleteCustomer(ActionEvent event) throws Exception {
        
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
        
        else {
            boolean exists = checkExists();
            
            if(exists == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Cannot delete customers with associated appointments!");
                alert.showAndWait();
            }
        
            else {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                    int id = selected.getCustomerID().getValue();
                    String custName = selected.getName().getValue();
                    
                    pst = c.prepareStatement("DELETE FROM customers WHERE Customer_ID = " + selected.getCustomerID());
                    pst.executeUpdate();
        
                    customerTable.getItems().remove(selected);
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("Customer with Customer ID: " + id + " and Name: " + custName + " has been deleted.");
                    alert2.showAndWait();
                }
            }
        }
    }
    
    /**
     * This method checks if a customer has any associated appointments.
     * 
     * @return true or false
     * @throws SQLException 
     */
    public boolean checkExists() throws SQLException {
        
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        c = JDBC.openConnection();
        
        rs = c.createStatement().executeQuery("SELECT * FROM appointments");
        while(rs.next()) {
            
            if(rs.getInt(12) == selected.getCustomerID().getValue()) {
                return true;
            }
        }
        
        return false;
    }
    /**
     * This method logs the user out of the application.
     * 
     * @param event
     * @throws Exception 
     */
    public void logOut(ActionEvent event) throws Exception {
        
        JDBC.closeConnection();
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Initializes the controller class with lambda expressions.
     * This method contains lambda expressions that set the cell values 
     * for the customer table-view.
     * Lambda expressions were used rather than PropertyValueFactory
     * because the lambdas allow for compile time checking.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        c = JDBC.openConnection();
        
        try {
            rs = c.createStatement().executeQuery("SELECT * FROM customers");
            
            while(rs.next()) {
                Customer customer = new Customer(new ReadOnlyObjectWrapper(rs.getInt(1)),
                                                 new ReadOnlyStringWrapper(rs.getString(2)),
                                                 new ReadOnlyStringWrapper(rs.getString(3)),
                                                 new ReadOnlyStringWrapper(rs.getString(4)),
                                                 new ReadOnlyStringWrapper(rs.getString(5)),
                                                 new ReadOnlyObjectWrapper(rs.getInt(10)));
                customerList.add(customer);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerID.setCellValueFactory(cellData -> cellData.getValue().getCustomerID());
        cName.setCellValueFactory(cellData -> cellData.getValue().getName());
        cAddress.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        cPostal.setCellValueFactory(cellData -> cellData.getValue().getPostal());
        cPhone.setCellValueFactory(cellData -> cellData.getValue().getPhone());
        cDivision.setCellValueFactory(cellData -> cellData.getValue().getDivisionID());
        
        customerTable.setItems(customerList);
    }    
    
}
