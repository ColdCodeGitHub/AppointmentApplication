/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.value.ObservableValue;

/**
 *
 * @author LabUser
 */
public class Customer {
    
    // Fields
    private ObservableValue<Integer> customerID;
    private ObservableValue<String> name;
    private ObservableValue<String> address;
    private ObservableValue<String> postal;
    private ObservableValue<String> phone;
    private ObservableValue<Integer> divisionID;
    
    // Constructor
    /**
     * 
     * @param customerID
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param divisionID 
     */
    public Customer(ObservableValue<Integer> customerID, ObservableValue<String> name, ObservableValue<String> address, ObservableValue<String> postal, ObservableValue<String> phone, ObservableValue<Integer> divisionID) {
        
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionID = divisionID;
    }
    
    // Setters
    /**
     * This method is a setter for customerID.
     * 
     * @param customerID 
     */
    public void setCustomerID(ObservableValue<Integer> customerID) {
        this.customerID = customerID;
    }
    
    /**
     * This method is a setter for name.
     * 
     * @param name 
     */
    public void setName(ObservableValue<String> name) {
        this.name = name;
    }
    
    /**
     * This method is a setter for address.
     * 
     * @param address 
     */
    public void setAddress(ObservableValue<String> address) {
        this.address = address;
    }
    
    /**
     * This method is a setter for postal.
     * 
     * @param postal 
     */
    public void setPostal(ObservableValue<String> postal) {
        this.postal = postal;
    }
    
    /**
     * This method is a setter for phone.
     * 
     * @param phone 
     */
    public void setPhone(ObservableValue<String> phone) {
        this.phone = phone;
    }
    
    /**
     * This method is a setter for divisionID.
     * 
     * @param divisionID 
     */
    public void setDivision(ObservableValue<Integer> divisionID) {
        this.divisionID = divisionID;
    }
    
    // Getters
    /**
     * This method is a getter for customerID.
     * 
     * @return customerID
     */
    public ObservableValue<Integer> getCustomerID() {
        return customerID;
    }
    
    /**
     * This method is a getter for name.
     * 
     * @return name
     */
    public ObservableValue<String> getName() {
        return name;
    }
    
    /**
     * This method is a getter for address.
     * 
     * @return address
     */
    public ObservableValue<String> getAddress() {
        return address;
    }
    
    /**
     * This method is a getter for postal.
     * 
     * @return postal
     */
    public ObservableValue<String> getPostal() {
        return postal;
    }
    
    /**
     * This method is a getter for phone.
     * 
     * @return phone
     */
    public ObservableValue<String> getPhone() {
        return phone;
    }
    
    /**
     * This method is a getter for divisionID.
     * 
     * @return divisionID
     */
    public ObservableValue<Integer> getDivisionID() {
        return divisionID;
    }
    
    
    
}
