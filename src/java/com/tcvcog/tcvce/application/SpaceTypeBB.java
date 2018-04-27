/*
 * Copyright (C) 2018 Adam Gutonski 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tcvcog.tcvce.application;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.integration.SpaceTypeIntegrator;

import com.tcvcog.tcvce.occupancy.SpaceType;

import java.io.Serializable;
import javax.faces.event.*;
import java.time.*;
//imported when adding @ManagedBean and @ViewScoped

import java.util.*;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Adam Gutonski
 */
@ManagedBean
@ViewScoped
public class SpaceTypeBB extends BackingBeanUtils implements Serializable {

    private LinkedList<SpaceType> spaceTypeList;
    private SpaceType selectedSpaceType;
    private int formSpaceTypeID;
    private String formSpaceTypeTitle;
    private String formSpaceTypeDescription;
    
    
    
    /**
     * Creates a new instance of SpaceTypeBB
     */
    public SpaceTypeBB() {
    }

    /**
     * @return the spaceTypeList
     */
    public LinkedList<SpaceType> getSpaceTypeList() {
        
        try {
            SpaceTypeIntegrator si = getSpaceTypeIntegrator();
            spaceTypeList = si.getSpaceTypeList();
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to load space type list", 
                        "This must be corrected by the System Administrator"));
        }
         if(spaceTypeList != null){
            return spaceTypeList;
            
        } else {
            spaceTypeList = new LinkedList();
            return spaceTypeList;
        }
    }
    
    public void editSpaceType(ActionEvent e){
        if(getSelectedSpaceType() != null){
            setFormSpaceTypeID(selectedSpaceType.getSpaceTypeID());
            setFormSpaceTypeTitle(selectedSpaceType.getSpaceTypeTitle());
            setFormSpaceTypeDescription(selectedSpaceType.getSpaceTypeDescription());
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select a space type to update", ""));
        }
    }
    
    public void deleteSelectedSpaceType(ActionEvent e){
        SpaceTypeIntegrator sti = getSpaceTypeIntegrator();
        if(getSelectedSpaceType() != null){
            try {
                sti.deleteSpaceType(getSelectedSpaceType());
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Space type deleted forever!", ""));
            } catch (IntegrationException ex) {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to delete space type--probably because it is used "
                                    + "somewhere in the database. Sorry.", 
                            "This category will always be with us."));
            }
            
        } else {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select a space type from the table to delete", ""));
        }
    }
    
    public void commitSpaceTypeUpdates(ActionEvent e){
        SpaceTypeIntegrator sti = getSpaceTypeIntegrator();
        SpaceType spaceType = selectedSpaceType;
        
        spaceType.setSpaceTypeTitle(formSpaceTypeTitle);
        spaceType.setSpaceTypeDescription(formSpaceTypeDescription);
        //oif.setOccupancyInspectionFeeNotes(formOccupancyInspectionFeeNotes);
        try{
            System.out.println("ATTEMPTING TO UPDATE SPACE TYPE");
            sti.updateSpaceType(spaceType);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Space type updated!", ""));
        } catch (IntegrationException ex){
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unable to update space type in database.",
                    "This must be corrected by the System Administrator"));
        }
    }
    

    public String addSpaceType(){
        
        SpaceType s = new SpaceType();
        SpaceTypeIntegrator si = new SpaceTypeIntegrator();
//need to add the getInspectableCodeElementIntegrator(); method to BBUtils        
//InspectableCodeElementIntegrator ei = getInspectableCodeElementIntegrator();
        
        s.setSpaceTypeID(formSpaceTypeID);
        s.setSpaceTypeTitle(formSpaceTypeTitle);
        s.setSpaceTypeDescription(formSpaceTypeDescription);
        //i.setHighImportance(formHighImportance);
        //i.setIceDate(formIceDate.toInstant()
        //        .atZone(ZoneId.systemDefault())
        //        .toLocalDateTime());
               
        // placeholder for lastupdated
        
        try {
            si.insertSpaceType(s);
            getFacesContext().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_INFO, 
                         "Successfully added "/* + i.getCodeElement() + */+ 
                                 " space type to the Database!", ""));

        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
               getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to add spaceType to the database, my apologies!", "Check server print out!"));
            return "";
        }
        
        return "spaceTypeManage";
    }
    

    /**
     * @param spaceTypeList the spaceTypeList to set
     */
    public void setSpaceTypeList(LinkedList<SpaceType> spaceTypeList) {
        this.spaceTypeList = spaceTypeList;
    }

    /**
     * @return the selectedSpaceType
     */
    public SpaceType getSelectedSpaceType() {
        return selectedSpaceType;
    }

    /**
     * @param selectedSpaceType the selectedSpaceType to set
     */
    public void setSelectedSpaceType(SpaceType selectedSpaceType) {
        this.selectedSpaceType = selectedSpaceType;
    }

    /**
     * @return the formSpaceTypeID
     */
    public int getFormSpaceTypeID() {
        return formSpaceTypeID;
    }

    /**
     * @param formSpaceTypeID the formSpaceTypeID to set
     */
    public void setFormSpaceTypeID(int formSpaceTypeID) {
        this.formSpaceTypeID = formSpaceTypeID;
    }

    /**
     * @return the formSpaceTypeTitle
     */
    public String getFormSpaceTypeTitle() {
        return formSpaceTypeTitle;
    }

    /**
     * @param formSpaceTypeTitle the formSpaceTypeTitle to set
     */
    public void setFormSpaceTypeTitle(String formSpaceTypeTitle) {
        this.formSpaceTypeTitle = formSpaceTypeTitle;
    }

    /**
     * @return the formSpaceTypeDescription
     */
    public String getFormSpaceTypeDescription() {
        return formSpaceTypeDescription;
    }

    /**
     * @param formSpaceTypeDescription the formSpaceTypeDescription to set
     */
    public void setFormSpaceTypeDescription(String formSpaceTypeDescription) {
        this.formSpaceTypeDescription = formSpaceTypeDescription;
    }
    
}
