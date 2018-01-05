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

import com.tcvcog.tcvce.coordinators.CodeCoordinator;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CodeSource;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sylvia
 */
public class CodeSourceBB extends BackingBeanUtils implements Serializable{
    
    
    private LinkedList<CodeSource> codeSources;
    private int sourceID;
    private CodeSource codeSource;
    private CodeSource selectedCodeSource;
    private int selectedCodeSourceID;
    
    private int formSourceID;
    private String formSourceName;
    private int formSourceYear;
    private String formSourceDescription;
    private boolean formSourceIsActive;
    private String formSourceURL;
    private String formSourceNotes;

    /**
     * Creates a new instance of CodeSourceBB
     */
    public CodeSourceBB() {
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    public String lookUpSource(){
        System.out.println("CodeSourceBB.lookUpSource");
        
        try {
            codeSource = getCodeCoordinator().retrieveCodeSourceByID(sourceID);
            formSourceName = codeSource.getSourceName();
        } catch (IntegrationException ex) {
            
            System.out.println("CodeSourceBB.lookUpSource: Catching integreation exception");
            
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Unable to find Code Source", ""));
        }
        
        
        return null;
    }
    
    public String retrieveAllCodeSources(){
        System.out.println("CodeSourceBB.retrieveAllCodeSources");
        CodeCoordinator coord = getCodeCoordinator();
        
        try {
            codeSources = coord.retrieveAllCodeSources();
        } catch (IntegrationException ex) {
            System.out.println("CodeSourceBB.lookUpSource: Catching integreation exception: " + ex.toString());
            
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Unable to find Code Source", ""));
        }
        
        return null;
        
    }
    
    
    public void lookUpSource(ActionEvent event){
        System.out.println("CodeSourceBB.lookUpSource: selectedSource name: " + selectedCodeSource.getSourceName());
        if(selectedCodeSource != null){
            formSourceID = selectedCodeSource.getSourceID();
            formSourceName = selectedCodeSource.getSourceName();
            formSourceYear = selectedCodeSource.getSourceYear();
            formSourceDescription = selectedCodeSource.getSourceDescription();
            formSourceIsActive = selectedCodeSource.isIsActive();
            formSourceURL = selectedCodeSource.getURL();
            formSourceNotes = selectedCodeSource.getSourceNotes();
        } else {
            
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Please select a code source in the table to view source details.", ""));
            
        }
        
    }
    
    public void updateSource(ActionEvent event){
        System.out.println("CodeSourceBB.updateSource");
        selectedCodeSource.setSourceID(formSourceID);
        selectedCodeSource.setSourceName(formSourceName);
        selectedCodeSource.setSourceYear(formSourceYear);
        selectedCodeSource.setSourceDescription(formSourceDescription);
        selectedCodeSource.setIsActive(formSourceIsActive);
        selectedCodeSource.setURL(formSourceURL);
        selectedCodeSource.setSourceNotes(formSourceNotes);
        
        try {
            getCodeCoordinator().updateCodeSource(selectedCodeSource);
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Unable to UpdateCodeSource", 
                            "Ensure the sourceID matches an existing code source from the table above"));
        }
        
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Successfully updated code source", ""));
        
        
    }
    
    public String addNewSource(){
        
        CodeSource newCodeSource = new CodeSource();
        newCodeSource.setSourceName(formSourceName);
        newCodeSource.setSourceYear(formSourceYear);
        newCodeSource.setSourceDescription(formSourceDescription);
        newCodeSource.setIsActive(formSourceIsActive);
        newCodeSource.setURL(formSourceURL);
        newCodeSource.setSourceNotes(formSourceNotes);
        try {
            getCodeCoordinator().addNewCodeSource(newCodeSource);
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Successfully added new code source", 
                            ""));
        
            return "success";
        } catch (IntegrationException ex) {
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Unable to Add Source", 
                            "This error will require administrator attention"));
             return null;
        }
        
        
    }


    /**
     * @return the sourceID
     */
    public int getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * @return the codeSources
     */
    public LinkedList<CodeSource> getCodeSources() {
        return codeSources;
    }

    /**
     * @param codeSources the codeSources to set
     */
    public void setCodeSources(LinkedList<CodeSource> codeSources) {
        this.codeSources = codeSources;
    }

    /**
     * @return the codeSource
     */
    public CodeSource getCodeSource() {
        return codeSource;
    }

    /**
     * @param codeSource the codeSource to set
     */
    public void setCodeSource(CodeSource codeSource) {
        this.codeSource = codeSource;
    }

    /**
     * @return the formSourceName
     */
    public String getFormSourceName() {
        return formSourceName;
    }

    /**
     * @param formSourceName the formSourceName to set
     */
    public void setFormSourceName(String formSourceName) {
        this.formSourceName = formSourceName;
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

    /**
     * @return the selectedCodeSource
     */
    public CodeSource getSelectedCodeSource() {
        return selectedCodeSource;
    }

    /**
     * @param selectedCodeSource the selectedCodeSource to set
     */
    public void setSelectedCodeSource(CodeSource selectedCodeSource) {
        this.selectedCodeSource = selectedCodeSource;
    }

    /**
     * @return the formSourceID
     */
    public int getFormSourceID() {
        return formSourceID;
    }

    /**
     * @param formSourceID the formSourceID to set
     */
    public void setFormSourceID(int formSourceID) {
        this.formSourceID = formSourceID;
    }

    /**
     * @return the formSourceYear
     */
    public int getFormSourceYear() {
        return formSourceYear;
    }

    /**
     * @param formSourceYear the formSourceYear to set
     */
    public void setFormSourceYear(int formSourceYear) {
        this.formSourceYear = formSourceYear;
    }

    /**
     * @return the formSourceDescription
     */
    public String getFormSourceDescription() {
        return formSourceDescription;
    }

    /**
     * @param formSourceDescription the formSourceDescription to set
     */
    public void setFormSourceDescription(String formSourceDescription) {
        this.formSourceDescription = formSourceDescription;
    }

    /**
     * @return the formSourceIsActive
     */
    public boolean isFormSourceIsActive() {
        return formSourceIsActive;
    }

    /**
     * @param formSourceIsActive the formSourceIsActive to set
     */
    public void setFormSourceIsActive(boolean formSourceIsActive) {
        this.formSourceIsActive = formSourceIsActive;
    }

    /**
     * @return the formSourceURL
     */
    public String getFormSourceURL() {
        return formSourceURL;
    }

    /**
     * @param formSourceURL the formSourceURL to set
     */
    public void setFormSourceURL(String formSourceURL) {
        this.formSourceURL = formSourceURL;
    }

    /**
     * @return the formSourceNotes
     */
    public String getFormSourceNotes() {
        return formSourceNotes;
    }

    /**
     * @param formSourceNotes the formSourceNotes to set
     */
    public void setFormSourceNotes(String formSourceNotes) {
        this.formSourceNotes = formSourceNotes;
    }
    
    
}
