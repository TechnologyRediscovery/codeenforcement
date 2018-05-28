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
package com.tcvcog.tcvce.occupancy.application;
import com.tcvcog.tcvce.application.BackingBeanUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.tcvcog.tcvce.domain.IntegrationException;

import com.tcvcog.tcvce.occupancy.integration.InspectableCodeElementIntegrator;
import com.tcvcog.tcvce.occupancy.entities.InspectableCodeElement;

import java.io.Serializable;
import javax.faces.event.*;
//imported when adding @ManagedBean and @ViewScoped

import java.util.*;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Adam Gutonski
 */
@ManagedBean
@ViewScoped
public class InspectableCodeElementBB extends BackingBeanUtils implements Serializable{
    
    //i believe this LinkList will load up the objects from our database to ship back
    //to the XHTML page...?
    private LinkedList<InspectableCodeElement> inspectableCodeElementList;
    private InspectableCodeElement selectedICE;
    private int formCodeElementID;
    private String formInspectionTips;
    //private String formNonComplianceNotes;
    private boolean formInspectionPriority;
    //private java.util.Date formIceDate;
    
    /**
     * Creates a new instance of ExerciseBB
     */
    public InspectableCodeElementBB() {
    }
    //need to add the getInspectableCodeElementIntegrator(); method to BBUtils  
    public String addInspectableCodeElement(){
        
        InspectableCodeElement i = new InspectableCodeElement();
        InspectableCodeElementIntegrator ic = new InspectableCodeElementIntegrator();
//need to add the getInspectableCodeElementIntegrator(); method to BBUtils        
//InspectableCodeElementIntegrator ei = getInspectableCodeElementIntegrator();
        
        i.setCodeElementID(formCodeElementID);
        i.setInspectionPriority(isFormInspectionPriority());
        i.setInspectionTips(formInspectionTips);
        //i.setHighImportance(formHighImportance);
        //i.setIceDate(formIceDate.toInstant()
        //        .atZone(ZoneId.systemDefault())
        //        .toLocalDateTime());
               
        // placeholder for lastupdated
        
        try {
            ic.insertInspectableCodeElement(i);
            getFacesContext().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_INFO, 
                         "Successfully added "/* + i.getCodeElement() + */+ 
                                 " inspectable code element to the Database!", ""));

        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
               getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to add InspectableCodeElement to the database, my apologies!", "Check server print out!"));
            return "";
        }
        
        return "inspectableCodeElementManage";
    }
    
    public void commitInspectableCodeElementUpdates(ActionEvent e){
        InspectableCodeElementIntegrator iceIntegrator = getInspectableCodeElementIntegrator();
        InspectableCodeElement ice = selectedICE;
        
        ice.setCodeElementID(formCodeElementID);
        ice.setInspectionTips(formInspectionTips);
        ice.setInspectionPriority(formInspectionPriority);
        //oif.setOccupancyInspectionFeeNotes(formOccupancyInspectionFeeNotes);
        try{
            iceIntegrator.updateInspectableCodeElement(ice);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Inspectable code element record updated!", ""));
        } catch (IntegrationException ex){
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unable to update Inspectable code element record in database.",
                    "This must be corrected by the System Administrator"));
        }
    }
    
    public void deleteSelectedInspectableCodeElement(ActionEvent e){
        InspectableCodeElementIntegrator iceIntegrator = getInspectableCodeElementIntegrator();
        if(getSelectedICE() != null){
            try {
                iceIntegrator.deleteInspectableCodeElement(getSelectedICE());
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Inspectable code element record deleted forever!", ""));
            } catch (IntegrationException ex) {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to delete Inspectable code element record--probably because it is used "
                                    + "somewhere in the database. Sorry.", 
                            "This category will always be with us."));
            }
            
        } else {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select a citation record from the table to delete", ""));
        }
    }
    
    
    public LinkedList<InspectableCodeElement> getInspectableCodeElementList() {
        try {
            InspectableCodeElementIntegrator ic = getInspectableCodeElementIntegrator();
            inspectableCodeElementList = ic.getInspectableCodeElementList();
            //return eventCategoryList;
        } catch (IntegrationException ex) {
             getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to load event category list", 
                        "This must be corrected by the System Administrator"));
        }
        if(inspectableCodeElementList != null){
            return inspectableCodeElementList;
            
        } else {
            inspectableCodeElementList = new LinkedList();
            return inspectableCodeElementList;
        }
        
    }
    
    public void editInspectableCodeElement(ActionEvent e){
        if(getSelectedICE() != null){
            setFormCodeElementID(selectedICE.getCodeElementID());
            setFormInspectionTips(selectedICE.getInspectionTips());
            setFormInspectionPriority(selectedICE.getInspectionPriority());
            
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select an inspectable code element record to update", ""));
        }
    }

    /**
     * @return the formCodeElement
     */
    public int getFormCodeElementID() {
        return formCodeElementID;
    }

    /**
     * @param formCodeElement the formCodeElement to set
     */
    public void setFormCodeElementID(int formCodeElementID) {
        this.formCodeElementID = formCodeElementID;
    }

    /**
     * @return the formInspectionGuidelines
     */
    public String getFormInspectionTips() {
        return formInspectionTips;
    }

    /**
     * @param formInspectionTips the formInspectionGuidelines to set
     */
    public void setFormInspectionTips(String formInspectionTips) {
        this.formInspectionTips = formInspectionTips;
    }

    /**
     * @return the formNonComplianceNotes
     
    public String getFormNonComplianceNotes() {
        return formNonComplianceNotes;
    }

    
     * @param formNonComplianceNotes the formNonComplianceNotes to set
    
    public void setFormNonComplianceNotes(String formNonComplianceNotes) {
        this.formNonComplianceNotes = formNonComplianceNotes;
    }

   
     * @return the formHighImportance
     
    public boolean isFormHighImportance() {
        return formHighImportance;
    }

    /**
     * @param formHighImportance the formHighImportance to set
     
    public void setFormHighImportance(boolean formHighImportance) {
        this.formHighImportance = formHighImportance;
    }

    /**
     * @return the selectedICE
     */
    public InspectableCodeElement getSelectedICE() {
        return selectedICE;
    }

    /**
     * @param selectedICE the selectedICE to set
     */
    public void setSelectedICE(InspectableCodeElement selectedICE) {
        this.selectedICE = selectedICE;
    }

    /**
     * @return the formInspectionPriority
     */
    public boolean isFormInspectionPriority() {
        return formInspectionPriority;
    }

    /**
     * @param formInspectionPriority the formInspectionPriority to set
     */
    public void setFormInspectionPriority(boolean formInspectionPriority) {
        this.formInspectionPriority = formInspectionPriority;
    }

    /**
     * @return the formIceDate
     
    public java.util.Date getFormIceDate() {
        return formIceDate;
    }

    /**
     * @param formIceDate the formIceDate to set
     
    public void setFormIceDate(java.util.Date formIceDate) {
        this.formIceDate = formIceDate;
    }
    * */
}
    
    