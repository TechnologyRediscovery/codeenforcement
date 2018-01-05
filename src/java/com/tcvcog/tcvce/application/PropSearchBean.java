/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcvcog.tcvce.application;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;
import com.tcvcog.tcvce.entities.Property;
import javax.faces.event.ActionEvent;
import com.tcvcog.tcvce.integration.PropertyIntegrator;
import javax.faces.component.UIInput;

/** 
 *
 * @author cedba
 */

public class PropSearchBean extends BackingBeanUtils implements Serializable {

    private String parid;
    private String address;
    private String addrPart;
    Connection con = null;
    
    private Property selectedProperty;
    private LinkedList<Property> propList;
    private UIInput addressInput;
    
    
    
    /**
     * Creates a new instance of PropSearchBean
     */
    public PropSearchBean() {
        System.out.println("PropSearchBean.PropSearchBean");

        
    } // close constructor
    
    public String lookUpProperty(){
        System.out.println("PropSearchBean.lookUpProperty");
        PropertyIntegrator pi = new PropertyIntegrator();
        
        //addrPart = (String) addressInput.getValue();
        // hardcoded muni id for testing only!!!
        propList = pi.searchForProperties(addrPart, 953);
        
     return null;
       
        
    }

    /**
     * @return the parid
     */
    public String getParid() {
        return this.parid;
    }

    /**
     * @param parid the parid to set
     */
    public void setParid(String parid) {
        this.parid = parid;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the addrPart
     */
    public String getAddrPart() {
        return addrPart;
    }

    /**
     * @param addrPart the addrPart to set
     */
    public void setAddrPart(String addrPart) {
        this.addrPart = addrPart;
    }

    /**
     * @return the propList
     */
    public List<Property> getPropList() {
        return propList;
    }

    /**
     * @param propList the propList to set
     */
    public void setPropList(LinkedList<Property> propList) {
        this.propList = propList;
    }

    /**
     * @return the selectedProperty
     */
    public Property getSelectedProperty() {
        return selectedProperty;
    }

    /**
     * @param selectedProperty the selectedProperty to set
     */
    public void setSelectedProperty(Property selectedProperty) {
        this.selectedProperty = selectedProperty;
    }

    /**
     * @return the addressInput
     */
    public UIInput getAddressInput() {
        return addressInput;
    }

    /**
     * @param addressInput the addressInput to set
     */
    public void setAddressInput(UIInput addressInput) {
        this.addressInput = addressInput;
    }
    
    
    
}
