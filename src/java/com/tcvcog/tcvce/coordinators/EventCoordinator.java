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
import com.tcvcog.tcvce.domain.CaseLifecyleException;
import com.tcvcog.tcvce.domain.EventException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.domain.ViolationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.integration.EventIntegrator;
import java.io.Serializable;
import com.tcvcog.tcvce.util.Constants;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.faces.application.FacesMessage;

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
    
    public EventType[] getUserAdmnisteredEventTypeList(){
        EventType[] eventTypeList = EventType.values();
            
        
        return eventTypeList;
        
    }
    
    public Event getInitializedEvent(CECase c, EventCategory ec) throws CaseLifecyleException{
        
        // check to make sure the case isn't closed before allowing event into the switched blocks
        if(c.getCasePhase() == CasePhase.Closed && 
                (
                    ec.getEventType() == EventType.Action
                    || 
                    ec.getEventType() == EventType.Origination
                    ||
                    ec.getEventType() == EventType.Compliance
                )
        ){
            
            throw new CaseLifecyleException("This event cannot be attached to a closed cases");
            
        }
        
        // the moment of event instantiaion
        Event event = new Event();
        event.setCategory(ec);
        System.out.println("EventCoordinator.getInitalizedEvent | eventCat: " 
                + event.getCategory().getEventCategoryTitle());
        return event;
    }
    
    public EventCategory getInitializedEventCateogry(){
        EventCategory ec =  new EventCategory();
        ec.setUserdeployable(true);
        ec.setHidable(true);
        // TODO: finishing autoconfiguring these 
        return ec;
    }
    
    public void generateAndInsertCodeViolationUpdateEvent(CECase ceCase, CodeViolation cv, Event event) throws IntegrationException, EventException{
        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        
        
        // the event is coming to us from the violationEditBB with the description and disclosures flags
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
    
    public Event generateViolationComplianceEvent(ArrayList<CodeViolation> violationList) throws IntegrationException{
        Event e = new Event();
        EventIntegrator ei = getEventIntegrator();
        e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("complianceEvent"))));
        e.setEventDescription("Compliance with municipal code achieved");
        
        ListIterator<CodeViolation> li = violationList.listIterator();
        CodeViolation cv;
        StringBuilder sb = new StringBuilder();
        sb.append("Compliance with the following code violations was observed:");
        sb.append("<br/><br/>");
        
        while(li.hasNext()){
            cv = li.next();
            sb.append(cv.getViolatedEnfElement().getOrdchapterNo());
            sb.append(".");
            sb.append(cv.getViolatedEnfElement().getOrdSecNum());
            sb.append(".");
            sb.append(cv.getViolatedEnfElement().getOrdSubSecNum());
            sb.append(":");
            sb.append(cv.getViolatedEnfElement().getOrdSubSecTitle());
            sb.append("<br/><br/>");
        }
        e.setNotes(sb.toString());
        return e;
    }
    
    /**
     * At its current impelementation, this amounts to a factory for ArrayLists
     * that are populated by the user when creating events
     * @return 
     */
    public ArrayList<Person> getEmptyEventPersonList(){
        return new ArrayList<>();
    }
    
    public String updateEvent(Event event) throws IntegrationException{
        EventIntegrator ei = getEventIntegrator();
        ei.updateEvent(event);
        
        return "caseManage";
    }
    
    
    public void insertEvent(Event e) throws IntegrationException{
        EventIntegrator ei = getEventIntegrator();
        ei.insertEvent(e);
        
    }
    
    /**
     * a pass through method called by the eventAddBB which sends the event and case
     * over to the case coordinator for the meat of the processing cycle. This exists
     * such that the eventAddBB is interacting only with the methods on the EventCoordinator
     * and allows the implementation of event-specific logic before interacting with the
     * CaseCoordinator.
     * @param c the current case
     * @param e the event to be processed which is passed over to the CaseCoordinator
     * @throws IntegrationException
     * @throws CaseLifecyleException
     * @throws ViolationException 
     */
    public void initiateEventProcessing(CECase c, Event e) throws IntegrationException, CaseLifecyleException, ViolationException{
        CaseCoordinator cc = getCaseCoordinator();
        
        cc.processCEEvent(c, e);
        
    }
    
    public void insertPopulatedAutomatedEvent(Event ev) throws IntegrationException{
        // the event must come to this method all ready to be sent
        // down to the integrattor, which is what insertEvent will do.
        System.out.println("EventCoordinator.insertAutomatedEvent | event Description: " + ev.getEventDescription());
        insertEvent(ev);
    }
    
    public LinkedList getEventList(CECase currentCase) throws IntegrationException{
        EventIntegrator ei = getEventIntegrator();
        LinkedList<Event> ll = ei.getEventsByCaseID(currentCase.getCaseID());
        return ll;
    }
    
    public void generateAndInsertPhaseChangeEvent(CECase currentCase, CasePhase pastPhase) throws IntegrationException, CaseLifecyleException{
        
        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        CECase c = sm.getVisit().getActiveCase();
        Event event = getInitializedEvent(c, ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("casePhaseChangeEventCatID"))));
        
        StringBuilder sb = new StringBuilder();
        sb.append("Case phase automatically advanced from  \'");
        sb.append(pastPhase.toString());
        sb.append("\' to \'");
        sb.append(currentCase.getCasePhase().toString());
        sb.append("\' triggered by an action-type event.");
        event.setEventDescription(sb.toString());
        
        event.setCaseID(currentCase.getCaseID());
        event.setDateOfRecord(LocalDateTime.now());
        // not sure if I can access the session level info for the specific user here in the
        // coordinator bean
        event.setEventOwnerUser(sm.getVisit().getActiveUser());
        event.setActiveEvent(true);
        
        insertEvent(event);
        
        getFacesContext().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "The case phase has been automatically "
                    + "advanced after a magic event occurred", ""));

    } // close method
    
    
    
    /**
     * An unused method for generating the appropriate event that will advance a case
     * to the next phase of its life cycle. Currently called by the method 
     * getEventForTriggeringCasePhaseAdvancement in CaseManageBB
     * @param c
     * @return
     * @throws IntegrationException
     * @throws CaseLifecyleException 
     */
    public Event getActionEventForCaseAdvancement(CECase c) throws IntegrationException, CaseLifecyleException{
        CasePhase cp = c.getCasePhase();
        EventIntegrator ei = getEventIntegrator();
        Event e = new Event();
        
        switch(cp){
            case PrelimInvestigationPending:

                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                   Constants.EVENT_CATEGORY_BUNDLE).getString("advToNoticeDelivery"))));
               return e;
             
            case NoticeDelivery:
                
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                    Constants.EVENT_CATEGORY_BUNDLE).getString("advToInitialComplianceTimeframe"))));
                return e;
            
            case InitialComplianceTimeframe:
            
               e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
               Constants.EVENT_CATEGORY_BUNDLE).getString("advToSecondaryComplianceTimeframe"))));
               return e;
             
            case SecondaryComplianceTimeframe:
             
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("advToAwaitingHearingDate"))));
                return e;
         
            case AwaitingHearingDate:
             
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("advToHearingPreparation"))));
                return e;

            case HearingPreparation:
             
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("advToInitialPostHearingComplianceTimeframe"))));
                return e;

            case InitialPostHearingComplianceTimeframe:
             
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("advToSecondaryPostHearingComplianceTimeframe"))));
                return e;
            
            case SecondaryPostHearingComplianceTimeframe:
             
                e.setCategory(ei.getEventCategory(Integer.parseInt(getResourceBundle(
                Constants.EVENT_CATEGORY_BUNDLE).getString("advToAwaitingHearingDate"))));
                return e;
         
            default: 
                throw new CaseLifecyleException("Cannot determine next action in case protocol");
            
         } // close switch
    } // close method
} // close class
