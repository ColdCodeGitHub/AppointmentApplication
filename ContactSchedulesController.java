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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class ContactSchedulesController implements Initializable {

    @FXML private TableView<Contact> contactTable;
    
    @FXML private TableColumn<Contact, Integer> contactID;
    @FXML private TableColumn<Contact, String> name;
    @FXML private TableColumn<Contact, String> email;
    
    private final ObservableList<Contact> contactList = FXCollections.observableArrayList();
    
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
     * This method shows the schedule of a selected contact
     * 
     * @param event
     * @throws Exception 
     */
    public void scheduleForm(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ScheduleForm.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.load();

            ScheduleFormController controller = loader.getController();
            controller.getData(contactTable.getSelectionModel().getSelectedItem());

            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a contact to view a schedule!");
            alert.showAndWait();
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
            rs = c.createStatement().executeQuery("SELECT * FROM contacts");
            
            while(rs.next()) {
                contactList.add(new Contact(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContactSchedulesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        contactTable.setItems(contactList);
    }    
    
}
