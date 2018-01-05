/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcvcog.tcvce.entities;

import java.util.LinkedList;

/**
 *
 * @author cedba
 */

public class Property {
    
    private int propertyID;
    private Municipality muni;
    private String parID;
    
    private String lotAndBlock;
    private String address;
    private String propertyUseType;

    private boolean rental;
    private boolean multiUnit;
    
    private String useGroup;
    private String constructionType;
    private String countyCode;
    
    private LinkedList<CECase> cases;
    private LinkedList<PropertyUnit> propertyUnits;
    
    
  /**
     * Creates a new instance of Property
     */
    public Property() {
      
    }

    /**
     * @return the propertyID
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * @param propertyID the propertyID to set
     */
    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    /**
     * @return the muni
     */
    public Municipality getMuni() {
        return muni;
    }

    /**
     * @param muni the muni to set
     */
    public void setMuni(Municipality muni) {
        this.muni = muni;
    }

    /**
     * @return the parID
     */
    public String getParID() {
        return parID;
    }

    /**
     * @param parID the parID to set
     */
    public void setParID(String parID) {
        this.parID = parID;
    }

    /**
     * @return the lotAndBlock
     */
    public String getLotAndBlock() {
        return lotAndBlock;
    }

    /**
     * @param lotAndBlock the lotAndBlock to set
     */
    public void setLotAndBlock(String lotAndBlock) {
        this.lotAndBlock = lotAndBlock;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the propertyUseType
     */
    public String getPropertyUseType() {
        return propertyUseType;
    }

    /**
     * @param propertyUseType the propertyUseType to set
     */
    public void setPropertyUseType(String propertyUseType) {
        this.propertyUseType = propertyUseType;
    }

    /**
     * @return the rental
     */
    public boolean isRental() {
        return rental;
    }

    /**
     * @param rental the rental to set
     */
    public void setRental(boolean rental) {
        this.rental = rental;
    }

    /**
     * @return the multiUnit
     */
    public boolean isMultiUnit() {
        return multiUnit;
    }

    /**
     * @param multiUnit the multiUnit to set
     */
    public void setMultiUnit(boolean multiUnit) {
        this.multiUnit = multiUnit;
    }

    /**
     * @return the useGroup
     */
    public String getUseGroup() {
        return useGroup;
    }

    /**
     * @param useGroup the useGroup to set
     */
    public void setUseGroup(String useGroup) {
        this.useGroup = useGroup;
    }

    /**
     * @return the constructionType
     */
    public String getConstructionType() {
        return constructionType;
    }

    /**
     * @param constructionType the constructionType to set
     */
    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    /**
     * @return the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }

    /**
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    /**
     * @return the cases
     */
    public LinkedList<CECase> getCases() {
        return cases;
    }

    /**
     * @param cases the cases to set
     */
    public void setCases(LinkedList<CECase> cases) {
        this.cases = cases;
    }

    /**
     * @return the propertyUnits
     */
    public LinkedList<PropertyUnit> getPropertyUnits() {
        return propertyUnits;
    }

    /**
     * @param propertyUnits the propertyUnits to set
     */
    public void setPropertyUnits(LinkedList<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
    }
    
  

    
    
}
