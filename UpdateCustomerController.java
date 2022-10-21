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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class UpdateCustomerController implements Initializable {

    @FXML private TextField customerID;
    @FXML private TextField fullName;
    @FXML private TextField address;
    @FXML private TextField postalCode;
    @FXML private TextField phoneNum;
    
    @FXML private ComboBox countryBox;
    @FXML private ComboBox divisionBox;
    
    private final ObservableList<Country> countryList = FXCollections.observableArrayList();
    private final ObservableList<Division> divisionList = FXCollections.observableArrayList();
    
    private Connection c;
    private ResultSet rs,rs2,rs3,rs4;
    private PreparedStatement pst;
    
    private Customer existingCustomer;
    
    /**
     * This method changes scenes to the customer table.
     * 
     * @param event
     * @throws Exception 
     */
    public void customerTable(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method saves all updated customer information.
     * 
     * @param event
     * @throws Exception 
     */
    public void saveButton(ActionEvent event) throws Exception {
        
        
        // Retrieve information from textfields and comboboxes
        try {
            int custID = Integer.parseInt(customerID.getText());
            
            String name = fullName.getText();
            String add = address.getText();
            String postal = postalCode.getText();
            String phone = phoneNum.getText();

            Division choice = (Division) divisionBox.getSelectionModel().getSelectedItem();

            int divId = choice.getDivisionID();

            // Insert new customer into database
            c = JDBC.openConnection();
            pst = c.prepareStatement("UPDATE customers SET "
                                       + "Customer_Name = " + "'" + name + "'" + ","
                                       +  "Address = " + "'" + add + "'" + ","
                                       + "Postal_Code = " + "'" + postal + "'" + ","
                                       + "Phone = " +"'" + phone + "'" + ","
                                       + "Division_ID = " + divId + " WHERE Customer_ID = " + custID);

            pst.execute();

            // Return to customer table
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Fill in all required information!");
            alert.showAndWait();
        }
        
    }
    
    /**
     * This method determines division data based on choice of country.
     * 
     * @param event
     * @throws Exception 
     */
    public void countryChoice(ActionEvent event) throws Exception {
        
        Country choice = (Country) countryBox.getSelectionModel().getSelectedItem();
        
        c = JDBC.openConnection();
        
        rs = c.createStatement().executeQuery("SELECT * FROM first_level_divisions");
        
        divisionList.clear();
        
        while(rs.next()) {
            if(rs.getInt(7) == choice.getCountryID()) {
                Division newDiv = new Division(rs.getInt(1), rs.getInt(7), rs.getString(2));
                divisionList.add(newDiv);
            }
        }
        
        divisionBox.setItems(divisionList);
        addressFormat();
    }
    
    /**
     * This method retrieves data from a selected customer.
     * 
     * @param customer 
     */
    public void getData(Customer customer) {
        
        existingCustomer = customer;
        
        customerID.setText(Integer.toString(existingCustomer.getCustomerID().getValue()));
        fullName.setText(existingCustomer.getName().getValue());
        address.setText(existingCustomer.getAddress().getValue());
        postalCode.setText(existingCustomer.getPostal().getValue());
        phoneNum.setText(existingCustomer.getPhone().getValue());
        
        c = JDBC.openConnection();
        
        try {
            rs = c.createStatement().executeQuery("SELECT MAX(Division_ID) FROM first_level_divisions WHERE Country_ID = 1"); //US
            rs2 = c.createStatement().executeQuery("SELECT MAX(Division_ID) FROM first_level_divisions WHERE Country_ID = 2"); //UK
            rs3 = c.createStatement().executeQuery("SELECT MAX(Division_ID) FROM first_level_divisions WHERE Country_ID = 3"); //Canada
            rs4 = c.createStatement().executeQuery("SELECT * FROM first_level_divisions");
            
            rs.next();
            rs2.next();
            rs3.next();
            
            int countUsDivisions = rs.getInt("MAX(Division_ID)");
            int countUkDivisions = rs2.getInt("MAX(Division_ID)");
            int countCanDivisions = rs3.getInt("MAX(Division_ID)");
            
            if(existingCustomer.getDivisionID().getValue() > 0 && existingCustomer.getDivisionID().getValue() <= countUsDivisions) {
                
                countryBox.getSelectionModel().select(0);
                
                rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'U.S'");
                rs.next();
                
                int us = rs.getInt("Country_ID");
                
                while(rs4.next()) {
                    
                    if(rs4.getInt(7) == us) {
                        Division newDiv = new Division(rs4.getInt(1), rs4.getInt(7), rs4.getString(2));
                        divisionList.add(newDiv);
                        divisionBox.setItems(divisionList);
                        
                        if(rs4.getInt(1) == existingCustomer.getDivisionID().getValue()){
                            divisionBox.getSelectionModel().select(newDiv);
                        }
                    }
                    
                }
                
                
            }
            
            else if(existingCustomer.getDivisionID().getValue() > countUsDivisions && existingCustomer.getDivisionID().getValue() <= countCanDivisions) {
                
                countryBox.getSelectionModel().select(2);
                
                
                rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'Canada'");
                rs.next();
                
                int can = rs.getInt("Country_ID");
                
                while(rs4.next()) {
                    
                    if(rs4.getInt(7) == can) {
                        Division newDiv = new Division(rs4.getInt(1), rs4.getInt(7), rs4.getString(2));
                        divisionList.add(newDiv);
                        divisionBox.setItems(divisionList);
                        
                        if(rs4.getInt(1) == existingCustomer.getDivisionID().getValue()){
                            divisionBox.getSelectionModel().select(newDiv);
                        }
                    }
                    
                }
            }
            
            else if(existingCustomer.getDivisionID().getValue() > countCanDivisions && existingCustomer.getDivisionID().getValue() <= countUkDivisions) {
                
                countryBox.getSelectionModel().select(1);
                
                rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'UK'");
                rs.next();
                
                int uk = rs.getInt("Country_ID");
                
                while(rs4.next()) {
                    
                    if(rs4.getInt(7) == uk) {
                        Division newDiv = new Division(rs4.getInt(1), rs4.getInt(7), rs4.getString(2));
                        divisionList.add(newDiv);
                        divisionBox.setItems(divisionList);
                        
                        if(rs4.getInt(1) == existingCustomer.getDivisionID().getValue()){
                            divisionBox.getSelectionModel().select(newDiv);
                        }
                    }
                    
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method determines the recommended address format based on country data.
     * 
     */
    public void addressFormat() {
        
        if(countryBox.getSelectionModel().isSelected(0)) {
            address.setPromptText("123 ABC Street,Lima");
        }
        
        else if(countryBox.getSelectionModel().isSelected(1)) {
            address.setPromptText("123 ABC Street,Greenwich,London");
        }
        
        else if(countryBox.getSelectionModel().isSelected(2)) {
            address.setPromptText("123 ABC Street, Newmarket");
        } 
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        c = JDBC.openConnection();
        
        try {
            rs = c.createStatement().executeQuery("SELECT * FROM countries");
            
            while(rs.next()) {
                Country country = new Country(rs.getInt(1), rs.getString(2));
                countryList.add(country);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        countryBox.setItems(countryList);
        
    }    
    
}
