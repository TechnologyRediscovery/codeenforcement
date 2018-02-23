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
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.integration.EventIntegrator;
import java.io.Serializable;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import jdk.nashorn.internal.runtime.Context;
import com.tcvcog.tcvce.util.Constants;
import java.time.LocalDateTime;
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
    
    public void logCodeViolationUpdate(CECase ceCase, CodeViolation cv, Event event) throws IntegrationException, EventIntegrationException{
        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        
        
        // the event is coming to use from the violationEditBB with the description and disclosures flags
        // correct. This method needs to set the description from the resource bundle, and 
        // set the date of record to the current date
        String updateViolationDescr = getResourceBundle(Constants.MESSAGE_BUNDLE).getString("violationChangeEventDescription");
        // fetch the event category id from the event category bundle under the key updateViolationEventCategoryID
        // now we're ready to log the event
//        
//        event.setCategory(Integer.parseInt(getResourceBundle(
//                Constants.EVENT_CATEGORY_BUNDLE).getString("updateViolationEventCategoryID"));
//        
        // hard coded for now
        event.setCategory(ei.getEventCategory(105));
        event.setCaseID(ceCase.getCaseID());
        event.setDateOfRecord(LocalDateTime.now());
        event.setEventDescription(updateViolationDescr);
        //even descr set by violation coordinator
        event.setEventOwnerUser(sm.getVisit().getCurrentUser());
        // disclose to muni from violation coord
        // disclose to public from violation coord
        event.setActiveEvent(true);
        
        
        ei.insertEvent(event);

        
    }
    
}
