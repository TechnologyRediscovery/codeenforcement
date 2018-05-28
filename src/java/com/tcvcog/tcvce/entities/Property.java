/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcvcog.tcvce.entities;

import com.tcvcog.tcvce.occupancy.entities.OccupancyPermit;
import java.util.ArrayList;

/**
 * Foundational entity for the system: Property
 * @author Eric Darsow
 */

public class Property {
    
    private int propertyID;
    private Municipality muni;
    private int muniCode;
    private String parID;
    
    private String lotAndBlock;
    private String address;
    private String propertyUseTypeName;
    private int propertyUseTypeID;

    private String useGroup;
    private String constructionType;
    private String countyCode;
    
    private String notes;
    
    private ArrayList<CECase> propertyCaseList;
    private ArrayList<PropertyUnit> propertyUnitList;
    private ArrayList<Person> propertyPersonList;
    
    
    
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
     * @return the propertyUseTypeName
     */
    public String getPropertyUseTypeName() {
        return propertyUseTypeName;
    }

    /**
     * @param propertyUseTypeName the propertyUseTypeName to set
     */
    public void setPropertyUseTypeName(String propertyUseTypeName) {
        this.propertyUseTypeName = propertyUseTypeName;
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
     * @return the propertyCaseList
     */
    public ArrayList<CECase> getPropertyCaseList() {
        return propertyCaseList;
    }

    /**
     * @param propertyCaseList the propertyCaseList to set
     */
    public void setPropertyCaseList(ArrayList<CECase> propertyCaseList) {
        this.propertyCaseList = propertyCaseList;
    }

   
    /**
     * @return the propertyPersonList
     */
    public ArrayList<Person> getPropertyPersonList() {
        return propertyPersonList;
    }

    /**
     * @param propertyPersonList the propertyPersonList to set
     */
    public void setPropertyPersonList(ArrayList<Person> propertyPersonList) {
        this.propertyPersonList = propertyPersonList;
    }

    /**
     * @return the muniCode
     */
    public int getMuniCode() {
        return muniCode;
    }

    /**
     * @param muniCode the muniCode to set
     */
    public void setMuniCode(int muniCode) {
        this.muniCode = muniCode;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the propertyUseTypeID
     */
    public int getPropertyUseTypeID() {
        return propertyUseTypeID;
    }

    /**
     * @param propertyUseTypeID the propertyUseTypeID to set
     */
    public void setPropertyUseTypeID(int propertyUseTypeID) {
        this.propertyUseTypeID = propertyUseTypeID;
    }

    /**
     * @return the propertyUnitList
     */
    public ArrayList<PropertyUnit> getPropertyUnitList() {
        return propertyUnitList;
    }

    /**
     * @param propertyUnitList the propertyUnitList to set
     */
    public void setPropertyUnitList(ArrayList<PropertyUnit> propertyUnitList) {
        this.propertyUnitList = propertyUnitList;
    }

}
