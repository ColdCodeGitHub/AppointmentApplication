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
public class Country {
    
    // Fields
    private int countryID;
    private String name;
    
    // Constructor
    /**
     * 
     * @param countryID
     * @param name 
     */
    public Country(int countryID, String name) {
        this.countryID = countryID;
        this.name = name;
    }
    
    // Setters
    /**
     * This method is a setter for countryID.
     * 
     * @param countryID 
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    
    /**
     * This method is a setter for name.
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    // Getters
    /**
     * This method is a getter for countryID.
     * 
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }
    
    /**
     * This method is a getter for name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    // Overloaded toString() method
    /**
     * This method retrieves the name of a country.
     * This method is overloaded.
     * 
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
    
}
