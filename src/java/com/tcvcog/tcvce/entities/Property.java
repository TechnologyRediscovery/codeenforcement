/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcvcog.tcvce.entities;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *SELECT propertyid, municipality_municode, parid, lotandblock, address, 
       propertyusetype_propertyuseid, rental, multiunit, usegroup, constructiontype, 
       countycode, apartmentno
  FROM public.property;
 * 
 * @author cedba
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

    private boolean rental;
    private boolean multiUnit;
    
    private String useGroup;
    private String constructionType;
    private String countyCode;
    
    private String notes;
    
    private LinkedList<CECase> propertyCaseList;
    private HashMap<PropertyUnit, Integer> propertyUnitHashMap;
    private LinkedList<Person> propertyPersonList;
    
    
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
     * @return the propertyCaseList
     */
    public LinkedList<CECase> getPropertyCaseList() {
        return propertyCaseList;
    }

    /**
     * @param propertyCaseList the propertyCaseList to set
     */
    public void setPropertyCaseList(LinkedList<CECase> propertyCaseList) {
        this.propertyCaseList = propertyCaseList;
    }

   
    /**
     * @return the propertyPersonList
     */
    public LinkedList<Person> getPropertyPersonList() {
        return propertyPersonList;
    }

    /**
     * @param propertyPersonList the propertyPersonList to set
     */
    public void setPropertyPersonList(LinkedList<Person> propertyPersonList) {
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
     * @return the propertyUnitHashMap
     */
    public HashMap<PropertyUnit, Integer> getPropertyUnitHashMap() {
        return propertyUnitHashMap;
    }

    /**
     * @param propertyUnitHashMap the propertyUnitHashMap to set
     */
    public void setPropertyUnitHashMap(HashMap<PropertyUnit, Integer> propertyUnitHashMap) {
        this.propertyUnitHashMap = propertyUnitHashMap;
    }
    
  

    
    
}
