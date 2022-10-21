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
import model.Division;
import model.JDBC;


/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class AddCustomerController implements Initializable {

    @FXML private TextField fullName;
    @FXML private TextField address;
    @FXML private TextField postalCode;
    @FXML private TextField phoneNum;
    
    @FXML private ComboBox countryBox;
    @FXML private ComboBox divisionBox;
    
    private final ObservableList<Country> countryList = FXCollections.observableArrayList();
    private final ObservableList<Division> divisionList = FXCollections.observableArrayList();
    
    private Connection c;
    private ResultSet rs;
    private PreparedStatement pst;
    
    /**
     * This method changes the scene to the customer table.
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
     * This method saves customer information
     * inputted by a user.
     * 
     * @param event
     * @throws Exception 
     */
    public void saveButton(ActionEvent event) throws Exception {
        
        
        // Retrieve information from textfields and comboboxes
        try {
            int customerID = generateID();
            
            String name = fullName.getText();
            String add = address.getText();
            String postal = postalCode.getText();
            String phone = phoneNum.getText();

            Division choice = (Division) divisionBox.getSelectionModel().getSelectedItem();



            // Insert new customer into database
            c = JDBC.openConnection();
            pst = c.prepareStatement("INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)"
                                       + "VALUES(" + customerID + ","
                                       + "'" + name + "'" + ","
                                       + "'" + add + "'" + ","
                                       + "'" + postal + "'" + ","
                                       + "'" + phone + "'" + ","
                                       + choice.getDivisionID() + ")");

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
     * This method sets the division combo-box according to the country choice.
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
        
        divisionBox.setDisable(false);
        addressFormat();
    }
    
    /**
     * This method generates ids for new customers.
     * 
     * @return
     * @throws SQLException 
     */
    public int generateID() throws SQLException {
        c = JDBC.openConnection();
        
            rs = c.createStatement().executeQuery("SELECT MAX(Customer_ID) AS rowcount FROM customers");
            rs.next();
            
            int count = rs.getInt("rowcount");
            
            int id = count + 1;
            
            return id;
    }
    
    /**
     * This method determines the recommended address format based on country data.
     * 
     */
    public void addressFormat() {
        address.setDisable(false);
        
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
