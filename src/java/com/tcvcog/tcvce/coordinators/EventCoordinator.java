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
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.integration.EventIntegrator;
import java.io.Serializable;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import jdk.nashorn.internal.runtime.Context;
import com.tcvcog.tcvce.util.Constants;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class EventCoordinator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of EventCoordinator
     */
    public EventCoordinator() {

    }

    public EventType[] getUserAdmnisteredEventTypeList() {
        EventType[] eventTypeList = EventType.values();

        return eventTypeList;

    }

    public Event getInitializedEvent(EventCategory ec) {
        // the moment of event instantiaion
        Event event = new Event();
        event.setCategory(ec);
        System.out.println("EventCoordinator.getInitalizedEvent | eventCat: "
                + event.getCategory().getEventCategoryTitle());
        return event;
    }

    public EventCategory getInitializedEventCateogry() {
        EventCategory ec = new EventCategory();
        ec.setUserdeployable(true);
        ec.setHidable(true);
        return ec;
    }

    public void logCodeViolationUpdate(CECase ceCase, CodeViolation cv, Event event) throws IntegrationException, EventException {
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

    /**
     * Main controller method for event-related life cycle events. Requires
     * event to be loaded up with a caseID and an eventType. No eventID is
     * required since it has not yet been logged into the db.
     *
     * @param c code enforcement case
     * @param event event to process
     * @throws com.tcvcog.tcvce.domain.CaseLifecyleException
     */
    public void processEvent(CECase c, Event event) throws CaseLifecyleException, IntegrationException {

        CaseCoordinator cc = getCaseCoordinator();

        Integer.parseInt(getResourceBundle(Constants.EVENT_CATEGORY_BUNDLE).getString("updateViolationEventCategoryID"));

        CasePhase phase = c.getCasePhase();
        int evCatID = event.getCategory().getCategoryID();
        EventType catEvType = event.getCategory().getEventType();

        // check to see if the event triggers a case phase chage. If so,
        // carry out that function with the case coordinator, creating phase change
        // event along the way. 
        try {
            // event trigger: deployed notice of violation
            if (phase == CasePhase.PrelimInvestigationPending
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToNoticeDelivery"))) {

                cc.advanceToNextCasePhase(c, CasePhase.NoticeDelivery);
                logCommittedCasePhaseChange(c, phase);

                // event trigger: notice of violation sent
            } else if (phase == CasePhase.NoticeDelivery
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToInitialComplianceTimeframe"))) {

                cc.advanceToNextCasePhase(c, CasePhase.InitialComplianceTimeframe);
                logCommittedCasePhaseChange(c, phase);

                // event trigger: property inspection
            } else if (phase == CasePhase.InitialComplianceTimeframe
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToSecondaryComplianceTimeframe"))) {

                cc.advanceToNextCasePhase(c, CasePhase.SecondaryComplianceTimeframe);
                logCommittedCasePhaseChange(c, phase);

                // Event Trigger: Citation Filed
            } else if (phase == CasePhase.SecondaryComplianceTimeframe
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToAwaitingHearingDate"))) {

                cc.advanceToNextCasePhase(c, CasePhase.AwaitingHearingDate);
                logCommittedCasePhaseChange(c, phase);

                // event trigger: hearing date scheduled
            } else if (phase == CasePhase.AwaitingHearingDate
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToHearingPreparation"))) {

                cc.advanceToNextCasePhase(c, CasePhase.HearingPreparation);
                logCommittedCasePhaseChange(c, phase);

                // event trigger: hearing held
            } else if (phase == CasePhase.HearingPreparation
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToInitialPostHearingComplianceTimeframe"))) {

                cc.advanceToNextCasePhase(c, CasePhase.InitialPostHearingComplianceTimeframe);
                logCommittedCasePhaseChange(c, phase);

                // event trigger: property inspection
            } else if (phase == CasePhase.InitialPostHearingComplianceTimeframe
                    && evCatID == Integer.parseInt(getResourceBundle(
                            Constants.EVENT_CATEGORY_BUNDLE).getString("advToSecondaryPostHearingComplianceTimeframe"))) {

                cc.advanceToNextCasePhase(c, CasePhase.SecondaryPostHearingComplianceTimeframe);
                logCommittedCasePhaseChange(c, phase);

            } else if (catEvType == EventType.Closing) {
                cc.advanceToNextCasePhase(c, CasePhase.Closed);
                logCommittedCasePhaseChange(c, phase);

            }

        } catch (IntegrationException ex) {
            Logger.getLogger(EventCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            throw new CaseLifecyleException("Cannot update case phase", ex);
        }

        // finally, insert event into system
        insertEvent(event);
    }

    private void insertEvent(Event e) throws IntegrationException {
        EventIntegrator ei = getEventIntegrator();
        ei.insertEvent(e);

    }

    public LinkedList geteventList(CECase currentCase) throws IntegrationException {
        EventIntegrator ei = getEventIntegrator();
        LinkedList<Event> ll = ei.getEventsByCaseID(currentCase.getCaseID());
        return ll;
    }

    public void logCommittedCasePhaseChange(CECase currentCase, CasePhase pastPhase) throws IntegrationException {

        EventIntegrator ei = getEventIntegrator();
        SessionManager sm = getSessionManager();
        Event event = getInitializedEvent(ei.getEventCategory(Integer.parseInt(getResourceBundle(
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
}
