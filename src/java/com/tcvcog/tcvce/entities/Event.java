/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcvcog.tcvce.entities;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * corresponding database table DDL
 * 
 * 
   INSERT INTO public.ceevent(
            eventid, ceeventcategory_catid, cecase_caseid, dateofrecord, 
            eventtimestamp, eventdescription, login_userid, disclosetomunicipality, 
            disclosetopublic, activeevent, requiresviewconfirmation, viewconfirmed, 
            hidden, notes)
    VALUES (?, ?, ?, ?, 
            ?, ?, ?, ?, 
            ?, ?, ?, ?, 
            ?, ?);

* 
 * @author cedba
 */
public class Event {
    
    private int eventID;
    private EventCategory category;
    
    private int caseID;
    
    private LocalDateTime dateOfRecord;
    private String prettyDateOfRecord;
    private LocalDateTime eventTimeStamp;
    private String eventDescription;
    
    private User eventOwnerUser;
    private boolean discloseToMunicipality;
    private boolean discloseToPublic;
    
    private boolean activeEvent;
    private boolean requiresViewConfirmation;
    private boolean viewConfirmed;
    private boolean hidden;
    
    private String notes;
    
    private LinkedList<Person> eventPersons;
    
    /**
     * @return the eventID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * @param eventID the eventID to set
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

 
    /**
     * @return the dateOfRecord
     */
    public LocalDateTime getDateOfRecord() {
        return dateOfRecord;
    }

    /**
     * @param dateOfRecord the dateOfRecord to set
     */
    public void setDateOfRecord(LocalDateTime dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    /**
     * @return the eventTimeStamp
     */
    public LocalDateTime getEventTimeStamp() {
        return eventTimeStamp;
    }

    /**
     * @param eventTimeStamp the eventTimeStamp to set
     */
    public void setEventTimeStamp(LocalDateTime eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    /**
     * @return the eventDescription
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * @param eventDescription the eventDescription to set
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    /**
     * @return the eventOwnerUser
     */
    public User getEventOwnerUser() {
        return eventOwnerUser;
    }

    /**
     * @param eventOwnerUser the eventOwnerUser to set
     */
    public void setEventOwnerUser(User eventOwnerUser) {
        this.eventOwnerUser = eventOwnerUser;
    }

    /**
     * @return the discloseToMunicipality
     */
    public boolean isDiscloseToMunicipality() {
        return discloseToMunicipality;
    }

    /**
     * @param discloseToMunicipality the discloseToMunicipality to set
     */
    public void setDiscloseToMunicipality(boolean discloseToMunicipality) {
        this.discloseToMunicipality = discloseToMunicipality;
    }

    /**
     * @return the discloseToPublic
     */
    public boolean isDiscloseToPublic() {
        return discloseToPublic;
    }

    /**
     * @param discloseToPublic the discloseToPublic to set
     */
    public void setDiscloseToPublic(boolean discloseToPublic) {
        this.discloseToPublic = discloseToPublic;
    }

    /**
     * @return the activeEvent
     */
    public boolean isActiveEvent() {
        return activeEvent;
    }

    /**
     * @param activeEvent the activeEvent to set
     */
    public void setActiveEvent(boolean activeEvent) {
        this.activeEvent = activeEvent;
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
     * @return the category
     */
    public EventCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(EventCategory category) {
        this.category = category;
    }

    /**
     * @return the caseID
     */
    public int getCaseID() {
        return caseID;
    }

    /**
     * @param caseID the caseID to set
     */
    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    /**
     * @return the prettyDateOfRecord
     */
    public String getPrettyDateOfRecord() {
        return prettyDateOfRecord;
    }

    /**
     * @param prettyDateOfRecord the prettyDateOfRecord to set
     */
    public void setPrettyDateOfRecord(String prettyDateOfRecord) {
        this.prettyDateOfRecord = prettyDateOfRecord;
    }

    /**
     * @return the eventPersons
     */
    public LinkedList<Person> getEventPersons() {
        return eventPersons;
    }

    /**
     * @param eventPersons the eventPersons to set
     */
    public void setEventPersons(LinkedList<Person> eventPersons) {
        this.eventPersons = eventPersons;
    }

    /**
     * @return the requiresViewConfirmation
     */
    public boolean isRequiresViewConfirmation() {
        return requiresViewConfirmation;
    }

    /**
     * @return the viewConfirmed
     */
    public boolean isViewConfirmed() {
        return viewConfirmed;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param requiresViewConfirmation the requiresViewConfirmation to set
     */
    public void setRequiresViewConfirmation(boolean requiresViewConfirmation) {
        this.requiresViewConfirmation = requiresViewConfirmation;
    }

    /**
     * @param viewConfirmed the viewConfirmed to set
     */
    public void setViewConfirmed(boolean viewConfirmed) {
        this.viewConfirmed = viewConfirmed;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    
    
}
