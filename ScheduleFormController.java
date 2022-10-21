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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class ScheduleFormController implements Initializable {

    @FXML private TableView<Appointment> scheduleTable;
    
    @FXML private TableColumn<Appointment, Integer> appointID;
    @FXML private TableColumn<Appointment, Integer> custID;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, LocalDateTime> start;
    @FXML private TableColumn<Appointment, LocalDateTime> end;
    
    private final ObservableList<Appointment> appointList = FXCollections.observableArrayList();
    
    private Connection c;
    private ResultSet rs;
    
    /**
     * This method changes scenes to the contact table.
     * 
     * @param event
     * @throws Exception 
     */
    public void contactSchedules(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ContactSchedules.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method retrieves appointment data for a selected contact.
     * 
     * @param selected
     * @throws SQLException 
     */
    public void getData(Contact selected) throws SQLException {
        Contact existingContact = selected;
        
        c = JDBC.openConnection();
        rs = c.createStatement().executeQuery("SELECT * FROM appointments WHERE Contact_ID = " + existingContact.getContactID());
        
        while(rs.next()) {
            
            Timestamp startStamp = rs.getTimestamp(6);
            Timestamp endStamp = rs.getTimestamp(7);
                
            LocalDateTime ldtStart = startStamp.toLocalDateTime();
            LocalDateTime ldtEnd = endStamp.toLocalDateTime();
                
            ZonedDateTime zdtStart = ldtStart.atZone(ZoneId.systemDefault());
            ZonedDateTime zdtEnd = ldtEnd.atZone(ZoneId.systemDefault());
                
            LocalDateTime localStart = zdtStart.toLocalDateTime();
            LocalDateTime localEnd = zdtEnd.toLocalDateTime();
            
            appointList.add(new Appointment(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),existingContact.getName(),rs.getString(5),localStart,localEnd,rs.getInt(12),rs.getInt(13)));
        }
        
        scheduleTable.setItems(appointList);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        appointID.setCellValueFactory(new PropertyValueFactory<>("appointID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("desc"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        custID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        
    } 
}
