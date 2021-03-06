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
import com.tcvcog.tcvce.entities.CEActionRequest;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import java.io.Serializable;
import java.util.LinkedList;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class WorkflowBB extends BackingBeanUtils implements Serializable{

    
    private LinkedList<CEActionRequest> requestList;
    private CEActionRequest selectedRequest;
    private LinkedList<CECase> caseList;
    private CECase selectedCase;
    private LinkedList<Event> recentEventList;
    private LinkedList<Person> muniPeopleList;
    
    
    
    /**
     * Creates a new instance of WorkflowBB
     */
    public WorkflowBB() {
    }
    
    
    public String viewCase(){
        
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveCase(selectedCase);
        sm.getVisit().setActiveProp(selectedCase.getProperty());
        
        return "caseManage";
    }
    
    public String viewSelectedActionRequest(){
        SessionManager sm = getSessionManager();
        sm.getVisit().setActionRequest(selectedRequest);
        
        return "actionRequestManage";
        
        
    }

    /**
     * @return the requestList
     */
    public LinkedList<CEActionRequest> getRequestList() {
        CEActionRequestIntegrator ari = getcEActionRequestIntegrator();
        SessionManager sm = getSessionManager();
        try {
            requestList = ari.getCEActionRequestList(sm.getVisit().getActiveUser().getMuniCode());
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to load action requests due to an error in the Integration Module", ""));
        }
        return requestList;
    }

    /**
     * @return the CaseList
     */
    public LinkedList<CECase> getCaseList() {
        CaseIntegrator ci = getCaseIntegrator();
        SessionManager sm = getSessionManager();
        int muniCodeForFetching = sm.getVisit().getActiveUser().getMuniCode();
        
        try {
            caseList = ci.getCECasesByMuni(muniCodeForFetching);
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to load cases for this municipality due to an error in the Integration Module", ""));
        }
        return caseList;
    }

    /**
     * @return the recentEventList
     */
    public LinkedList<Event> getRecentEventList() {
        return recentEventList;
    }

    /**
     * @return the muniPeopleList
     */
    public LinkedList<Person> getMuniPeopleList() {
        return muniPeopleList;
    }

    /**
     * @param requestList the requestList to set
     */
    public void setRequestList(LinkedList<CEActionRequest> requestList) {
        this.requestList = requestList;
    }

    /**
     * @param CaseList the CaseList to set
     */
    public void setCaseList(LinkedList<CECase> CaseList) {
        this.caseList = CaseList;
    }

    /**
     * @param recentEventList the recentEventList to set
     */
    public void setRecentEventList(LinkedList<Event> recentEventList) {
        this.recentEventList = recentEventList;
    }

    /**
     * @param muniPeopleList the muniPeopleList to set
     */
    public void setMuniPeopleList(LinkedList<Person> muniPeopleList) {
        this.muniPeopleList = muniPeopleList;
    }

    /**
     * @return the selectedCase
     */
    public CECase getSelectedCase() {
        return selectedCase;
    }

    /**
     * @param selectedCase the selectedCase to set
     */
    public void setSelectedCase(CECase selectedCase) {
        this.selectedCase = selectedCase;
    }

    /**
     * @return the selectedRequest
     */
    public CEActionRequest getSelectedRequest() {
        return selectedRequest;
    }

    /**
     * @param selectedRequest the selectedRequest to set
     */
    public void setSelectedRequest(CEActionRequest selectedRequest) {
        this.selectedRequest = selectedRequest;
    }
    
}
