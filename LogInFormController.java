/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class LogInFormController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    
    @FXML private Label timeZone, login;
    @FXML private Button enter;
    
    private Connection c;
    private ResultSet rs,rs2;
    
    /**
     * This method checks the username and password and logs any login attempt
     * 
     * @param event
     * @throws Exception 
     */
    public void logInTest(ActionEvent event) throws Exception {
        String uName = username.getText();
        String pWord = password.getText();
        
        boolean upcoming = false;
        int id = 0;
        LocalDateTime start = null;
        
        // The boolean value in the filehandler ensures that the file is appended, not overwritten
        FileHandler fileHandler = new FileHandler("login_activity.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        fileHandler.setFormatter(sf);
        Logger logger = Logger.getLogger("login");
        logger.addHandler(fileHandler);
        
        if(uName.equals(" ") || pWord.equals(" ") || uName.isEmpty() || pWord.isEmpty()) {
            
            logger.log(Level.INFO, "\n-Login Attempt: Failure\n-Username: ".concat(username.getText()));
            
            ResourceBundle rb = ResourceBundle.getBundle("model/Nat", Locale.getDefault());
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("Error"));
            alert.setContentText(rb.getString("Blank") + "!");
            alert.showAndWait();
        }
        
        else {
            try {
                c = JDBC.openConnection();
                
                PreparedStatement pst;
                pst = c.prepareStatement("SELECT * FROM users WHERE User_Name = ? AND Password = ?");
                
                pst.setString(1, uName);
                pst.setString(2, pWord);
                
               
                rs = pst.executeQuery();
                
                if(rs.next()) {
                    Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                    
                    logger.log(Level.INFO, "\n-Login Attempt: Success\n-Username: ".concat(username.getText()));
                    
                    try {
                        rs2 = c.createStatement().executeQuery("SELECT * FROM appointments");
                        Long now = System.currentTimeMillis();

                        while(rs2.next()) {
                            Timestamp startStamp = rs2.getTimestamp(6);
                            ZonedDateTime startZoneTime = ZonedDateTime.ofInstant(startStamp.toInstant(), ZoneId.systemDefault());
                            Timestamp finalStamp = Timestamp.valueOf(startZoneTime.toLocalDateTime());

                            Long startS = finalStamp.getTime();

                            upcoming = Math.abs(startS - now) <= TimeUnit.MINUTES.toMillis(15);
                            
                            if(upcoming == true) {
                                id = rs2.getInt(1);
                                
                                LocalDateTime ldtStart = startStamp.toLocalDateTime();
                                ZonedDateTime zdtStart = ldtStart.atZone(ZoneId.systemDefault());
                                start = zdtStart.toLocalDateTime();
                            }
                        }

                    if(upcoming == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("There will be an upcoming appointment soon!\nAppointment ID: " + id + "\nStart Time: " + start);
                        alert.showAndWait();
                    }

                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("There are no upcoming appointments.");
                        alert.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(CustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                else {
                    logger.log(Level.INFO, "\n-Login Attempt: Failure\n-Username: ".concat(username.getText()));
                    
                    ResourceBundle rb = ResourceBundle.getBundle("model/Nat", Locale.getDefault());
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(rb.getString("Error"));
                    alert.setContentText(rb.getString("Incorrect") + "!");
                    alert.showAndWait();
                }
                
            }
            catch(IOException | SQLException e) {
                
            }
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rb = ResourceBundle.getBundle("model/Nat", Locale.getDefault());
        username.setPromptText(rb.getString("Username"));
        password.setPromptText("Password");
        login.setText(rb.getString("Login"));
        enter.setText(rb.getString("Enter"));
        
        ZoneId zoneID = ZoneId.systemDefault();
        timeZone.setText(zoneID.toString());
        
    }    
    
}
