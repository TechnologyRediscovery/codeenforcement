/*
 * Copyright (C) 2017 Turtle Creek Valley
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
package com.tcvcog.tcvce.coordinators;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.application.SessionManager;
import com.tcvcog.tcvce.domain.CaseLifecyleException;
import com.tcvcog.tcvce.domain.EventException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.entities.Citation;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.NoticeOfViolation;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import com.tcvcog.tcvce.integration.CitationIntegrator;
import com.tcvcog.tcvce.integration.CodeViolationIntegrator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class CaseCoordinator extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CaseCoordinator
     */
    public CaseCoordinator() {
    
    }
    
    public void createNewCECase(CECase newCase) throws IntegrationException{
        
        CaseIntegrator ci = getCaseIntegrator();
        
        // set default status to prelim investigation pending
        newCase.setCasePhase(CasePhase.PrelimInvestigationPending);
        
        CECase newlyAddedCase = ci.insertNewCECase(newCase);
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveCase(newlyAddedCase);
        
    }
    
    private CasePhase getNextCasePhase(CECase c) throws CaseLifecyleException{
        CasePhase currentPhase = c.getCasePhase();
        CasePhase nextPhaseInSequence;
        
        switch(currentPhase){
            
            case PrelimInvestigationPending:
                nextPhaseInSequence = CasePhase.NoticeDelivery;
                break;
                // conduct inital investigation
                // compose and deply notice of violation
            case NoticeDelivery:
                nextPhaseInSequence = CasePhase.InitialComplianceTimeframe;
                break;
                // Letter marked with a send date
            case InitialComplianceTimeframe:
                nextPhaseInSequence = CasePhase.SecondaryComplianceTimeframe;
                break;
                // compliance inspection
            case SecondaryComplianceTimeframe:
                nextPhaseInSequence = CasePhase.AwaitingHearingDate;
                break;
                // Filing of citation
            case AwaitingHearingDate:
                nextPhaseInSequence = CasePhase.HearingPreparation;
                break;
                // hearing date scheduled
            case HearingPreparation:
                nextPhaseInSequence = CasePhase.InitialPostHearingComplianceTimeframe;
                break;
                // hearing not resulting in a case closing
            case InitialPostHearingComplianceTimeframe:
                nextPhaseInSequence = CasePhase.SecondaryPostHearingComplianceTimeframe;
                break;
                
            case Closed:
                throw new CaseLifecyleException("Cannot advance a closed case to any other phase");
            
            case InactiveHolding:
                throw new CaseLifecyleException("Cases in inactive holding must have "
                        + "their case phase overriden manually to return to the case management flow");
                
            default:
                throw new CaseLifecyleException("Unable to determine next case phase, sorry");
        }
        
        return nextPhaseInSequence;
    }
    
    // I think this method is deprecated by the second overriden method
    private void advanceToNextCasePhase(CECase ceCase) throws CaseLifecyleException, IntegrationException, EventException{
        CaseIntegrator caseInt = getCaseIntegrator();
        EventCoordinator eventCoor = getEventCoordinator();
        
        
        CasePhase nextPhase = getNextCasePhase(ceCase);
        CasePhase pastPhase = ceCase.getCasePhase();
        ceCase.setCasePhase(nextPhase);
        
        // we must ship the case to the integrator with the case phase updated
        // because the integrator does not implement any business logic
        caseInt.changeCECasePhase(ceCase);
        // If we get an integration exception, the case phase change event
        // is never generated since execution returns to caller through an exception
//        eventCoor.logCommittedCasePhaseChange(ceCase, pastPhase);
    }
    
    /**
     * Updates a case's phase.
     * @param ceCase case whose phase has not been updated
     * @param nextPhase the desired phase to which the case should be assigned
     */
    public void advanceToNextCasePhase(CECase ceCase, CasePhase nextPhase){
         CaseIntegrator caseInt = getCaseIntegrator();
         EventCoordinator eventCoor = getEventCoordinator();
 
         CasePhase pastPhase = ceCase.getCasePhase();
         ceCase.setCasePhase(nextPhase);
        
        try {
            // we must ship the case to the integrator with the case phase updated
            // because the integrator does not implement any business logic
            caseInt.changeCECasePhase(ceCase);
            //eventCoor.logCommittedCasePhaseChange(ceCase, pastPhase);
        
        } catch (IntegrationException ex) {
            Logger.getLogger(CaseCoordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public LinkedList retrieveViolationList(CECase ceCase) throws IntegrationException{
        LinkedList<CodeViolation> ll;
        CodeViolationIntegrator cvi = getCodeViolationIntegrator();
        ll = cvi.getCodeViolations(ceCase);
        return ll;
    }
    
    public NoticeOfViolation generateNewNoticeOfViolation(CECase ceCase){
        System.out.println("CaseCoordinator.genreateNewNOV | input case: " +ceCase.getViolationList());
        NoticeOfViolation newNotice = new NoticeOfViolation();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Automatically generated letter content<br><br>");
        
        LinkedList<CodeViolation> violationList = ceCase.getViolationList();
        
        for(Iterator<CodeViolation> iter = violationList.iterator()
                ;iter.hasNext() ; ){
            CodeViolation cv = (CodeViolation) iter.next();
            System.out.println("CaseCoordinator.genreateNewNOV | violation adding: " + cv.getDescription());
            
            sb.append(cv.getViolatedEnfElement().getCodeElement().getOrdchapterNo());
            sb.append(" : ");
            sb.append(cv.getViolatedEnfElement().getCodeElement().getOrdSecNum());
            sb.append(" : ");
            sb.append(cv.getViolatedEnfElement().getCodeElement().getOrdSubSecNum());
            sb.append(" - ");
            sb.append(cv.getViolatedEnfElement().getCodeElement().getOrdSubSecTitle());
            sb.append("<br><br>");
            sb.append("Ordinance Technical Text:");
            sb.append(cv.getViolatedEnfElement().getCodeElement().getOrdTechnicalText());
            sb.append("<br>");
            sb.append("**************************");
        } //close for
        
        System.out.println("CaseCoordinator.genreateNewNOV | notice text: " + sb.toString());
        newNotice.setNoticeText(sb.toString());
        
        return newNotice;
    }
    
    public void deployNoticeOfViolation(CECase c, NoticeOfViolation nov) 
            throws CaseLifecyleException, IntegrationException, EventException{
        CodeViolationIntegrator cvi = getCodeViolationIntegrator();
        nov.setRequestToSend(true);
        // flag violation letter as ready to send
        // this will trigger a sending process that hasn't been implemented as
        // of 2 March 2018
        cvi.updateViolationLetter(nov);
        advanceToNextCasePhase(c);
        
    }
    
    public void markNoticeOfViolationAsSent(CECase ceCase, NoticeOfViolation nov) throws CaseLifecyleException, EventException{
        CodeViolationIntegrator cvi = getCodeViolationIntegrator();
        nov.setLetterSentDate(LocalDateTime.now());
        nov.setLetterSentDatePretty(getPrettyDate(LocalDateTime.now()));
        try {
            cvi.updateViolationLetter(nov);
            advanceToNextCasePhase(ceCase);
            
            
        } catch (IntegrationException ex) {
            throw new CaseLifecyleException("Unable to mark letter as sent "
                    + "due to a database communication foul-up");
        }
    }
    
    public void deleteNoticeOfViolation(NoticeOfViolation nov) throws CaseLifecyleException{
        CodeViolationIntegrator cvi = getCodeViolationIntegrator();

        //cannot delete a letter that was already sent
        if(nov != null && nov.getLetterSentDate() != null){
            throw new CaseLifecyleException("Cannot delete a letter that has been sent");
        } else {
            try {
                cvi.deleteViolationLetter(nov);
            } catch (IntegrationException ex) {
                 getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Unable to delete notice of violation due to a database error", ""));
            }
        }
    }
    
   public Citation generateNewCitation(LinkedList<CodeViolation> violationList){
       Citation newCitation = new Citation();
       newCitation.setViolationList(violationList);
       return newCitation;
   }
   
   public Citation generateNewCitation(ArrayList<CodeViolation> violationList){
       Citation newCitation = new Citation();
       LinkedList<CodeViolation> ll = new LinkedList<>();
       ListIterator<CodeViolation> li = violationList.listIterator();
       CodeViolation cv;
       
       while(li.hasNext()){
           
           cv = li.next();
           System.out.println("CaseCoordinator.generateNewCitation | linked list item: " 
                   + cv.getDescription());
           ll.add(cv);
           
       }
       newCitation.setViolationList(ll);
       return newCitation;
   }
   
   public void deleteCitation(Citation c) throws IntegrationException{
       CitationIntegrator citint = getCitationIntegrator();
       citint.deleteCitation(c);
              
   }
   
   public void issueCitation(Citation c) throws IntegrationException{
       CitationIntegrator citint = getCitationIntegrator();
       citint.insertCitation(c);
       
   }
   
   public void updateCitation(Citation c) throws IntegrationException{
       CitationIntegrator citint = getCitationIntegrator();
       citint.updateCitation(c);
       
   }
}
