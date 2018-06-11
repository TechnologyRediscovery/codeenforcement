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
import com.tcvcog.tcvce.entities.ImprovementSuggestion;
import com.tcvcog.tcvce.entities.ListChangeRequest;
import com.tcvcog.tcvce.integration.SystemIntegrator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class SystemServicesBB extends BackingBeanUtils implements Serializable{

    private String listItemChangeRequestRText;
    
    private String systemImprovementTicketRText;
    private int selectedImprovementType;
    private HashMap<String, Integer> improvementTypeMap;
    
    
    /**
     * Creates a new instance of SystemServicesBB
     */
    public SystemServicesBB() {
    }

    public String submitImprovementSuggestion(){
        SessionManager sm = getSessionManager();
        SystemIntegrator si = getSystemIntegrator();

        ImprovementSuggestion is = new ImprovementSuggestion();
        
        is.setImprovementTypeID(selectedImprovementType);
        is.setSubmitter(sm.getVisit().getActiveUser());
        is.setSuggestionText(systemImprovementTicketRText);
        
        try {
            si.insertImprovementSuggestion(is);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                   "Suggesetion Submitted! Thanks!",""));
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    ex.getLocalizedMessage(), 
                    "This issue will need to be addressed by the system admin, sorry"));
        }
        
        return "dashboard";
    }
    
    public String submitListItemChange(){
        SystemIntegrator si = getSystemIntegrator();
        ListChangeRequest lcr = new ListChangeRequest();
        lcr.setChangeRequestText(listItemChangeRequestRText);
        try {
            si.insertListChangeRequest(lcr);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                         ex.getLocalizedMessage(), 
                         "This issue will need to be addressed by the system admin, sorry"));
        }
        
        return "dashboard";
    }
    
    /**
     * @return the listItemChangeRequestRText
     */
    public String getListItemChangeRequestRText() {
        return listItemChangeRequestRText;
    }

    /**
     * @return the systemImprovementTicketRText
     */
    public String getSystemImprovementTicketRText() {
        return systemImprovementTicketRText;
    }

    /**
     * @param listItemChangeRequestRText the listItemChangeRequestRText to set
     */
    public void setListItemChangeRequestRText(String listItemChangeRequestRText) {
        this.listItemChangeRequestRText = listItemChangeRequestRText;
    }

    /**
     * @param systemImprovementTicketRText the systemImprovementTicketRText to set
     */
    public void setSystemImprovementTicketRText(String systemImprovementTicketRText) {
        this.systemImprovementTicketRText = systemImprovementTicketRText;
    }

    /**
     * @return the selectedImprovementType
     */
    public int getSelectedImprovementType() {
        return selectedImprovementType;
    }

    /**
     * @param selectedImprovementType the selectedImprovementType to set
     */
    public void setSelectedImprovementType(int selectedImprovementType) {
        this.selectedImprovementType = selectedImprovementType;
    }

    /**
     * @return the improvementTypeMap
     */
    public HashMap<String, Integer> getImprovementTypeMap() {
        SystemIntegrator si = getSystemIntegrator();
        try {
            improvementTypeMap = si.getSuggestionTypeMap();
        } catch (IntegrationException ex) {
            System.out.println(ex);
        }
        return improvementTypeMap;
    }

    /**
     * @param improvementTypeMap the improvementTypeMap to set
     */
    public void setImprovementTypeMap(HashMap<String, Integer> improvementTypeMap) {
        this.improvementTypeMap = improvementTypeMap;
    }
    
    
    
}
