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

import com.tcvcog.tcvce.coordinators.SessionCoordinator;
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
    
    private String formCategory;
    private String formSubCategory;
    private String formDescription;
    private String formEnforcementGuidelines;
    private String formInspectionGuidelines;
    private boolean formPriority;
    
    
    /**
     * Creates a new instance of CodeElementTypeBB
     */
    public CodeElementGuideBB() {
    }
    
    public String addCodeElementGuideEntry(){
        
        CodeElementGuideEntry cege = new CodeElementGuideEntry();
        CodeIntegrator ci = getCodeIntegrator();
        
        cege.setCategory(formCategory);
        cege.setSubCategory(formCategory);
        cege.setDescription(formDescription);
        cege.setEnforcementGuidelines(formEnforcementGuidelines);
        cege.setInspectionGuidelines(formInspectionGuidelines);
        cege.setPriority(formPriority);
        
        try {
            ci.insertCodeElementGuideEntry(cege);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Succesfully added code guide entry", ""));
        } catch (IntegrationException ex) {

            System.out.println(ex.toString());

            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        ex.getLocalizedMessage(), 
                        "This must be corrected by the System Administrator, sorry"));
        }
        
        return "codeGuideView";
    }
    
    public String updateCodeElementGuideEntry(){
        System.out.println("CodeElementTypeBB.updateCodeElement");
        CodeIntegrator ci = getCodeIntegrator();
        
        currentGuideEntry.setCategory(formCategory);
        currentGuideEntry.setSubCategory(formCategory);
        currentGuideEntry.setDescription(formDescription);
        currentGuideEntry.setEnforcementGuidelines(formEnforcementGuidelines);
        currentGuideEntry.setInspectionGuidelines(formInspectionGuidelines);
        currentGuideEntry.setPriority(formPriority);
        
        try {

            System.out.println("CodeElementGuideBB.updateCodeElementGuideEntry: " 
                    + currentGuideEntry.getGuideEntryID());

            ci.updateCodeElementGuideEntry(currentGuideEntry);

            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Updated code element guide entry!", ""));

        } catch (IntegrationException ex) {

            System.out.println(ex.toString());

            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        ex.getLocalizedMessage(), 
                        "This must be corrected by the System Administrator"));
        }
        
        return "codeGuideView";
            
    }
    
    public String deleteCodeElementGuideEntry(){
        CodeElementGuideEntry cege = currentGuideEntry;
        try {
            CodeIntegrator ci = getCodeIntegrator();
            ci.deleteCodeElementGuideEntry(cege);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());

            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        ex.getLocalizedMessage(), 
                        "This must be corrected by the System Administrator"));
        }
        
        return "codeGuideView";
    }
    
    /**
     * @return the currentGuideEntry
     */
    public CodeElementGuideEntry getCurrentGuideEntry() {
        SessionCoordinator sm = getSessionManager();
        currentGuideEntry = sm.getVisit().getCurrentCodeElementGuide();
                
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
            System.out.println("CodeElementGuideBB.getEntryList | " + ex.getLocalizedMessage());
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
     * @return the formCategory
     */
    public String getFormCategory() {
        formCategory = currentGuideEntry.getCategory();
        return formCategory;
    }

    /**
     * @return the formSubCategory
     */
    public String getFormSubCategory() {
        formSubCategory = currentGuideEntry.getSubCategory();
        return formSubCategory;
    }

    /**
     * @return the formDescription
     */
    public String getFormDescription() {
        formDescription = currentGuideEntry.getDescription();
        return formDescription;
    }

    /**
     * @return the formEnforcementGuidelines
     */
    public String getFormEnforcementGuidelines() {
        formEnforcementGuidelines = currentGuideEntry.getEnforcementGuidelines();
        return formEnforcementGuidelines;
    }

    /**
     * @return the formInspectionGuidelines
     */
    public String getFormInspectionGuidelines() {
        formInspectionGuidelines = currentGuideEntry.getInspectionGuidelines();
        return formInspectionGuidelines;
    }

    /**
     * @return the formPriority
     */
    public boolean isFormPriority() {
        formPriority = currentGuideEntry.isPriority();
        return formPriority;
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

    /**
     * @param formCategory the formCategory to set
     */
    public void setFormCategory(String formCategory) {
        this.formCategory = formCategory;
    }

    /**
     * @param formSubCategory the formSubCategory to set
     */
    public void setFormSubCategory(String formSubCategory) {
        this.formSubCategory = formSubCategory;
    }

    /**
     * @param formDescription the formDescription to set
     */
    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    /**
     * @param formEnforcementGuidelines the formEnforcementGuidelines to set
     */
    public void setFormEnforcementGuidelines(String formEnforcementGuidelines) {
        this.formEnforcementGuidelines = formEnforcementGuidelines;
    }

    /**
     * @param formInspectionGuidelines the formInspectionGuidelines to set
     */
    public void setFormInspectionGuidelines(String formInspectionGuidelines) {
        this.formInspectionGuidelines = formInspectionGuidelines;
    }

    /**
     * @param formPriority the formPriority to set
     */
    public void setFormPriority(boolean formPriority) {
        this.formPriority = formPriority;
    }
    

    
}
