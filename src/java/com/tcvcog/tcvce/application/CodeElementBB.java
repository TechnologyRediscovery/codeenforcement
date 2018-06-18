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
import com.tcvcog.tcvce.entities.CodeElementGuideEntry;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.CodeViolationIntegrator;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Eric C. Darsow
 */
public class CodeElementBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeElementBB
     */
    public CodeElementBB() {
    }
    
    
    
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
    
    
    
    public String insertCodeElement(){
        
        
        
        CodeIntegrator codeIntegrator = getCodeIntegrator();
        CodeElement newCE = new CodeElement();
        
        newCE.setSource(activeCodeSource);
        newCE.setTypeID(formType);
        
        newCE.setOrdchapterNo(formOrdChapterNo);
        
        newCE.setOrdchapterTitle(formOrdChapterTitle);
        newCE.setOrdSecNum(formOrdSecNum);
        newCE.setOrdsecTitle(formOrdSecTitle);
        
        newCE.setOrdSubSecNum(formOrdSecNum);
        newCE.setOrdSubSecTitle(formOrdSubSecTitle);
        newCE.setOrdTechnicalText(formOrdTechnicalText);
        
        newCE.setOrdHumanFriendlyText(formOrdHumanFriendlyText);
        newCE.setDefaultPenalty(formDefaultPenalty);
        newCE.setIsActive(formIsActive);
        
        newCE.setIsEnforcementPriority(formIsEnforcementPriority);
        newCE.setResourceURL(formResourceURL);
        newCE.setInspectionTips(formInspectionTips);
        
        try {
            codeIntegrator.insertCodeElement(newCE);
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to add code elment to code source", 
                        "This must be corrected by the System Administrator"));
        }
        
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Successfully added code element to code source", ""));

        return "codeSourceManage";
    }
    

    /**
     * @return the formOrdChapterNo
     */
    public int getFormOrdChapterNo() {
        return formOrdChapterNo;
    }

    /**
     * @param formOrdChapterNo the formOrdChapterNo to set
     */
    public void setFormOrdChapterNo(int formOrdChapterNo) {
        this.formOrdChapterNo = formOrdChapterNo;
    }

    /**
     * @return the formOrdChapterTitle
     */
    public String getFormOrdChapterTitle() {
        return formOrdChapterTitle;
    }

    /**
     * @param formOrdChapterTitle the formOrdChapterTitle to set
     */
    public void setFormOrdChapterTitle(String formOrdChapterTitle) {
        this.formOrdChapterTitle = formOrdChapterTitle;
    }

    /**
     * @return the formOrdSecNum
     */
    public String getFormOrdSecNum() {
        return formOrdSecNum;
    }

    /**
     * @param formOrdSecNum the formOrdSecNum to set
     */
    public void setFormOrdSecNum(String formOrdSecNum) {
        this.formOrdSecNum = formOrdSecNum;
    }

    /**
     * @return the formOrdSecTitle
     */
    public String getFormOrdSecTitle() {
        return formOrdSecTitle;
    }

    /**
     * @param formOrdSecTitle the formOrdSecTitle to set
     */
    public void setFormOrdSecTitle(String formOrdSecTitle) {
        this.formOrdSecTitle = formOrdSecTitle;
    }

    /**
     * @return the formOrdSubSecNum
     */
    public String getFormOrdSubSecNum() {
        return formOrdSubSecNum;
    }

    /**
     * @param formOrdSubSecNum the formOrdSubSecNum to set
     */
    public void setFormOrdSubSecNum(String formOrdSubSecNum) {
        this.formOrdSubSecNum = formOrdSubSecNum;
    }

    /**
     * @return the formOrdSubSecTitle
     */
    public String getFormOrdSubSecTitle() {
        return formOrdSubSecTitle;
    }

    /**
     * @param formOrdSubSecTitle the formOrdSubSecTitle to set
     */
    public void setFormOrdSubSecTitle(String formOrdSubSecTitle) {
        this.formOrdSubSecTitle = formOrdSubSecTitle;
    }

    /**
     * @return the formOrdTechnicalText
     */
    public String getFormOrdTechnicalText() {
        return formOrdTechnicalText;
    }

    /**
     * @param formOrdTechnicalText the formOrdTechnicalText to set
     */
    public void setFormOrdTechnicalText(String formOrdTechnicalText) {
        this.formOrdTechnicalText = formOrdTechnicalText;
    }

    /**
     * @return the formOrdHumanFriendlyText
     */
    public String getFormOrdHumanFriendlyText() {
        return formOrdHumanFriendlyText;
    }

    /**
     * @param formOrdHumanFriendlyText the formOrdHumanFriendlyText to set
     */
    public void setFormOrdHumanFriendlyText(String formOrdHumanFriendlyText) {
        this.formOrdHumanFriendlyText = formOrdHumanFriendlyText;
    }

    /**
     * @return the formDefaultPenalty
     */
    public double getFormDefaultPenalty() {
        return formDefaultPenalty;
    }

    /**
     * @param formDefaultPenalty the formDefaultPenalty to set
     */
    public void setFormDefaultPenalty(double formDefaultPenalty) {
        this.formDefaultPenalty = formDefaultPenalty;
    }

    /**
     * @return the formIsActive
     */
    public boolean isFormIsActive() {
        return formIsActive;
    }

    /**
     * @param formIsActive the formIsActive to set
     */
    public void setFormIsActive(boolean formIsActive) {
        this.formIsActive = formIsActive;
    }

    /**
     * @return the formIsEnforcementPriority
     */
    public boolean isFormIsEnforcementPriority() {
        return formIsEnforcementPriority;
    }

    /**
     * @param formIsEnforcementPriority the formIsEnforcementPriority to set
     */
    public void setFormIsEnforcementPriority(boolean formIsEnforcementPriority) {
        this.formIsEnforcementPriority = formIsEnforcementPriority;
    }

    /**
     * @return the formResourceURL
     */
    public String getFormResourceURL() {
        return formResourceURL;
    }

    /**
     * @param formResourceURL the formResourceURL to set
     */
    public void setFormResourceURL(String formResourceURL) {
        this.formResourceURL = formResourceURL;
    }

    /**
     * @return the formInspectionTips
     */
    public String getFormInspectionTips() {
        return formInspectionTips;
    }

    /**
     * @param formInspectionTips the formInspectionTips to set
     */
    public void setFormInspectionTips(String formInspectionTips) {
        this.formInspectionTips = formInspectionTips;
    }

    /**
     * @return the formType
     */
    public int getFormType() {
        return formType;
    }

    /**
     * @param formType the formType to set
     */
    public void setFormType(int formType) {
        this.formType = formType;
    }

    /**
     * @return the activeCodeSource
     */
    public CodeSource getActiveCodeSource() {
      SessionCoordinator sessionManager = getSessionManager();
      activeCodeSource = sessionManager.getActiveCodeSource();
        return activeCodeSource;
    }

    /**
     * @param activeCodeSource the activeCodeSource to set
     */
    public void setActiveCodeSource(CodeSource activeCodeSource) {
        this.activeCodeSource = activeCodeSource;
    }
    
}
