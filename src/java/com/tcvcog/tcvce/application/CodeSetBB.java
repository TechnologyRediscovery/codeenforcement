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
import com.tcvcog.tcvce.entities.Municipality;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.MunicipalityIntegrator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author sylvia
 */


public class CodeSetBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeSetBB
     */
    public CodeSetBB() {
    }
    
    @ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
    
    private HashMap muniMap;
    private Municipality selectedMuni;
    private LinkedList<CodeSet> codeSetList;
    // used by codeSetElementManage
    private CodeSet selectedCodeSet;
    private EnforcableCodeElement selectedEnforcableCodeElement;
    
    
    private int currentCodeSetID;
    
    private int selectedMuniCode;
    
    private String currentCodeSetName;
    private String currentCodeSetDescription;
    private int currentCodeSetMuniCode;
    private String currentCodeSetMuniName;
    
    private CodeSet setToUpdate;
    
    
    private String newCodeElementTypeName;
    private String newCodeElementTypeDesc;
    
    private String formCodeSetName;
    private String formCodeSetDescription;
    private int formMuniCode;
    private int formNewMuniCode;
    
    public void retrieveCodeSetsByMuni(ActionEvent event){
        CodeIntegrator codeInt = getCodeIntegrator();
        LinkedList<CodeSet> retrievedCodeSetList;
        try {
            System.out.println("CodeSetBB.retrieveCodeSetsByMuniID | selected Muni Code:  "+ selectedMuniCode);
            
            retrievedCodeSetList = codeInt.getCodeSetsByMuniCode(selectedMuniCode);
            if(!retrievedCodeSetList.isEmpty()){
                codeSetList = retrievedCodeSetList;
                getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Code sets loaded for municipality with ID Code: " + selectedMuniCode, 
                                ""));
            } else {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hark! No code sets exist for the selected muni. Conveneintly, you may add one at the bottom of this page.", 
                            ""));
                
            }
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to load code sets for this municipality.", 
                        "This must be corrected by the System Administrator"));
        }
        
        //return "";
        
    }
    
    public void updateSelectedCodeSet(ActionEvent event){
        setToUpdate = new CodeSet();
        setToUpdate.setCodeSetName(formCodeSetName);
        setToUpdate.setCodeSetDescription(formCodeSetDescription);
        setToUpdate.setMuniCode(formMuniCode);
        setToUpdate.setCodeSetID(selectedCodeSet.getCodeSetID());
        
        CodeIntegrator codeInt = getCodeIntegrator();
        try {
            System.out.println("CodeSetBB.updateSelectedCodeSet | selectedCodeSetName: " + selectedCodeSet.getCodeSetName());
            codeInt.updateCodeSet(setToUpdate);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to update code set, sorry.", 
                        "This must be corrected by the System Administrator"));
        }
        
    }
    
    public String manageCodeSetElements(){
        System.out.println("CodeSetBB.manageCodeSetElements");
        //CodeIntegrator codeInt = getCodeIntegrator();
        
//        if(selectedCodeSet != null){
//            Visit visit = new Visit();
//            visit.setActiveCodeSet(selectedCodeSet);
//            sessionManager.setVisit(visit);
//            
//            //try {
//                //sessionManager.getVisit().setActiveCodeSet(selectedCodeSet);
//                //System.out.println("CodeSetBB.manageCodeSetElements | activeCodeSet: " + sessionManager.getVisit().getActiveCodeSet().getCodeSetName());
////            } catch (IntegrationException ex) {
////                System.out.println(ex.toString());
////                getFacesContext().addMessage(null,
////                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
////                            "Unable to find Enforcable Code Elements, sorry.", 
////                            "There may be no Enforcable Code Elements in this set"));
////
////            }
//            
//        } else {
//                getFacesContext().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
//                            "Hark! No code set selected--Please select a code set", ""));
//            
//        }
//        Visit visit = new Visit();
//        visit.setEceList(eceList);
//        setVisit(visit);
//        System.out.println("CodeSetBB.manageCodeSetElements | gotten visit: " + getVisit().getEceList().peek().getOrdsecTitle());
        return "codeSetElementManage";
    }
    
    

    
    public void displaySelectedCodeSet(ActionEvent event){
        System.out.println("CodeSetBB.displaySelectedCodeSet");
        
        if(selectedCodeSet != null){
            formCodeSetName = selectedCodeSet.getCodeSetName();
            formCodeSetDescription = selectedCodeSet.getCodeSetDescription();
            
        } else {
                getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hark! No code set selected--Please select a code set", ""));
            
        }
    }
    
    public void addNewCodeSet(ActionEvent event){
        CodeSet cs = new CodeSet();
        cs.setMuniCode(formNewMuniCode);
        cs.setCodeSetName(newCodeElementTypeName);
        cs.setCodeSetDescription(newCodeElementTypeDesc);
        
        System.out.println("CodeSetBB.addNewCodeSet | set to add name: " + cs.getCodeSetName());
        CodeIntegrator codeInt = getCodeIntegrator();
        
        try {
            codeInt.insertCodeSet(cs);
        } catch (IntegrationException ex) {
              getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Unable to add code set to DB", "Your fearless system administrator will need to correct this."));
        }
        getFacesContext().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_INFO, 
                      "New Code Set named " + cs.getCodeSetName() + "!", ""));
        
