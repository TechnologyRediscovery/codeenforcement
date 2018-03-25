/*
 * Copyright (C) 2017 Turtle Creek Valley
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
package com.tcvcog.tcvce.coordinators;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.application.SessionManager;
import com.tcvcog.tcvce.domain.EventIntegrationException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.integration.EventIntegrator;
import java.io.Serializable;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import jdk.nashorn.internal.runtime.Context;
import com.tcvcog.tcvce.util.Constants;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 *
 * @author sylvia
 */
public class EventCoordinator extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of EventCoordinator
     */
    public EventCoordinator() {
        
    }
    
    public Event getInitializedEvent(EventCategory ec){
        // the moment of event instantiaion
        Event event = new Event();
        event.setCategory(ec);
        System.out.println("EventCoordinator.getInitalizedEvent | eventCat: " 
                + event.getCategory().getEventCategoryTitle());
        return event;
    }
    
    public void logCodeViolationUpdate(CECase ceCase, CodeViolation cv, Event event) throws IntegrationException, EventIntegrationException{
        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        
        
        // the event is coming to use from the violationEditBB with the description and disclosures flags
        // correct. This method needs to set the description from the resource bundle, and 
        // set the date of record to the current date
        String updateViolationDescr = getResourceBundle(Constants.MESSAGE_BUNDLE).getString("violationChangeEventDescription");
        // fetch the event category id from the event category bundle under the key updateViolationEventCategoryID
        // now we're ready to log the event
        EventCategory ec = new EventCategory();
        ec.setCategoryID(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("updateViolationEventCategoryID")));
        event.setCategory(ec); 
       
        // hard coded for now
//        event.setCategory(ei.getEventCategory(117));
        event.setCaseID(ceCase.getCaseID());
        event.setDateOfRecord(LocalDateTime.now());
        event.setEventDescription(updateViolationDescr);
        //even descr set by violation coordinator
        event.setEventOwnerUser(sm.getVisit().getActiveUser());
        // disclose to muni from violation coord
        // disclose to public from violation coord
        event.setActiveEvent(true);
        
        
        ei.insertEvent(event);

        
    }
    
    public LinkedList geteventList(CECase currentCase) throws IntegrationException{
        EventIntegrator ei = getEventIntegrator();
        LinkedList<Event> ll = ei.getEventsByCaseID(currentCase.getCaseID());
        return ll;
    }
    
    public void LogCasePhaseChange(CECase currentCase, CasePhase pastPhase) throws EventIntegrationException{
        
        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        Event event = new Event();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Case phase automatically advanced from  \'");
        sb.append(pastPhase.toString());
        sb.append("\' to \'");
        sb.append(currentCase.getCasePhase().toString());
        sb.append("\' triggered by an action-type event.");
        event.setEventDescription(sb.toString());
        
        event.setCaseID(currentCase.getCaseID());
        event.setDateOfRecord(LocalDateTime.now());
        event.setEventOwnerUser(sm.getVisit().getActiveUser());
        event.setActiveEvent(true);
        
        ei.insertEvent(event);
        
    } // close method
}
