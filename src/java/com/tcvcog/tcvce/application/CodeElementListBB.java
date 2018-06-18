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
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Eric C. Darsow
 */
public class CodeElementListBB extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of CodeElementListBB
     */
    public CodeElementListBB() {
        System.out.println("CodeElementListBB.CodeElementListBB | const");
    }
    
    private CodeElement selectedElement;
    private ArrayList<CodeElement> codeElementList;
    
    private CodeSource activeCodeSource;
    private int formType;
    
    private int formOrdChapterNo;
    
    private String formOrdChapterTitle;
    private String formOrdSecNum;
    private String formOrdSecTitle;
    
    private String formOrdSubSecNum;
    private String formOrdSubSecTitle;
    private String formOrdTechnicalText;
    
    private String formOrdHumanFriendlyText;
    private double formDefaultPenalty;
    private boolean formIsActive;
    
    private boolean formIsEnforcementPriority;
    private String formResourceURL;
    private String formInspectionTips;
    
    
    public String moveToUpdateCodeElement(){
        System.out.println("CodeElementListBB.moveToUpdateCodeElement | selectedElement: " + selectedElement.getOrdchapterTitle());
        if(selectedElement != null){
            
            
            return "codeElementUpdate";
            
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hark--No elemented selected. Please click on a code element first.", ""));
            return null;
        }
        
        
    }
    
    /**
     * TODO: Update for code element guide entry infrastructure
     * @return 
     */
    public String commitUpdatesToCodeElement() {
        CodeElement eleToUpdate = new CodeElement();
        eleToUpdate.setElementID(selectedElement.getElementID());
        System.out.println("CodeElementListBB.commitUpdatesToCodeElement | formtype = "+ formType);
        System.out.println("CodeElementListBB.commitUpdatesToCodeElement | selectedElement  = ");
        if(formType == 0){
//            eleToUpdate.setTypeID(selectedElement.getType().getCdelTypeID());
            
        } else {
            eleToUpdate.setTypeID(formType);
        }
        eleToUpdate.setOrdchapterNo(formOrdChapterNo);
        
        eleToUpdate.setOrdchapterTitle(formOrdChapterTitle);
        eleToUpdate.setOrdSecNum(formOrdSecNum);
        eleToUpdate.setOrdsecTitle(formOrdSecTitle);
        
        eleToUpdate.setOrdSecNum(formOrdSecNum);
        eleToUpdate.setOrdSubSecTitle(formOrdSubSecTitle);
        eleToUpdate.setOrdTechnicalText(formOrdTechnicalText);
        
        eleToUpdate.setOrdHumanFriendlyText(formOrdHumanFriendlyText);
        eleToUpdate.setDefaultPenalty(formDefaultPenalty);
        eleToUpdate.setIsActive(formIsActive);
        
        eleToUpdate.setIsEnforcementPriority(formIsEnforcementPriority);
        eleToUpdate.setResourceURL(formResourceURL);
        eleToUpdate.setInspectionTips(formInspectionTips);
        
        CodeIntegrator integrator = getCodeIntegrator();
        try {
            integrator.updateCodeElement(eleToUpdate);
            getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Code element updated!", ""));
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to update code element; most sincere apologies!", 
                        "This must be corrected by the System Administrator"));
        }
        
        return "codeElementList";
    }
    
    public void deleteCodeElement(ActionEvent event){
        
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
     * @return the codeElementList
     */
    public ArrayList<CodeElement> getCodeElementList() {
        SessionCoordinator sm = getSessionManager();
        CodeSource source = sm.getActiveCodeSource();
        CodeIntegrator codeIntegrator = getCodeIntegrator();
        try {
            codeElementList = codeIntegrator.getCodeElements(source.getSourceID());
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to populate list of code elements", 
                            "This error will require administrator attention"));
            
        }
        
        return codeElementList;
    }

    /**
     * @param codeElementList the codeElementList to set
     */
    public void setCodeElementList(ArrayList<CodeElement> codeElementList) {
        this.codeElementList = codeElementList;
    }

    /**
     * @return the activeCodeSource
     */
    public CodeSource getActiveCodeSource() {
        return activeCodeSource;
    }

    /**
     * @return the formType
     */
    public int getFormType() {
        formType = selectedElement.getTypeID();
        return  formType;
    }

    /**
     * @return the formOrdChapterNo
     */
    public int getFormOrdChapterNo() {
        formOrdChapterNo = selectedElement.getOrdchapterNo();
        return formOrdChapterNo;
    }

    /**
     * @return the formOrdChapterTitle
     */
    public String getFormOrdChapterTitle() {
        formOrdChapterTitle = selectedElement.getOrdchapterTitle();
        return formOrdChapterTitle;
    }

    /**
     * @return the formOrdSecNum
     */
    public String getFormOrdSecNum() {
        formOrdSecNum = selectedElement.getOrdSecNum();
        return formOrdSecNum;
    }

    /**
     * @return the formOrdSecTitle
     */
    public String getFormOrdSecTitle() {
        formOrdSecTitle = selectedElement.getOrdsecTitle();
        return formOrdSecTitle;
    }

    /**
     * @return the formOrdSubSecNum
     */
    public String getFormOrdSubSecNum() {
        formOrdSubSecNum = selectedElement.getOrdSubSecNum();
        return formOrdSubSecNum;
    }

    /**
     * @return the formOrdSubSecTitle
     */
    public String getFormOrdSubSecTitle() {
        formOrdSecTitle = selectedElement.getOrdSubSecTitle();
        return formOrdSubSecTitle;
    }

    /**
     * @return the formOrdTechnicalText
     */
    public String getFormOrdTechnicalText() {
        formOrdTechnicalText = selectedElement.getOrdTechnicalText();
        return formOrdTechnicalText;
    }

    /**
     * @return the formOrdHumanFriendlyText
     */
    public String getFormOrdHumanFriendlyText() {
        formOrdHumanFriendlyText = selectedElement.getOrdHumanFriendlyText();
        return formOrdHumanFriendlyText;
    }

    /**
     * @return the formDefaultPenalty
     */
    public double getFormDefaultPenalty() {
        formDefaultPenalty = selectedElement.getDefaultPenalty();
        return formDefaultPenalty;
    }

    /**
     * @return the formIsActive
     */
    public boolean isFormIsActive() {
        formIsActive = selectedElement.isIsActive();
        return formIsActive;
    }

    /**
     * @return the formIsEnforcementPriority
     */
    public boolean isFormIsEnforcementPriority() {
        formIsEnforcementPriority = selectedElement.isIsEnforcementPriority();
        return formIsEnforcementPriority;
    }

    /**
     * @return the formResourceURL
     */
    public String getFormResourceURL() {
        formResourceURL = selectedElement.getResourceURL();
        return formResourceURL;
    }

    /**
     * @return the formInspectionTips
     */
    public String getFormInspectionTips() {
        formInspectionTips = selectedElement.getInspectionTips();
        return formInspectionTips;
    }

    /**
     * @param activeCodeSource the activeCodeSource to set
     */
    public void setActiveCodeSource(CodeSource activeCodeSource) {
        this.activeCodeSource = activeCodeSource;
    }

    /**
     * @param formType the formType to set
     */
    public void setFormType(int formType) {
        this.formType = formType;
    }

    /**
     * @param formOrdChapterNo the formOrdChapterNo to set
     */
    public void setFormOrdChapterNo(int formOrdChapterNo) {
        this.formOrdChapterNo = formOrdChapterNo;
    }

    /**
     * @param formOrdChapterTitle the formOrdChapterTitle to set
     */
    public void setFormOrdChapterTitle(String formOrdChapterTitle) {
        this.formOrdChapterTitle = formOrdChapterTitle;
    }

    /**
     * @param formOrdSecNum the formOrdSecNum to set
     */
    public void setFormOrdSecNum(String formOrdSecNum) {
        this.formOrdSecNum = formOrdSecNum;
    }

    /**
     * @param formOrdSecTitle the formOrdSecTitle to set
     */
    public void setFormOrdSecTitle(String formOrdSecTitle) {
        this.formOrdSecTitle = formOrdSecTitle;
    }

    /**
     * @param formOrdSubSecNum the formOrdSubSecNum to set
     */
    public void setFormOrdSubSecNum(String formOrdSubSecNum) {
        this.formOrdSubSecNum = formOrdSubSecNum;
    }

    /**
     * @param formOrdSubSecTitle the formOrdSubSecTitle to set
     */
    public void setFormOrdSubSecTitle(String formOrdSubSecTitle) {
        this.formOrdSubSecTitle = formOrdSubSecTitle;
    }

    /**
     * @param formOrdTechnicalText the formOrdTechnicalText to set
     */
    public void setFormOrdTechnicalText(String formOrdTechnicalText) {
        this.formOrdTechnicalText = formOrdTechnicalText;
    }

    /**
     * @param formOrdHumanFriendlyText the formOrdHumanFriendlyText to set
     */
    public void setFormOrdHumanFriendlyText(String formOrdHumanFriendlyText) {
        this.formOrdHumanFriendlyText = formOrdHumanFriendlyText;
    }

    /**
     * @param formDefaultPenalty the formDefaultPenalty to set
     */
    public void setFormDefaultPenalty(double formDefaultPenalty) {
        this.formDefaultPenalty = formDefaultPenalty;
    }

    /**
     * @param formIsActive the formIsActive to set
     */
    public void setFormIsActive(boolean formIsActive) {
        this.formIsActive = formIsActive;
    }

    /**
     * @param formIsEnforcementPriority the formIsEnforcementPriority to set
     */
    public void setFormIsEnforcementPriority(boolean formIsEnforcementPriority) {
        this.formIsEnforcementPriority = formIsEnforcementPriority;
    }

    /**
     * @param formResourceURL the formResourceURL to set
     */
    public void setFormResourceURL(String formResourceURL) {
        this.formResourceURL = formResourceURL;
    }

    /**
     * @param formInspectionTips the formInspectionTips to set
     */
    public void setFormInspectionTips(String formInspectionTips) {
        this.formInspectionTips = formInspectionTips;
    }
    
    
}
