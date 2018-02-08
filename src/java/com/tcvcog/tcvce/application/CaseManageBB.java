/*
 * Copyright (C) 2018 Turtle Creek Valley
Council of Governments, PA
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

import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.EnforcableCodeElement;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class CaseManageBB extends BackingBeanUtils implements Serializable{

    private CECase currentCase;
    
    private LinkedList<Event> eventList;
    private Event selectedEvent;
    
    private LinkedList<CodeViolation> violationList;
    private CodeViolation selectedViolation;
    
    // add event form fields
    private String formEventDesc;
    private Date formEventDate;
    private EventType formEventType;
    private boolean formDiscloseToMuni;
    private boolean formDiscloseToPublic;
    private boolean activeEvent;
    private String formEventNotes;
    private LinkedList<Person> formEventpersonList;
    
    private EventType[] eventTypeArr;
    private EventType selectedEventType;
    
    
    
    
    
    /**
     * Creates a new instance of CaseManageBB
     */
    public CaseManageBB() {
    }
    
    public String editSelectedViolation(){
        
        return "";
    }
    
    public String editSelectedEvent(){
        
        return "";
    }
    

    /**
     * @return the currentCase
     */
    public CECase getCurrentCase() {
        SessionManager sm = getSessionManager();
        currentCase = sm.getVisit().getActiveCase();
        return currentCase;
    }

   
    /**
     * @param currentCase the currentCase to set
     */
    public void setCurrentCase(CECase currentCase) {
        this.currentCase = currentCase;
    }

    /**
     * @return the eventList
     */
    public LinkedList<Event> getEventList() {
        return eventList;
    }

    /**
     * @return the selectedEvent
     */
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * @return the violationList
     */
    public LinkedList<CodeViolation> getViolationList() {
        return violationList;
    }

    /**
     * @return the selectedViolation
     */
    public CodeViolation getSelectedViolation() {
        return selectedViolation;
    }

    /**
     * @param eventList the eventList to set
     */
    public void setEventList(LinkedList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * @param selectedEvent the selectedEvent to set
     */
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    /**
     * @param violationList the violationList to set
     */
    public void setViolationList(LinkedList<CodeViolation> violationList) {
        this.violationList = violationList;
    }

    /**
     * @param selectedViolation the selectedViolation to set
     */
    public void setSelectedViolation(CodeViolation selectedViolation) {
        this.selectedViolation = selectedViolation;
    }

    /**
     * @return the formEventDesc
     */
    public String getFormEventDesc() {
        return formEventDesc;
    }

    /**
     * @return the formEventDate
     */
    public Date getFormEventDate() {
        return formEventDate;
    }

    /**
     * @return the formEventType
     */
    public EventType getFormEventType() {
        return formEventType;
    }

    /**
     * @return the formDiscloseToMuni
     */
    public boolean isFormDiscloseToMuni() {
        return formDiscloseToMuni;
    }

    /**
     * @return the formDiscloseToPublic
     */
    public boolean isFormDiscloseToPublic() {
        return formDiscloseToPublic;
    }

    /**
     * @return the activeEvent
     */
    public boolean isActiveEvent() {
        return activeEvent;
    }

    /**
     * @return the formEventNotes
     */
    public String getFormEventNotes() {
        return formEventNotes;
    }

    /**
     * @return the formEventpersonList
     */
    public LinkedList<Person> getFormEventpersonList() {
        return formEventpersonList;
    }

    /**
     * @param formEventDesc the formEventDesc to set
     */
    public void setFormEventDesc(String formEventDesc) {
        this.formEventDesc = formEventDesc;
    }

    /**
     * @param formEventDate the formEventDate to set
     */
    public void setFormEventDate(Date formEventDate) {
        this.formEventDate = formEventDate;
    }

    /**
     * @param formEventType the formEventType to set
     */
    public void setFormEventType(EventType formEventType) {
        this.formEventType = formEventType;
    }

    /**
     * @param formDiscloseToMuni the formDiscloseToMuni to set
     */
    public void setFormDiscloseToMuni(boolean formDiscloseToMuni) {
        this.formDiscloseToMuni = formDiscloseToMuni;
    }

    /**
     * @param formDiscloseToPublic the formDiscloseToPublic to set
     */
    public void setFormDiscloseToPublic(boolean formDiscloseToPublic) {
        this.formDiscloseToPublic = formDiscloseToPublic;
    }

    /**
     * @param activeEvent the activeEvent to set
     */
    public void setActiveEvent(boolean activeEvent) {
        this.activeEvent = activeEvent;
    }

    /**
     * @param formEventNotes the formEventNotes to set
     */
    public void setFormEventNotes(String formEventNotes) {
        this.formEventNotes = formEventNotes;
    }

    /**
     * @param formEventpersonList the formEventpersonList to set
     */
    public void setFormEventpersonList(LinkedList<Person> formEventpersonList) {
        this.formEventpersonList = formEventpersonList;
    }

    /**
     * @return the eventTypeArr
     */
    public EventType[] getEventTypeArr() {
        eventTypeArr = EventType.values();
        return eventTypeArr;
    }

    /**
     * @param eventTypeArr the eventTypeArr to set
     */
    public void setEventTypeArr(EventType[] eventTypeArr) {
        this.eventTypeArr = eventTypeArr;
    }

    /**
     * @return the selectedEventType
     */
    public EventType getSelectedEventType() {
        return selectedEventType;
    }

    /**
     * @param selectedEventType the selectedEventType to set
     */
    public void setSelectedEventType(EventType selectedEventType) {
        this.selectedEventType = selectedEventType;
    }
}