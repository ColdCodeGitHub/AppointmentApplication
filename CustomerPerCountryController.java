/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class CustomerPerCountryController implements Initializable {
    
    @FXML private TextField us;
    @FXML private TextField uk;
    @FXML private TextField can;
    
    private Connection c;
    private ResultSet rs,rs2;

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
     * This method retrieves division and country data from the database.
     * 
     * @param customerList
     * @throws SQLException 
     */
    public void getData(ObservableList<Customer> customerList) throws SQLException {
        
        int countUs = 0;
        int countUk = 0;
        int countCan = 0;
        
        c = JDBC.openConnection();
        
        rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'U.S'");
        rs.next();
        int usID = rs.getInt("Country_ID");
        
        rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'UK'");
        rs.next();
        int ukID = rs.getInt("Country_ID");
        
        rs = c.createStatement().executeQuery("SELECT Country_ID FROM countries WHERE Country = 'Canada'");
        rs.next();
        int canID = rs.getInt("Country_ID");
        
        rs = c.createStatement().executeQuery("SELECT * FROM countries");
        while(rs.next()) {
            for(Customer customer : customerList)
            {
                rs2 = c.createStatement().executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = " + customer.getDivisionID().getValue());
                rs2.next();
                if(rs2.getInt(7) == rs.getInt(1) && rs.getInt(1) == usID){
                    countUs++;
                }
                
                else if(rs2.getInt(7) == rs.getInt(1) && rs.getInt(1) == ukID) {
                    countUk++;
                }
                else if(rs2.getInt(7) == rs.getInt(1) && rs.getInt(1) == canID) {
                    countCan++;
                }
            }
        }
        
        us.setText(Integer.toString(countUs));
        uk.setText(Integer.toString(countUk));
        can.setText(Integer.toString(countCan));
        
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
