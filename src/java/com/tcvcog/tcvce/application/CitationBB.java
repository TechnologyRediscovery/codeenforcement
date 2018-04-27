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

import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.Citation;
import com.tcvcog.tcvce.integration.CitationIntegrator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Adam Gutonski
 */
@ManagedBean
@ViewScoped
public class CitationBB extends BackingBeanUtils implements Serializable {

    private LinkedList<Citation> citationList;
    private Citation selectedCitation;
    private int formCitationID;
    private String formCitationNo;
    private int formCourtEntityOriginID;
    private int formCaseID;
    private int formUserID;
    private java.util.Date formDateOfRecord;
    private java.util.Date formTransTimeStamp;
    private boolean formIsActive;
    private String formNotes;
    
    
    /**
     * Creates a new instance of CitationBB
     */
    public CitationBB() {
    }

    /**
     * @return the citationList
     */
    public LinkedList<Citation> getCitationList() {
       try {
            CitationIntegrator citationIntegrator = getCitationIntegrator();
            citationList = citationIntegrator.getCitationList();
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Unable to load CitationList",
                        "This must be corrected by the system administrator"));
        }
        if(citationList != null){
        return citationList;
        }else{
         citationList = new LinkedList();
         return citationList;
        }
    }
    
    public void commitCitationUpdates(ActionEvent e){
        CitationIntegrator citationIntegrator = getCitationIntegrator();
        Citation citation = selectedCitation;
        
        citation.setCitationNo(formCitationNo);
        citation.setCourtEntityOriginID(formCourtEntityOriginID);
        citation.setCaseID(formCaseID);
        citation.setUserID(formUserID);
        citation.setDateOfRecord(formDateOfRecord.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        citation.setTransTimeStamp(formTransTimeStamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        citation.setIsActive(formIsActive);
        citation.setNotes(formNotes);
        //oif.setOccupancyInspectionFeeNotes(formOccupancyInspectionFeeNotes);
        try{
            citationIntegrator.updateCitation(citation);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Citation record updated!", ""));
        } catch (IntegrationException ex){
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unable to update citation record in database.",
                    "This must be corrected by the System Administrator"));
        }
    }
    
    public void deleteSelectedCitation(ActionEvent e){
        CitationIntegrator citationIntegrator = getCitationIntegrator();
        if(getSelectedCitation() != null){
            try {
                citationIntegrator.deleteCitation(getSelectedCitation());
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Citation record deleted forever!", ""));
            } catch (IntegrationException ex) {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to delete citation record--probably because it is used "
                                    + "somewhere in the database. Sorry.", 
                            "This category will always be with us."));
            }
            
        } else {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select a citation record from the table to delete", ""));
        }
    }
       
    public String addCitation(){
        Citation citation = new Citation();
        CitationIntegrator citationIntegrator = new CitationIntegrator();
        citation.setCitationID(formCitationID);
        citation.setCitationNo(formCitationNo);
        citation.setCourtEntityOriginID(formCourtEntityOriginID);
        citation.setCaseID(formCaseID);
        citation.setUserID(formUserID);
        citation.setDateOfRecord(formDateOfRecord.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        citation.setTransTimeStamp(formTransTimeStamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        citation.setIsActive(formIsActive);
        citation.setNotes(formNotes);
        try {
            citationIntegrator.insertCitation(citation);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfully added citation record to database!", ""));
        } catch(IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unable to add citation record to database, sorry!", "Check server print out..."));
            return "";
        }
        
        return "citationManage";
        
        
    }
    
        public void editCitation(ActionEvent e){
        if(getSelectedCitation() != null){
            setFormCitationID(selectedCitation.getCitationID());
            setFormCitationNo(selectedCitation.getCitationNo());
            setFormCourtEntityOriginID(selectedCitation.getCourtEntityOriginID());
            setFormIsActive(selectedCitation.isIsActive());
            setFormNotes(selectedCitation.getNotes());
            //setFormOccupancyInspectionFeeNotes(selectedOccupancyInspectionFee.getOccupancyInspectionFeeNotes());
            /*
            Have to figure out what to do w/ setting dates...
            setFormOccupancyInspectionFeeEffDate(formOccupancyInspectionFeeEffDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            */
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select a citation record to update", ""));
        }
    }

    /**
     * @param citationList the citationList to set
     */
    public void setCitationList(LinkedList<Citation> citationList) {
        this.citationList = citationList;
    }

    /**
     * @return the selectedCitation
     */
    public Citation getSelectedCitation() {
        return selectedCitation;
    }

    /**
     * @param selectedCitation the selectedCitation to set
     */
    public void setSelectedCitation(Citation selectedCitation) {
        this.selectedCitation = selectedCitation;
    }

    /**
     * @return the formCitationID
     */
    public int getFormCitationID() {
        return formCitationID;
    }

    /**
     * @param formCitationID the formCitationID to set
     */
    public void setFormCitationID(int formCitationID) {
        this.formCitationID = formCitationID;
    }

    /**
     * @return the formCitationNo
     */
    public String getFormCitationNo() {
        return formCitationNo;
    }

    /**
     * @param formCitationNo the formCitationNo to set
     */
    public void setFormCitationNo(String formCitationNo) {
        this.formCitationNo = formCitationNo;
    }

    /**
     * @return the formCourtEntityOriginID
     */
    public int getFormCourtEntityOriginID() {
        return formCourtEntityOriginID;
    }

    /**
     * @param formCourtEntityOriginID the formCourtEntityOriginID to set
     */
    public void setFormCourtEntityOriginID(int formCourtEntityOriginID) {
        this.formCourtEntityOriginID = formCourtEntityOriginID;
    }

    /**
     * @return the formCaseID
     */
    public int getFormCaseID() {
        return formCaseID;
    }

    /**
     * @param formCaseID the formCaseID to set
     */
    public void setFormCaseID(int formCaseID) {
        this.formCaseID = formCaseID;
    }

    /**
     * @return the formUserID
     */
    public int getFormUserID() {
        return formUserID;
    }

    /**
     * @param formUserID the formUserID to set
     */
    public void setFormUserID(int formUserID) {
        this.formUserID = formUserID;
    }

    /**
     * @return the formDateOfRecord
     */
    public java.util.Date getFormDateOfRecord() {
        return formDateOfRecord;
    }

    /**
     * @param formDateOfRecord the formDateOfRecord to set
     */
    public void setFormDateOfRecord(java.util.Date formDateOfRecord) {
        this.formDateOfRecord = formDateOfRecord;
    }

    /**
     * @return the formTransTimeStamp
     */
    public java.util.Date getFormTransTimeStamp() {
        return formTransTimeStamp;
    }

    /**
     * @param formTransTimeStamp the formTransTimeStamp to set
     */
    public void setFormTransTimeStamp(java.util.Date formTransTimeStamp) {
        this.formTransTimeStamp = formTransTimeStamp;
    }

    /**
     * @return the formIsActive
     */
    public boolean isFormIsActive() {
        return formIsActive;
    }

    /**
     * @param formIsActive the formIsActive to set
     */
    public void setFormIsActive(boolean formIsActive) {
        this.formIsActive = formIsActive;
    }

    /**
     * @return the formNotes
     */
    public String getFormNotes() {
        return formNotes;
    }

    /**
     * @param formNotes the formNotes to set
     */
    public void setFormNotes(String formNotes) {
        this.formNotes = formNotes;
    }
    
}
