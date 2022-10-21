/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LabUser
 */
public class Contact {
    
    // Fields
    int contactID;
    String name;
    String email;
    
    // Constructor
    /**
     * 
     * @param contactID
     * @param name
     * @param email 
     */
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }
    
    // Setters
    /**
     * This method is a setter for contactID.
     * 
     * @param contactID 
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    
    /**
     * This method is a setter for name.
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * This method is a setter for email.
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getters
    /**
     * This method is a getter for contactID.
     * 
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }
    
    /**
     * This method is a getter for name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * This method is a getter for email.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    // Overloaded toString method
    /**
     * This method returns the name of a contact.
     * This is an overloaded method.
     * 
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
