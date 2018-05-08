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

import com.tcvcog.tcvce.coordinators.CaseCoordinator;
import com.tcvcog.tcvce.domain.CaseLifecyleException;
import com.tcvcog.tcvce.domain.EventException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.NoticeOfViolation;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.CodeViolationIntegrator;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class NoticeOfViolationBB extends BackingBeanUtils implements Serializable {
    
    private String formLetterText;
    private Date formDateOfRecord;
    private NoticeOfViolation currentNotice;
    
    /**
     * Creates a new instance of NoticeOfViolationBB
     */
    public NoticeOfViolationBB() {
    }

    
    public String insertNoticeOfViolation(){
        SessionManager sm = getSessionManager();
        
        
        CodeViolationIntegrator cvi = getCodeViolationIntegrator();
        NoticeOfViolation notice = new NoticeOfViolation();
        notice.setNoticeText(formLetterText);
        notice.setDateOfRecord(formDateOfRecord.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        
        
        try {
            cvi.insertViolationLetter(sm.getVisit().getActiveCase(), notice);
        } catch (IntegrationException ex) {
            System.out.println(ex);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to saveDraft of notice letter", ""));
        }
        return "caseManage";
    }
    
    public String saveNoticeDraft(){
        SessionManager sm = getSessionManager();
        CECase c = sm.getVisit().getActiveCase();
        
        CodeViolationIntegrator ci = getCodeViolationIntegrator();
        try {
        
            if(currentNotice.getNoticeID() == 0){
                ci.insertViolationLetter(c, currentNotice);
                
            } else {
                ci.updateViolationLetter(currentNotice);
            }
            
        } catch (IntegrationException ex) {
            System.out.println(ex);
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to saveDraft of notice letter", ""));
        }
        return "caseManage";
    } // close method
    
    public String deployNotice() throws EventException{
        
        CaseCoordinator caseCoord = getCaseCoordinator();
        SessionManager sm = getSessionManager();
        CECase ceCase = sm.getVisit().getActiveCase();
        try {
            caseCoord.deployNoticeOfViolation(ceCase, currentNotice);
        } catch (CaseLifecyleException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to deploy notice due to a business logic error", ""));
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to update case phase due to a database connectivity error",
                "this issue must be corrected by a system administrator, sorry"));
        }
        
        return "caseManage";
        
    }
    
    

    /**
     * @return the formLetterText
     */
    public String getFormLetterText() {
        formLetterText = currentNotice.getNoticeText();
        
        return formLetterText;
    }

    /**
     * @param formLetterText the formLetterText to set
     */
    public void setFormLetterText(String formLetterText) {
        this.formLetterText = formLetterText;
    }

    /**
     * @return the formDateOfRecord
     */
    public Date getFormDateOfRecord() {
        return formDateOfRecord;
    }

    /**
     * @param formDateOfRecord the formDateOfRecord to set
     */
    public void setFormDateOfRecord(Date formDateOfRecord) {
        this.formDateOfRecord = formDateOfRecord;
    }

    /**
     * @return the currentNotice
     */
    public NoticeOfViolation getCurrentNotice() {
        SessionManager sm = getSessionManager();
        currentNotice = sm.getVisit().getActiveNotice();
        return currentNotice;
    }

    /**
     * @param currentNotice the currentNotice to set
     */
    public void setCurrentNotice(NoticeOfViolation currentNotice) {
        this.currentNotice = currentNotice;
    }
    
}
