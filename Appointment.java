/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author LabUser
 */
public class Appointment {
    
    // Fields
    private int appointID;
    private String title;
    private String desc;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    
    /**
     * @param appointID    
     * @param title    
     * @param desc    
     * @param location    
     * @param contact    
     * @param type    
     * @param start    
     * @param end    
     * @param cID    
     * @param uID    
    */
    public Appointment(int appointID, String title, String desc, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int cID, int uID) {
        this.appointID = appointID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = cID;
        this.userID = uID;
    }
    
    // Setters
    
    /**
     * This method is a setter for appointID.
     * 
     * @param appointID 
     */
    public void setAppointID(int appointID) {
        this.appointID = appointID;
    }
    
    /**
     * This method is a setter for title.
     * 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * This method is a setter for desc.
     * 
     * @param desc 
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * This method is a setter for location.
     * 
     * @param location 
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * This method is a setter for contact.
     * 
     * @param contact 
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    /**
     * This method is a setter for type.
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * This method is a setter for start.
     * 
     * @param start 
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    /**
     * This method is a setter for end.
     * 
     * @param end 
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    /**
     * This method is a setter for customerID.
     * 
     * @param customerID 
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    /**
     * This method is a setter for userID.
     * 
     * @param userID 
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    // Getters
    
    /**
     * This method is a getter for appointID.
     * 
     * @return appointID
     */
    public int getAppointID() {
        return appointID;
    }
    
    /**
     * This method is a getter for title.
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * This method is a getter for desc.
     * 
     * @return desc
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * This method is a getter for location.
     * 
     * @return location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * This method is a getter for contact.
     * 
     * @return contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * This method is a getter for type.
     * 
     * @return type
     */
    public String getType() {
        return type;
    }
    
    /**
     * This method is a getter for start.
     * 
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }
    
    /**
     * This method is a getter for end.
     * 
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }
    
    /**
     * This method is a getter for customerID.
     * 
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }
    
    /**
     * This method is a getter for userID.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }
    
}
