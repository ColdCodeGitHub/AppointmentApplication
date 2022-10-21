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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class TypeTotalsController implements Initializable {
    
    @FXML private TextField janStand, janPrem;
    @FXML private TextField febStand, febPrem;
    @FXML private TextField marStand, marPrem;
    @FXML private TextField aprStand, aprPrem;
    @FXML private TextField mayStand, mayPrem;
    @FXML private TextField junStand, junPrem;
    @FXML private TextField julStand, julPrem;
    @FXML private TextField augStand, augPrem;
    @FXML private TextField sepStand, sepPrem;
    @FXML private TextField octStand, octPrem;
    @FXML private TextField novStand, novPrem;
    @FXML private TextField decStand, decPrem;
    
    private Connection c;
    private ResultSet rs;

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
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        c = JDBC.openConnection();
        try {
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 1 AND Type = 'Standard'");
            rs.next();
            int janSt = rs.getInt("typecount");
            janStand.setText(Integer.toString(janSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 1 AND Type = 'Premium'");
            rs.next();
            int janPr = rs.getInt("typecount");
            janPrem.setText(Integer.toString(janPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 2 AND Type = 'Standard'");
            rs.next();
            int febSt = rs.getInt("typecount");
            febStand.setText(Integer.toString(febSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 2 AND Type = 'Premium'");
            rs.next();
            int febPr = rs.getInt("typecount");
            febPrem.setText(Integer.toString(febPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 3 AND Type = 'Standard'");
            rs.next();
            int marSt = rs.getInt("typecount");
            marStand.setText(Integer.toString(marSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 3 AND Type = 'Premium'");
            rs.next();
            int marPr = rs.getInt("typecount");
            marPrem.setText(Integer.toString(marPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 4 AND Type = 'Standard'");
            rs.next();
            int aprSt = rs.getInt("typecount");
            aprStand.setText(Integer.toString(aprSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 4 AND Type = 'Premium'");
            rs.next();
            int aprPr = rs.getInt("typecount");
            aprPrem.setText(Integer.toString(aprPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 5 AND Type = 'Standard'");
            rs.next();
            int maySt = rs.getInt("typecount");
            mayStand.setText(Integer.toString(maySt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 5 AND Type = 'Premium'");
            rs.next();
            int mayPr = rs.getInt("typecount");
            mayPrem.setText(Integer.toString(mayPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 6 AND Type = 'Standard'");
            rs.next();
            int junSt = rs.getInt("typecount");
            junStand.setText(Integer.toString(junSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 6 AND Type = 'Premium'");
            rs.next();
            int junPr = rs.getInt("typecount");
            junPrem.setText(Integer.toString(junPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 7 AND Type = 'Standard'");
            rs.next();
            int julSt = rs.getInt("typecount");
            julStand.setText(Integer.toString(julSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 7 AND Type = 'Premium'");
            rs.next();
            int julPr = rs.getInt("typecount");
            julPrem.setText(Integer.toString(julPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 8 AND Type = 'Standard'");
            rs.next();
            int augSt = rs.getInt("typecount");
            augStand.setText(Integer.toString(augSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 8 AND Type = 'Premium'");
            rs.next();
            int augPr = rs.getInt("typecount");
            augPrem.setText(Integer.toString(augPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 9 AND Type = 'Standard'");
            rs.next();
            int sepSt = rs.getInt("typecount");
            sepStand.setText(Integer.toString(sepSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 9 AND Type = 'Premium'");
            rs.next();
            int sepPr = rs.getInt("typecount");
            sepPrem.setText(Integer.toString(sepPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 10 AND Type = 'Standard'");
            rs.next();
            int octSt = rs.getInt("typecount");
            octStand.setText(Integer.toString(octSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 10 AND Type = 'Premium'");
            rs.next();
            int octPr = rs.getInt("typecount");
            octPrem.setText(Integer.toString(octPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 11 AND Type = 'Standard'");
            rs.next();
            int novSt = rs.getInt("typecount");
            novStand.setText(Integer.toString(novSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 11 AND Type = 'Premium'");
            rs.next();
            int novPr = rs.getInt("typecount");
            novPrem.setText(Integer.toString(novPr));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 12 AND Type = 'Standard'");
            rs.next();
            int decSt = rs.getInt("typecount");
            decStand.setText(Integer.toString(decSt));
            
            rs = c.createStatement().executeQuery("SELECT COUNT(*) AS typecount FROM appointments WHERE MONTH(Start) = 12 AND Type = 'Premium'");
            rs.next();
            int decPr = rs.getInt("typecount");
            decPrem.setText(Integer.toString(decPr));
            
        } catch (SQLException ex) {
            Logger.getLogger(TypeTotalsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
