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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.JDBC;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class AppointmentFormController implements Initializable {

    @FXML private TableView<Appointment> appointmentTable;
    
    @FXML private TableColumn<Appointment, Integer> appointID;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> desc;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> contact;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, LocalDateTime> start;
    @FXML private TableColumn<Appointment, LocalDateTime> end;
    @FXML private TableColumn<Appointment, Integer> cID;
    @FXML private TableColumn<Appointment, Integer> uID;
    
    @FXML private RadioButton month;
    @FXML private RadioButton week;
    @FXML private RadioButton all;
    
    private Connection c;
    private ResultSet rs, rs2;
    private PreparedStatement pst;
    
    private final ObservableList<Appointment> appointList = FXCollections.observableArrayList();
    
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
     * This method changes scenes to the Type-Totals report.
     * 
     * @param event
     * @throws Exception 
     */
    public void typeTotals(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/TypeTotals.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method changes scenes to the Contact-Schedules report.
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
     * This method changes scenes to the Add Appointment form.
     * 
     * @param event
     * @throws Exception 
     */
    public void addAppoint(ActionEvent event) throws Exception {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * This method changes scenes to the Update Appointment form.
     * 
     * @param event
     * @throws Exception 
     */
    public void updateAppoint(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.load();

            UpdateAppointmentController controller = loader.getController();
            controller.getData(appointmentTable.getSelectionModel().getSelectedItem());

            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select an appointment!");
            alert.showAndWait();
        }
    }
    
    /**
     * This method deletes any selected appointment.
     * 
     * @param event
     * @throws Exception 
     */
    public void deleteAppoint(ActionEvent event) throws Exception {
        
            Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();

            if(selected == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Select an appointment!");
                alert.showAndWait();
            }
        
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                    int id = selected.getAppointID();
                    String appointType = selected.getType();
                    c = JDBC.openConnection();

                    pst = c.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = " + selected.getAppointID());
                    pst.executeUpdate();

                    appointmentTable.getItems().remove(selected);
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("Appointment with Appointment ID: " + id + " and Type: " + appointType + " has been deleted.");
                    alert2.showAndWait();
                }
            }
    }
    
    /**
     * This method will show all appointments within the current month by using a lambda expression.
     * The lambda expression takes table-view rows as a parameter and returns only
     * those within the allotted thirty-one days.
     * Using the lambda expression makes the method more simple by directly filtering rows,
     * rather than accessing the database and then passing the data back into the application
     * 
     * @param event
     * @throws Exception 
     */
    public void filterByMonth(ActionEvent event) throws Exception {
        week.setSelected(false);
        all.setSelected(false);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nowPlus28 = currentDateTime.plusDays(31);
        FilteredList<Appointment> filteredAppoints = new FilteredList<>(appointList);
        
        filteredAppoints.setPredicate(row -> {

            LocalDateTime rowDate = row.getStart();

            return rowDate.isAfter(currentDateTime) && rowDate.isBefore(nowPlus28);
            
        });
        appointmentTable.setItems(filteredAppoints);
    }
    
    /**
     * This method will show all appointments in the current week by using a lambda expression.
     * The lambda expression takes table-view rows as a parameter and returns only
     * those within the allotted seven days.
     * Using the lambda expression makes the method more simple by directly filtering rows,
     * rather than accessing the database and then passing the data back into the application.
     * 
     * @param event
     * @throws Exception 
     */
    public void filterByWeek(ActionEvent event) throws Exception {
        month.setSelected(false);
        all.setSelected(false);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nowPlus7 = currentDateTime.plusDays(7);
        FilteredList<Appointment> filteredAppoints = new FilteredList<>(appointList);
        
        filteredAppoints.setPredicate(row -> {

            LocalDateTime rowDate = row.getStart();

            return rowDate.isAfter(currentDateTime) && rowDate.isBefore(nowPlus7);

            
        });
        appointmentTable.setItems(filteredAppoints);
    }
    
    /**
     * This method will show all appointments, regardless of month or week.
     * 
     * @param event
     * @throws Exception 
     */
    public void showAll(ActionEvent event) throws Exception {
        month.setSelected(false);
        week.setSelected(false);
        
        appointmentTable.setItems(appointList);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String contactName = null;
        
        c = JDBC.openConnection();
        
        try {
            rs = c.createStatement().executeQuery("SELECT * FROM appointments");
            
            while(rs.next()) {
                rs2 = c.createStatement().executeQuery("SELECT * FROM contacts");
                while(rs2.next()) {
                    if(rs2.getInt(1) == rs.getInt(14)) {
                        contactName = rs2.getString(2);
                    }   
                }
                
                Timestamp startStamp = rs.getTimestamp(6);
                Timestamp endStamp = rs.getTimestamp(7);
                
                LocalDateTime ldtStart = startStamp.toLocalDateTime();
                LocalDateTime ldtEnd = endStamp.toLocalDateTime();
                
                ZonedDateTime zdtStart = ldtStart.atZone(ZoneId.systemDefault());
                ZonedDateTime zdtEnd = ldtEnd.atZone(ZoneId.systemDefault());
                
                LocalDateTime localStart = zdtStart.toLocalDateTime();
                LocalDateTime localEnd = zdtEnd.toLocalDateTime();
                
                Appointment appoint = new Appointment(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),contactName,rs.getString(5),localStart,localEnd,rs.getInt(12),rs.getInt(13));
                appointList.add(appoint);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        appointID.setCellValueFactory(new PropertyValueFactory<>("appointID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        cID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        uID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        
        appointmentTable.setItems(appointList);
    }    
    
}
