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

import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.integration.EventIntegrator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;


/**
 *
 * @author sylvia
 */
public class EventConfigurationBB extends BackingBeanUtils implements Serializable{

    private EventCategory selectedEventCategory;
    private LinkedList<EventCategory> eventCategoryList;
     
    private EventType[] eventTypeList;
    
    private EventType formEventType;
    private String formEventCategoryTitle;
    private String formEventCategoryDescr;
    
    private EventType newFormSelectedEventType;
    private String newFormEventCategoryTitle;
    private String newFormEventCategoryDescr;
    
    
    public EventConfigurationBB() {
    }
    
    public void editSelectedEventCategory(ActionEvent e){
        if(selectedEventCategory != null){
            formEventType = selectedEventCategory.getEventType();
            formEventCategoryTitle = selectedEventCategory.getEventTypeTitle();
            formEventCategoryDescr = selectedEventCategory.getEventTypeDesc();
            
        } else {
           getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select an event category to update", ""));
        }
        
    }
    
    public void commitUpdates(ActionEvent e){
       EventIntegrator ei = getEventIntegrator();
       EventCategory ec = new EventCategory();
       
       ec.setCategoryID(selectedEventCategory.getCategoryID());
       ec.setEventType(formEventType);
       ec.setEventTypeTitle(formEventCategoryTitle);
       ec.setEventTypeDesc(formEventCategoryDescr);
       
        try {
            ei.updateEventCategory(ec);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Event category updated!", ""));
            formEventCategoryTitle = "";
            formEventCategoryDescr = "";
        } catch (IntegrationException ex) {
           getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to update Event Category in database, sorry.", 
                        "This must be corrected by the System Administrator"));
        }
        
    }
    
    public void addNewEventCategory(ActionEvent e){
        EventIntegrator ei = getEventIntegrator();
        EventCategory ec = new EventCategory();
        
        ec.setEventType(newFormSelectedEventType);
        ec.setEventTypeTitle(newFormEventCategoryTitle);
        ec.setEventTypeDesc(newFormEventCategoryDescr);
        
        try {
            ei.insertEventCategory(ec);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Event category added!", ""));
            newFormEventCategoryTitle = "";
            newFormEventCategoryDescr = "";
        } catch (IntegrationException ex) {
            System.out.println(ex);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to add new Event Category to database, sorry.", 
                        "This must be corrected by the System Administrator"));
        }
        
    }
    
    public void deleteSelectedEventCategory(ActionEvent e){
        EventIntegrator ei = getEventIntegrator();
        if(selectedEventCategory != null){
            try {
                ei.deleteEventCategory(selectedEventCategory);
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Event category deleted forever!", ""));
            } catch (IntegrationException ex) {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to delete the category--probably because it is used "
                                    + "somewhere in the database. Sorry.", 
                            "This category will always be with us."));
            }
            
        } else {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select a category from the table to delete", ""));
        }
    }

    /**
     * @return the selectedEventCategory
     */
    public EventCategory getSelectedEventCategory() {
        return selectedEventCategory;
    }

    /**
     * @return the eventCategoryList
     */
    public LinkedList<EventCategory> getEventCategoryList() {
        try {
            EventIntegrator ei = getEventIntegrator();
            eventCategoryList = ei.getEventCategoryList();
            //return eventCategoryList;
        } catch (IntegrationException ex) {
             getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to load event category list", 
                        "This must be corrected by the System Administrator"));
        }
        if(eventCategoryList != null){
            return eventCategoryList;
            
        } else {
            eventCategoryList = new LinkedList();
            return eventCategoryList;
        }
        
    }

    /**
     * @return the eventTypeList
     */
    public EventType[] getEventTypeList() {
        return eventTypeList;
    }

    /**
     * @return the formEventType
     */
    public EventType getFormEventType() {
        return formEventType;
    }

    /**
     * @return the formEventCategoryTitle
     */
    public String getFormEventCategoryTitle() {
        return formEventCategoryTitle;
    }

    /**
     * @return the formEventCategoryDescr
     */
    public String getFormEventCategoryDescr() {
        return formEventCategoryDescr;
    }

    /**
     * @param selectedEventCategory the selectedEventCategory to set
     */
    public void setSelectedEventCategory(EventCategory selectedEventCategory) {
        this.selectedEventCategory = selectedEventCategory;
    }

    /**
     * @param eventCategoryList the eventCategoryList to set
     */
    public void setEventCategoryList(LinkedList<EventCategory> eventCategoryList) {
        
        
        this.eventCategoryList = eventCategoryList;
    }

    /**
     * @param eventTypeList the eventTypeList to set
     */
    public void setEventTypeList(EventType[] eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    /**
     * @param formEventType the formEventType to set
     */
    public void setFormEventType(EventType formEventType) {
        this.formEventType = formEventType;
    }

    /**
     * @param formEventCategoryTitle the formEventCategoryTitle to set
     */
    public void setFormEventCategoryTitle(String formEventCategoryTitle) {
        this.formEventCategoryTitle = formEventCategoryTitle;
    }

    /**
     * @param formEventCategoryDescr the formEventCategoryDescr to set
     */
    public void setFormEventCategoryDescr(String formEventCategoryDescr) {
        this.formEventCategoryDescr = formEventCategoryDescr;
    }

    /**
     * @return the newFormSelectedEventType
     */
    public EventType getNewFormSelectedEventType() {
        return newFormSelectedEventType;
    }

    /**
     * @return the newFormEventCategoryTitle
     */
    public String getNewFormEventCategoryTitle() {
        return newFormEventCategoryTitle;
    }

    /**
     * @return the newFormEventCategoryDescr
     */
    public String getNewFormEventCategoryDescr() {
        return newFormEventCategoryDescr;
    }

    /**
     * @param newFormSelectedEventType the newFormSelectedEventType to set
     */
    public void setNewFormSelectedEventType(EventType newFormSelectedEventType) {
        this.newFormSelectedEventType = newFormSelectedEventType;
    }

    /**
     * @param newFormEventCategoryTitle the newFormEventCategoryTitle to set
     */
    public void setNewFormEventCategoryTitle(String newFormEventCategoryTitle) {
        this.newFormEventCategoryTitle = newFormEventCategoryTitle;
    }

    /**
     * @param newFormEventCategoryDescr the newFormEventCategoryDescr to set
     */
    public void setNewFormEventCategoryDescr(String newFormEventCategoryDescr) {
        this.newFormEventCategoryDescr = newFormEventCategoryDescr;
    }
    
}
