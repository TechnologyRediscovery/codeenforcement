/*
 * Copyright (C) 2017 sylvia
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

import com.tcvcog.tcvce.coordinators.SessionCoordinator;
import com.tcvcog.tcvce.domain.IntegrationException;
import java.util.LinkedList;
import javax.faces.component.html.HtmlDataTable;
//import org.primefaces.component.datatable.DataTable;
import com.tcvcog.tcvce.entities.*;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sylvia
 */
public class RequestManagementBean extends BackingBeanUtils implements Serializable{
    
    private LinkedList<CEActionRequest> requestList;
    
    private HtmlDataTable requestTable;
    
    private String currentRequestAddressOfConcern;
    
    private CEActionRequest currentRequest;
    
    private RequestStatus newStatus;
    
    // bean utilities
    private CEActionRequestIntegrator integrator = new CEActionRequestIntegrator();
    
    /**
     * Creates a new instance of RequestManagementBBean
     */
    public RequestManagementBean() {
    }

    // action listener method for managing requests by COG staff
    public String manage(){
        System.out.println("RequestManagementBean.manage - action event");
        currentRequest = (CEActionRequest) requestTable.getRowData();
        // these next lines were created for testing purposes only and can be deleted
        currentRequestAddressOfConcern = String.valueOf(currentRequest.getAddressOfConcern());
        System.out.println("RequestManagementBean.manage - got address:" + currentRequest.getAddressOfConcern());
        
        return "manageRequest";
        
        
    }
    
    /**
     * @return the requestList
     */
    public LinkedList getRequestList() {
        
//        try {
//            return integrator.getCEActionRequestList();
//        } catch (IntegrationException ex) {
//            Logger.getLogger(RequestManagementBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        return new LinkedList();
    }

    /**
     * @param requestList the requestList to set
     */
    public void setRequestList(LinkedList requestList) {
        this.requestList = requestList;
    }

    /**
     * @return the requestTable
     */
    public HtmlDataTable getRequestTable() {
        return requestTable;
    }

    /**
     * @param requestTable the requestTable to set
     */
    public void setRequestTable(HtmlDataTable requestTable) {
        this.requestTable = requestTable;
    }

    /**
     * @return the currentRequestAddressOfConcern
     */
    public String getCurrentRequestAddressOfConcern() {
        return currentRequestAddressOfConcern;
    }

    /**
     * @param currentRequestAddressOfConcern the currentRequestAddressOfConcern to set
     */
    public void setCurrentRequestAddressOfConcern(String currentRequestAddressOfConcern) {
        this.currentRequestAddressOfConcern = currentRequestAddressOfConcern;
    }

    /**
     * @return the currentRequest
     */
    public CEActionRequest getCurrentRequest() {
        SessionCoordinator sm = getSessionManager();
        currentRequest = sm.getVisit().getActionRequest();
        return currentRequest;
    }

    /**
     * @param currentRequest the currentRequest to set
     */
    public void setCurrentRequest(CEActionRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    /**
     * @return the newStatus
     */
    public RequestStatus getNewStatus() {
        return newStatus;
    }

    /**
     * @param newStatus the newStatus to set
     */
    public void setNewStatus(RequestStatus newStatus) {
        this.newStatus = newStatus;
    }
    
}