//        return "";
        
    }

    /**
     * @return the muniMap
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public HashMap getMuniMap() throws IntegrationException{
        MunicipalityIntegrator muniInt = getMunicipalityIntegrator();
        muniMap = muniInt.getMunicipalityMap();
        return muniMap;
    }

    /**
     * @param muniMap the muniMap to set
     */
    public void setMuniMap(HashMap muniMap) {
        this.muniMap = muniMap;
    }

    /**
     * @return the selectedMuni
     */
    public Municipality getSelectedMuni() {
        return selectedMuni;
    }

    /**
     * @param selectedMuni the selectedMuni to set
     */
    public void setSelectedMuni(Municipality selectedMuni) {
        this.selectedMuni = selectedMuni;
    }

    /**
     * @return the selectedMuniCode
     */
    public int getSelectedMuniCode() {
        return selectedMuniCode;
    }

    /**
     * @param selectedMuniCode the selectedMuniCode to set
     */
    public void setSelectedMuniCode(int selectedMuniCode) {
        this.selectedMuniCode = selectedMuniCode;
    }

    /**
     * @return the currentCodeSetID
     */
    public int getCurrentCodeSetID() {
        return currentCodeSetID;
    }

    /**
     * @param currentCodeSetID the currentCodeSetID to set
     */
    public void setCurrentCodeSetID(int currentCodeSetID) {
        this.currentCodeSetID = currentCodeSetID;
    }

    /**
     * @return the currentCodeSetName
     */
    public String getCurrentCodeSetName() {
        return currentCodeSetName;
    }

    /**
     * @param currentCodeSetName the currentCodeSetName to set
     */
    public void setCurrentCodeSetName(String currentCodeSetName) {
        this.currentCodeSetName = currentCodeSetName;
    }

    /**
     * @return the currentCodeSetDescription
     */
    public String getCurrentCodeSetDescription() {
        return currentCodeSetDescription;
    }

    /**
     * @param currentCodeSetDescription the currentCodeSetDescription to set
     */
    public void setCurrentCodeSetDescription(String currentCodeSetDescription) {
        this.currentCodeSetDescription = currentCodeSetDescription;
    }

    /**
     * @return the currentCodeSetMuniCode
     */
    public int getCurrentCodeSetMuniCode() {
        return currentCodeSetMuniCode;
    }

    /**
     * @param currentCodeSetMuniCode the currentCodeSetMuniCode to set
     */
    public void setCurrentCodeSetMuniCode(int currentCodeSetMuniCode) {
        this.currentCodeSetMuniCode = currentCodeSetMuniCode;
    }

    /**
     * @return the formCodeSetName
     */
    public String getFormCodeSetName() {
        return formCodeSetName;
    }

    /**
     * @param formCodeSetName the formCodeSetName to set
     */
    public void setFormCodeSetName(String formCodeSetName) {
        this.formCodeSetName = formCodeSetName;
    }

    /**
     * @return the formCodeSetDescription
     */
    public String getFormCodeSetDescription() {
        return formCodeSetDescription;
    }

    /**
     * @param formCodeSetDescription the formCodeSetDescription to set
     */
    public void setFormCodeSetDescription(String formCodeSetDescription) {
        this.formCodeSetDescription = formCodeSetDescription;
    }

    /**
     * @return the codeSetList
     */
    public LinkedList<CodeSet> getCodeSetList() {
        
        CodeIntegrator codeInt = getCodeIntegrator();
        try {
            codeSetList = codeInt.getCodeSetsByMuniCode(selectedMuniCode);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to retrieve code sets by Muni Code, sorry.", 
                        "This must be corrected by the System Administrator"));
        }
        
        return codeSetList;
    }

    /**
     * @param codeSetList the codeSetList to set
     */
    public void setCodeSetList(LinkedList<CodeSet> codeSetList) {
        this.codeSetList = codeSetList;
    }

    /**
     * @return the selectedCodeSet
     */
    public CodeSet getSelectedCodeSet() {
        return selectedCodeSet;
    }

    /**
     * @param selectedCodeSet the selectedCodeSet to set
     */
    public void setSelectedCodeSet(CodeSet selectedCodeSet) {
        this.selectedCodeSet = selectedCodeSet;
    }

    /**
     * @return the currentCodeSetMuniName
     */
    public String getCurrentCodeSetMuniName() {
        return currentCodeSetMuniName;
    }

    /**
     * @param currentCodeSetMuniName the currentCodeSetMuniName to set
     */
    public void setCurrentCodeSetMuniName(String currentCodeSetMuniName) {
        this.currentCodeSetMuniName = currentCodeSetMuniName;
    }

    /**
     * @return the formMuniCode
     */
    public int getFormMuniCode() {
        return formMuniCode;
    }

    /**
     * @param formMuniCode the formMuniCode to set
     */
    public void setFormMuniCode(int formMuniCode) {
        this.formMuniCode = formMuniCode;
    }

    /**
     * @return the setToUpdate
     */
    public CodeSet getSetToUpdate() {
        return setToUpdate;
    }

    /**
     * @param setToUpdate the setToUpdate to set
     */
    public void setSetToUpdate(CodeSet setToUpdate) {
        this.setToUpdate = setToUpdate;
    }

    /**
     * @return the newCodeElementTypeName
     */
    public String getNewCodeElementTypeName() {
        return newCodeElementTypeName;
    }

    /**
     * @param newCodeElementTypeName the newCodeElementTypeName to set
     */
    public void setNewCodeElementTypeName(String newCodeElementTypeName) {
        this.newCodeElementTypeName = newCodeElementTypeName;
    }

    /**
     * @return the newCodeElementTypeDesc
     */
    public String getNewCodeElementTypeDesc() {
        return newCodeElementTypeDesc;
    }

    /**
     * @param newCodeElementTypeDesc the newCodeElementTypeDesc to set
     */
    public void setNewCodeElementTypeDesc(String newCodeElementTypeDesc) {
        this.newCodeElementTypeDesc = newCodeElementTypeDesc;
    }

    /**
     * @return the formNewMuniCode
     */
    public int getFormNewMuniCode() {
        return formNewMuniCode;
    }

    /**
     * @param formNewMuniCode the formNewMuniCode to set
     */
    public void setFormNewMuniCode(int formNewMuniCode) {
        this.formNewMuniCode = formNewMuniCode;
    }

   

    /**
     * @return the sessionManager
     */
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @param sessionManager the sessionManager to set
     */
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * @return the selectedEnforcableCodeElement
     */
    public EnforcableCodeElement getSelectedEnforcableCodeElement() {
        return selectedEnforcableCodeElement;
    }

    /**
     * @param selectedEnforcableCodeElement the selectedEnforcableCodeElement to set
     */
    public void setSelectedEnforcableCodeElement(EnforcableCodeElement selectedEnforcableCodeElement) {
        this.selectedEnforcableCodeElement = selectedEnforcableCodeElement;
    }
    
    
    
}
