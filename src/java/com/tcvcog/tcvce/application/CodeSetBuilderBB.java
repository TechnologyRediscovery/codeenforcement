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
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.CodeSet;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.entities.CodeElementEnforcable;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sylvia
 */
public class CodeSetBuilderBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeSetBuilderBB
     */
    public CodeSetBuilderBB() {
    }
    
    private CodeSet codeSet;
    private LinkedList<CodeSource> codeSourceList;
    private int selectedCodeSourceID;
    private HashMap<String, CodeSource> codeSourceMap;
    private LinkedList<CodeElement> codeElementList;
    private CodeElement selectedElement;
    
    // form fields mapped to CodeElementEnforcable fields
    private int formCodeSetElementID;
    //private CodeElement formCodeElement; // populated with selectedElement field
    private double formMaxPenalty;
    private double formMinPenalty;
    private double formNormPenalty;
    private String formPenaltyNotes;
    private int formNormDaysToComply;
    private String formDaysToComplyNotes; 
    
     
    public void retrieveCodeElementsFromSelectedSource(ActionEvent event){
        System.out.println("CodeSetBuilderBB.retrieveCodeElementsFromSelectedSource | Start of method");
        CodeIntegrator integrator = getCodeIntegrator();
        try {
            System.out.println("CodeSetBuilderBB.retrieveCodeElementsFromSelectedSource | selected source: " + selectedCodeSourceID);
            codeElementList = integrator.getCodeElements(selectedCodeSourceID);
        } catch (IntegrationException ex) {
               System.out.println(ex.toString());
               getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to find any code elements in the selected source, sorry.", ""));
        }
        
    }
    
    public void prepareSlectedElementForCodeSet(ActionEvent event){
        // used for page reload only
        
    }
    
    public String viewCodeSetElementsInSet(){
        SessionManager sm = getSessionManager();
            sm.getVisit().setActiveCodeSet(codeSet);
            if (codeSet != null){
                //System.out.println("CodeSetBB.buildCodeSet | selected set: " + selectedCodeSet.getCodeSetName());
                return "codeSetBuilder";
            } else {
                return "";
            }
    }
    
    public void addElementToSet(ActionEvent event){
        CodeElementEnforcable ece = new CodeElementEnforcable();
        ece.setCodeElement(selectedElement);
        ece.setMaxPenalty(formMaxPenalty);
        ece.setMinPenalty(formMinPenalty);
        ece.setNormPenalty(formNormPenalty);
        ece.setPenaltyNotes(formPenaltyNotes);
        ece.setNormDaysToComply(formNormDaysToComply);
        ece.setDaysToComplyNotes(formDaysToComplyNotes);
        
        CodeIntegrator integrator = getCodeIntegrator();
        
        try {
            integrator.addEnforcableCodeElementToCodeSet(ece, codeSet.getCodeSetID());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Successfully created enforcable code element!", ""));            
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Unable to add enforcement data to code element, sorry.", "This is an unwanted system error."));            
        }
    }

    /**
     * @return the codeSet
     */
    public CodeSet getCodeSet() {
        SessionManager sm = getSessionManager();
        CodeSet cs = sm.getVisit().getActiveCodeSet();
        codeSet = cs;
        return codeSet;
    }

    /**
     * @param codeSet the codeSet to set
     */
    public void setCodeSet(CodeSet codeSet) {
        this.codeSet = codeSet;
    }

    /**
     * @return the codeSourceList
     */
    public LinkedList<CodeSource> getCodeSourceList() {
        return codeSourceList;
    }

    /**
     * @param codeSourceList the codeSourceList to set
     */
    public void setCodeSourceList(LinkedList<CodeSource> codeSourceList) {
        this.codeSourceList = codeSourceList;
    }

    /**
     * @return the codeElementList
     */
    public LinkedList<CodeElement> getCodeElementList() {
        return codeElementList;
    }

    /**
     * @param codeElementList the codeElementList to set
     */
    public void setCodeElementList(LinkedList<CodeElement> codeElementList) {
        this.codeElementList = codeElementList;
    }

    /**
     * @return the selectedElement
     */
    public CodeElement getSelectedElement() {
        return selectedElement;
    }

    /**
     * @param selectedElement the selectedElement to set
     */
    public void setSelectedElement(CodeElement selectedElement) {
        this.selectedElement = selectedElement;
    }

    /**
     * @return the codeSourceMap
     */
    public HashMap getCodeSourceMap() {
        
        System.out.println("CodeSetBuilderBB.getCodeSourceMap");
        CodeIntegrator integrator = getCodeIntegrator();
        try {
            codeSourceMap = integrator.getCodeSourceMap();
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to find code sources, sorry.", "This is an unwanted system error."));
        }
        return codeSourceMap;
    }

    /**
     * @param codeSourceMap the codeSourceMap to set
     */
    public void setCodeSourceMap(HashMap<String, CodeSource> codeSourceMap) {
        this.codeSourceMap = codeSourceMap;
    }

    /**
     * @return the selectedCodeSource
     */
    public int getSelectedCodeSource() {
        return selectedCodeSourceID;
    }

    /**
     * @param selectedCodeSource the selectedCodeSource to set
     */
    public void setSelectedCodeSource(int selectedCodeSource) {
        this.selectedCodeSourceID = selectedCodeSource;
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
        return formMaxPenalty;
    }

    /**
     * @return the formMinPenalty
     */
    public double getFormMinPenalty() {
        return formMinPenalty;
    }

    /**
     * @return the formNormPenalty
     */
    public double getFormNormPenalty() {
        return formNormPenalty;
    }

    /**
     * @return the formPenaltyNotes
     */
    public String getFormPenaltyNotes() {
        return formPenaltyNotes;
    }

    /**
     * @return the formNormDaysToComply
     */
    public int getFormNormDaysToComply() {
        return formNormDaysToComply;
    }

    /**
     * @return the formDaysToComplyNotes
     */
    public String getFormDaysToComplyNotes() {
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

    /**
     * @return the selectedCodeSourceID
     */
    public int getSelectedCodeSourceID() {
        return selectedCodeSourceID;
    }

    /**
     * @param selectedCodeSourceID the selectedCodeSourceID to set
     */
    public void setSelectedCodeSourceID(int selectedCodeSourceID) {
        this.selectedCodeSourceID = selectedCodeSourceID;
    }
    
    
    
}
