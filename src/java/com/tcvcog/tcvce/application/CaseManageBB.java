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

import com.tcvcog.tcvce.coordinators.CaseCoordinator;
import com.tcvcog.tcvce.coordinators.EventCoordinator;
import com.tcvcog.tcvce.coordinators.ViolationCoordinator;
import com.tcvcog.tcvce.domain.CaseLifecyleException;
import com.tcvcog.tcvce.domain.EventException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.entities.NoticeOfViolation;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

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
    
    private LinkedList<NoticeOfViolation> noticeList;
    private NoticeOfViolation selectedNotice;
    
    private LinkedList<CodeViolation> citationList;
    private CodeViolation code;
    
    
    
    
    
    /**
     * Creates a new instance of CaseManageBB
     */
    public CaseManageBB() {
    }
    
    public String editViolation(){
        
        if(selectedViolation != null){
            SessionManager sm = getSessionManager();
            sm.getVisit().setActiveCodeViolation(selectedViolation);
            return "violationEdit";
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a violation and try again", ""));
            return "";
            
        }
    }
    
    public String createCitation(){
        return "";
    }
    
    public String recordCompliance(){
        
        if(selectedViolation != null){
            ViolationCoordinator vc = getViolationCoordinator();
            
            return "eventCompliance";
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a violation and try again", ""));
            return "";
            
        }
    }
    

    public void deleteViolation(ActionEvent e){
        if(selectedViolation != null){
            ViolationCoordinator vc = getViolationCoordinator();
            try {
                vc.deleteViolation(selectedViolation);
            } catch (IntegrationException ex) {
                getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Unable to delete selected violation", 
                                "Check if the violation has been referenced in a citation."
                                        + "If so, and you still wish to delete, you must remove"
                                        + "the citation first, then delete the violation."));
            }
            
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a violation and try again", ""));
            
        }
    }
    
    public String addViolation(){
        // no logic needed in the backing bean
        
        return "violationSelectElement";
    }
    
    public String editSelectedEvent(){

        if(selectedViolation != null){
            SessionManager sm = getSessionManager();
            sm.getVisit().setActiveCodeViolation(selectedViolation);
            return "";
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a violation and try again", ""));
            return "";

            }
    }
    
    public String createNewNoticeOfViolation(){
        CaseCoordinator cc = getCaseCoordinator();
        NoticeOfViolation nov = cc.generateNewNoticeOfViolation(currentCase);
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveNotice(nov);
             
        return "noticeOfViolationEditor";
    }
    
    
    
    public String editNoticeOfViolation(){
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveNotice(selectedNotice);
        
        return "noticeOfViolationEditor";
    }
    
    
    public void deleteNoticeOfViolation(ActionEvent event){
        CaseCoordinator caseCoord = getCaseCoordinator();
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveNotice(selectedNotice);
        try {
            caseCoord.deleteNoticeOfViolation(selectedNotice);
        } catch (CaseLifecyleException ex) {
            
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to delete this notice of violation, "
                                    + "probably because it has been sent already", ""));
        }
    }
    
    public void deployNoticeOfViolation(ActionEvent event){
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveNotice(selectedNotice);
        CaseCoordinator cc = getCaseCoordinator();
        try {
            cc.deployNoticeOfViolation(currentCase, selectedNotice);
        } catch (CaseLifecyleException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to deploy notice of violation", "Case phase remains unchanged"));
            
        } catch (IntegrationException ex) {
            
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to communicate effectively with the database", ""));
        } catch (EventException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to generate case event to log phase change", 
                            "Note that because this message is being displayed, the phase change"
                                    + "has probably succeeded"));
        } // close try/catch \
        
    }
    
    public void markNoticeOfViolationAsSent(Event event){
        CaseCoordinator caseCoord = getCaseCoordinator();
        try {
            caseCoord.markNoticeOfViolationAsSent(currentCase, selectedNotice);
        } catch (CaseLifecyleException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            ex.toString(), "This must be corrected by a "
                                    + "system administrator, sorry"));
            
        } catch (EventException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to generate case event to log phase change", 
                            "Note that because this message is being displayed, the phase change"
                                    + "has probably succeeded"));
            
            
        } // close try/cathc section
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
        EventCoordinator ec = getEventCoordinator();
        try {
            eventList = ec.geteventList(currentCase);
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Could not load event list for this case", ""));
        }
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
        ViolationCoordinator vc = getViolationCoordinator();
        try {
            violationList = vc.getCodeViolations(currentCase);
            if(violationList != null){
            }
        } catch (IntegrationException ex) {
            System.out.println(ex);
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to load code violation list", 
                                "This is a system-level error that msut be corrected by an administrator, Sorry!"));
            
        }
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
     * @return the selectedNotice
     */
    public NoticeOfViolation getSelectedNotice() {
        return selectedNotice;
    }

    /**
     * @param selectedNotice the selectedNotice to set
     */
    public void setSelectedNotice(NoticeOfViolation selectedNotice) {
        this.selectedNotice = selectedNotice;
    }

    /**
     * @return the noticeList
     */
    public LinkedList<NoticeOfViolation> getNoticeList() {
        return noticeList;
    }

    /**
     * @param noticeList the noticeList to set
     */
    public void setNoticeList(LinkedList<NoticeOfViolation> noticeList) {
        this.noticeList = noticeList;
    }
}