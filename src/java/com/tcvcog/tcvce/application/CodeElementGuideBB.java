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
import com.tcvcog.tcvce.entities.CodeElementGuideEntry;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Eric C. Darsow
 */
public class CodeElementGuideBB extends BackingBeanUtils implements Serializable {
      
    private CodeElementGuideEntry currentGuideEntry;
    private ArrayList<CodeElementGuideEntry> entryList;
    private CodeElementGuideEntry selectedGuideEntry;
    
    
    /**
     * Creates a new instance of CodeElementGuide
     */
    public CodeElementGuideBB() {
    }
    
   
    
    public String deleteCodeElementGuideEntry(){
        if(selectedGuideEntry != null){
            
            CodeElementGuideEntry cege = selectedGuideEntry;
            try {
                CodeIntegrator ci = getCodeIntegrator();
                ci.deleteCodeElementGuideEntry(cege);

                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Code guide entry nuked forever!", ""));
            } catch (IntegrationException ex) {
                System.out.println(ex.toString());

                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            ex.getLocalizedMessage(), 
                            "This must be corrected by the System Administrator"));
            }
            return "codeGuideView";
        } else {
                getFacesContext().addMessage(null,
                   new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a gode guide entry to delete it", ""));
            return "";
        }
    }
    
    public String updateSelectedGuideEntry(){
        if(selectedGuideEntry != null){
            getSessionBean().setActiveCodeElementGuideEntry(selectedGuideEntry);
            System.out.println("CodeElementGuideBB.updateSelectedGuideEntry | selectedGuideEntry: " + selectedGuideEntry.getDescription());
            return "codeGuideEntryUpdate";
        } else {
            getFacesContext().addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Please select a gode guide entry to update it", ""));
            return "";
        }
    }
    
    public String addNewCodeGuideEntry(){
        return "codeGuideEntryAdd";
    }
    
    /**
     * @return the currentGuideEntry
     */
    public CodeElementGuideEntry getCurrentGuideEntry() {
        currentGuideEntry = getSessionBean().getActiveCodeElementGuideEntry();
        return currentGuideEntry;
    }

    /**
     * @return the entryList
     */
    public ArrayList<CodeElementGuideEntry> getEntryList() {
        CodeIntegrator ci = getCodeIntegrator();
        try {
            entryList = ci.getCodeElementGuideEntries();
        } catch (IntegrationException ex) {
            System.out.println("CodeElementGuideBB.getEntryList | " + ex.getMessage());
        }
        return entryList;
    }

    /**
     * @return the selectedGuideEntry
     */
    public CodeElementGuideEntry getSelectedGuideEntry() {
        return selectedGuideEntry;
    }

  

    /**
     * @param currentGuideEntry the currentGuideEntry to set
     */
    public void setCurrentGuideEntry(CodeElementGuideEntry currentGuideEntry) {
        this.currentGuideEntry = currentGuideEntry;
    }

    /**
     * @param entryList the entryList to set
     */
    public void setEntryList(ArrayList<CodeElementGuideEntry> entryList) {
        this.entryList = entryList;
    }

    /**
     * @param selectedGuideEntry the selectedGuideEntry to set
     */
    public void setSelectedGuideEntry(CodeElementGuideEntry selectedGuideEntry) {
        this.selectedGuideEntry = selectedGuideEntry;
    }

   
    
}
