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
import com.tcvcog.tcvce.entities.CodeSet;
import com.tcvcog.tcvce.entities.EnforcableCodeElement;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/** 
 *
 * @author Eric C. Darsow
 */

public class CodeSetElementBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeSetElementBB
     */
    public CodeSetElementBB() {
    }
   
    private CodeSet currentCodeSet;
    
    private EnforcableCodeElement selectedECE;
    private ArrayList<EnforcableCodeElement> cEEList;
    
    // for editing
    private int formCodeSetElementID;
    private double formMaxPenalty;
    private double formMinPenalty;
    private double formNormPenalty;
    private String formPenaltyNotes;
    private int formNormDaysToComply;
    private String formDaysToComplyNotes; 
    
    public String updateECEData(){
        CodeIntegrator ci = getCodeIntegrator();
        try {
            ci.updateEnforcableCodeElement(selectedECE);
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return the selectedECE
     */
    public EnforcableCodeElement getSelectedECE() {
        return selectedECE;
    }

    /**
     * @param selectedECE the selectedECE to set
     */
    public void setSelectedECE(EnforcableCodeElement selectedECE) {
        this.selectedECE = selectedECE;
    }

    /**
     * @return the cEEList
     */
    public ArrayList<EnforcableCodeElement> getcEEList() {
        
        CodeIntegrator integrator = getCodeIntegrator();
        CodeSet codeSet = getSessionBean().getActiveCodeSet();
        
        if(codeSet != null){
            System.out.println("CodeSetElementBB.getEceList | active code set name: " + codeSet.getCodeSetName());
            int setID = codeSet.getCodeSetID();
            try {
                cEEList =  integrator.getEnforcableCodeElementList(setID);
//                getFacesContext().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
//                                "Loaded Enforcable Code Elements for set named: " +codeSet.getCodeSetName() , ""));
            } catch (IntegrationException ex) {
                System.out.println(ex.toString());
                getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "No Enforcable Code Elements were found with SetID: " + setID, ""));
            }
        } 
        return cEEList;
    }

    /**
     * @param cEEList the cEEList to set
     */
    public void setcEEList(ArrayList<EnforcableCodeElement> cEEList) {
        this.cEEList = cEEList;
    }

    /**
     * @return the currentCodeSet
     */
    public CodeSet getCurrentCodeSet() {
        currentCodeSet = getSessionBean().getActiveCodeSet();
        return currentCodeSet;
    }

    /**
     * @param currentCodeSet the currentCodeSet to set
     */
    public void setCurrentCodeSet(CodeSet currentCodeSet) {
        this.currentCodeSet = currentCodeSet;
    }

    /**
     * @return the formCodeSetElementID
     */
    public int getFormCodeSetElementID() {
        return formCodeSetElementID;
    }

    /**
     * @return the formMaxPenalty
     */
    public double getFormMaxPenalty() {
        if(selectedECE != null){
            formMaxPenalty = selectedECE.getMaxPenalty();
        }
        return formMaxPenalty;
    }

    /**
     * @return the formMinPenalty
     */
    public double getFormMinPenalty() {
        if(selectedECE != null){
            formMinPenalty = selectedECE.getMinPenalty();
        }
        return formMinPenalty;
    }

    /**
     * @return the formNormPenalty
     */
    public double getFormNormPenalty() {
        if(selectedECE != null){
            formNormPenalty = selectedECE.getNormPenalty();
        }
        return formNormPenalty;
    }

    /**
     * @return the formPenaltyNotes
     */
    public String getFormPenaltyNotes() {
        if(selectedECE != null){
            formPenaltyNotes = selectedECE.getPenaltyNotes();
        }
        return formPenaltyNotes;
    }

    /**
     * @return the formNormDaysToComply
     */
    public int getFormNormDaysToComply() {
        if(selectedECE != null){
            formNormDaysToComply = selectedECE.getNormDaysToComply();
        }
        return formNormDaysToComply;
    }

    /**
     * @return the formDaysToComplyNotes
     */
    public String getFormDaysToComplyNotes() {
        if(selectedECE != null){
            formDaysToComplyNotes = selectedECE.getDaysToComplyNotes();
        }
        return formDaysToComplyNotes;
    }

    /**
     * @param formCodeSetElementID the formCodeSetElementID to set
     */
    public void setFormCodeSetElementID(int formCodeSetElementID) {
        this.formCodeSetElementID = formCodeSetElementID;
    }

    /**
     * @param formMaxPenalty the formMaxPenalty to set
     */
    public void setFormMaxPenalty(double formMaxPenalty) {
        this.formMaxPenalty = formMaxPenalty;
    }

    /**
     * @param formMinPenalty the formMinPenalty to set
     */
    public void setFormMinPenalty(double formMinPenalty) {
        this.formMinPenalty = formMinPenalty;
    }

    /**
     * @param formNormPenalty the formNormPenalty to set
     */
    public void setFormNormPenalty(double formNormPenalty) {
        this.formNormPenalty = formNormPenalty;
    }

    /**
     * @param formPenaltyNotes the formPenaltyNotes to set
     */
    public void setFormPenaltyNotes(String formPenaltyNotes) {
        this.formPenaltyNotes = formPenaltyNotes;
    }

    /**
     * @param formNormDaysToComply the formNormDaysToComply to set
     */
    public void setFormNormDaysToComply(int formNormDaysToComply) {
        this.formNormDaysToComply = formNormDaysToComply;
    }

    /**
     * @param formDaysToComplyNotes the formDaysToComplyNotes to set
     */
    public void setFormDaysToComplyNotes(String formDaysToComplyNotes) {
        this.formDaysToComplyNotes = formDaysToComplyNotes;
    }
    
    
    
}
