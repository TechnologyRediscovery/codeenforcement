/*
 * Copyright (C) 2017 cedba
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

import java.util.Date;
import java.io.Serializable;
import com.tcvcog.tcvce.entities.CEActionRequest;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import java.time.ZoneId;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.entities.PersonType;
import com.tcvcog.tcvce.integration.PersonIntegrator;
import javax.faces.application.FacesMessage;
/**
 *
 * @author cedba
 */


public class ActionRequestBean extends BackingBeanUtils implements Serializable{
    
    // for request lookup
    private int lookUpControlCode; 
    
    private CEActionRequest submittedRequest;
    private CEActionRequest currentRequest;
    private Person currentPerson;

    // UI Component bindings
    
    private int violationTypeID;
    private String violationTypeName;
    
    private int muniID;
    private String muniName;
    
    private String form_addressOfConcern;
    
    private boolean form_notAtAddress;
    
    private String form_requestDescription;
    private boolean form_isUrgent;
    private Date form_dateOfRecord;
    
    private PersonType submittingPersonType;
    
    // located address
    private String form_requestorFName;
    private String form_requestorLName;
    private String form_requestor_phoneCell;
    private String form_requestor_phoneHome;
    private String form_requestor_phoneWork;
    private String form_requestor_email;
    private String form_requestor_addressStreet;
    private String form_requestor_addressCity;
    private String form_requestor_addressZip;
    private String form_requestor_notes;
    private boolean form_anonymous;

    /**
     * Creates a new instance of ActionRequestBean
     */
    public ActionRequestBean(){
        // set date of record to current date
        form_dateOfRecord = new Date();
    }
    
    
    /**
     * This action method is called when the request code enforcement
     * action request is submitted online (submit button in submitCERequest
     * @return 
     */
    public String submitActionRequest(){
                 getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "ActionRequestBean.submitActionRequest: request submitted", ""));
        
        
        int controlCode;
        
        System.out.println("ActionRequestBean.submitActionRequest: start method");
        currentRequest = new CEActionRequest();
        currentRequest.setIssueType_issueTypeID(violationTypeID);
        //currentRequest.setMuni_muniCode(muniID);
        currentRequest.setAddressOfConcern(form_addressOfConcern);
        currentRequest.setNotAtAddress(form_notAtAddress);
        currentRequest.setRequestDescription(form_requestDescription);
        currentRequest.setIsUrgent(form_isUrgent);
        currentRequest.setDateOfRecord(form_dateOfRecord
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        // note that the time stamp is applied by the integration layer
        // with a simple call to the backing bean getTimeStamp method
        
        // should't be making an integration layer object in this actionMethod
        // TODO Fix this structure!
        CEActionRequestIntegrator integrator = new CEActionRequestIntegrator();
        // for testing
        //System.out.println("ActionRequestBean.submitActionRequest: personType: " + submittingPersonType.getLabel());
        controlCode = integrator.submitCEActionRequest(currentRequest);
        
        System.out.println("ActionRequestBean.submitActionRequest - submitting request");
        submittedRequest = integrator.getActionRequest(controlCode);
        System.out.println("ActionRequestBean.submitActionRequest: submitted request = " + submittedRequest.getRequestPublicCC());
        System.out.println("ActionRequestBean.submitActionRequest - COMPLETE request submission");
        
        //getVisit().setActionRequest(aRequest);
        
        return "success";
    }
    
    public String refreshPage(){
        return "submitCERequest";
    }
    
    public void storeActionRequestorPerson(){
        
        currentPerson = new Person();
        
        currentPerson.setPersonType(submittingPersonType);
        currentPerson.setfName(form_requestorFName);
        currentPerson.setlName(form_requestorLName);
        currentPerson.setPhoneCell(form_requestor_phoneCell);
        currentPerson.setPhoneHome(form_requestor_phoneHome);
        currentPerson.setPhoneWork(form_requestor_phoneWork);
        currentPerson.setEmail(form_requestor_email);
        currentPerson.setAddress_street(form_requestor_addressStreet);
        currentPerson.setAddress_city(form_requestor_addressCity);
        currentPerson.setAddress_zip(form_requestor_addressZip);
        currentPerson.setNotes(form_requestor_notes);
        // the insertion of this person will be timestamped
        // by the integrator class
        
        // Commit the sin of creating an integrator here and using it
        PersonIntegrator personIntegrator = new PersonIntegrator();
        int newPersonId = personIntegrator.storeNewPerson(currentPerson);
        currentPerson.setPersonid(newPersonId);
        
    } // close storePerson 

    /**
     * @return the form_addressOfConcern
     */
    public String getForm_addressOfConcern() {
        return form_addressOfConcern;
    }

    /**
     * @param form_addressOfConcern the form_addressOfConcern to set
     */
    public void setForm_addressOfConcern(String form_addressOfConcern) {
        this.form_addressOfConcern = form_addressOfConcern;
    }

    /**
     * @return the form_notAtAddress
     */
    public boolean isForm_notAtAddress() {
        return form_notAtAddress;
    }

    /**
     * @param form_notAtAddress the form_notAtAddress to set
     */
    public void setForm_notAtAddress(boolean form_notAtAddress) {
        this.form_notAtAddress = form_notAtAddress;
    }

    /**
     * @return the form_requestDescription
     */
    public String getForm_requestDescription() {
        return form_requestDescription;
    }

    /**
     * @param form_requestDescription the form_requestDescription to set
     */
    public void setForm_requestDescription(String form_requestDescription) {
        this.form_requestDescription = form_requestDescription;
    }

    /**
     * @return the form_isUrgent
     */
    public boolean isForm_isUrgent() {
        return form_isUrgent;
    }

    /**
     * @param form_isUrgent the form_isUrgent to set
     */
    public void setForm_isUrgent(boolean form_isUrgent) {
        this.form_isUrgent = form_isUrgent;
    }

    /**
     * @return the form_dateOfRecord
     */
    public Date getForm_dateOfRecord() {
        return form_dateOfRecord;
    }

    /**
     * @param form_dateOfRecord the form_dateOfRecord to set
     */
    public void setForm_dateOfRecord(Date form_dateOfRecord) {
        this.form_dateOfRecord = form_dateOfRecord;
    }

    /**
     * @return the form_requestorFName
     */
    public String getForm_requestorFName() {
        return form_requestorFName;
    }

    /**
     * @param form_requestorFName the form_requestorFName to set
     */
    public void setForm_requestorFName(String form_requestorFName) {
        this.form_requestorFName = form_requestorFName;
    }

    /**
     * @return the form_requestorLName
     */
    public String getForm_requestorLName() {
        return form_requestorLName;
    }

    /**
     * @param form_requestorLName the form_requestorLName to set
     */
    public void setForm_requestorLName(String form_requestorLName) {
        this.form_requestorLName = form_requestorLName;
    }

    /**
     * @return the form_requestor_phoneCell
     */
    public String getForm_requestor_phoneCell() {
        return form_requestor_phoneCell;
    }

    /**
     * @param form_requestor_phoneCell the form_requestor_phoneCell to set
     */
    public void setForm_requestor_phoneCell(String form_requestor_phoneCell) {
        this.form_requestor_phoneCell = form_requestor_phoneCell;
    }

    /**
     * @return the form_requestor_email
     */
    public String getForm_requestor_email() {
        return form_requestor_email;
    }

    /**
     * @param form_requestor_email the form_requestor_email to set
     */
    public void setForm_requestor_email(String form_requestor_email) {
        this.form_requestor_email = form_requestor_email;
    }

    /**
     * @return the form_requestor_addressStreet
     */
    public String getForm_requestor_addressStreet() {
        return form_requestor_addressStreet;
    }

    /**
     * @param form_requestor_addressStreet the form_requestor_addressStreet to set
     */
    public void setForm_requestor_addressStreet(String form_requestor_addressStreet) {
        this.form_requestor_addressStreet = form_requestor_addressStreet;
    }

    /**
     * @return the form_requestor_addressCity
     */
    public String getForm_requestor_addressCity() {
        return form_requestor_addressCity;
    }

    /**
     * @param form_requestor_addressCity the form_requestor_addressCity to set
     */
    public void setForm_requestor_addressCity(String form_requestor_addressCity) {
        this.form_requestor_addressCity = form_requestor_addressCity;
    }

    /**
     * @return the form_requestor_addressZip
     */
    public String getForm_requestor_addressZip() {
        return form_requestor_addressZip;
    }

    /**
     * @param form_requestor_addressZip the form_requestor_addressZip to set
     */
    public void setForm_requestor_addressZip(String form_requestor_addressZip) {
        this.form_requestor_addressZip = form_requestor_addressZip;
    }

    /**
     * @return the form_anonymous
     */
    public boolean isForm_anonymous() {
        return form_anonymous;
    }

    /**
     * @param form_anonymous the form_anonymous to set
     */
    public void setForm_anonymous(boolean form_anonymous) {
        this.form_anonymous = form_anonymous;
    }

   

    /**
     * @return the violationTypeID
     */
    public int getViolationTypeID() {
        return violationTypeID;
    }

    /**
     * @param violationTypeID the violationTypeID to set
     */
    public void setViolationTypeID(int violationTypeID) {
        this.violationTypeID = violationTypeID;
    }

    /**
     * @return the violationTypeName
     */
    public String getViolationTypeName() {
        return violationTypeName;
    }

    /**
     * @param violationTypeName the violationTypeName to set
     */
    public void setViolationTypeName(String violationTypeName) {
        this.violationTypeName = violationTypeName;
    }

    /**
     * @return the muniID
     */
    public int getMuniID() {
        return muniID;
    }

    /**
     * @param muniID the muniID to set
     */
    public void setMuniID(int muniID) {
        this.muniID = muniID;
    }

    /**
     * @return the muniName
     */
    public String getMuniName() {
        return muniName;
    }

    /**
     * @param muniName the muniName to set
     */
    public void setMuniName(String muniName) {
        this.muniName = muniName;
    }

    /**
     * @return the submittedRequest
     */
    public CEActionRequest getSubmittedRequest() {
        return submittedRequest;
    }

    /**
     * @param submittedRequest the submittedRequest to set
     */
    public void setSubmittedRequest(CEActionRequest submittedRequest) {
        this.submittedRequest = submittedRequest;
    }

    /**
     * @return the currentRequest
     */
    public CEActionRequest getCurrentRequest() {
        return currentRequest;
    }

    /**
     * @param currentRequest the currentRequest to set
     */
    public void setCurrentRequest(CEActionRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    /**
     * @return the currentPerson
     */
    public Person getCurrentPerson() {
        return currentPerson;
    }

    /**
     * @param currentPerson the currentPerson to set
     */
    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    /**
     * @return the submittingPersonType
     */
    public PersonType getSubmittingPersonType() {
        return submittingPersonType;
    }

    /**
     * @param submittingPersonType the submittingPersonType to set
     */
    public void setSubmittingPersonType(PersonType submittingPersonType) {
        this.submittingPersonType = submittingPersonType;
    }

    /**
     * @return the form_requestor_phoneHome
     */
    public String getForm_requestor_phoneHome() {
        return form_requestor_phoneHome;
    }

    /**
     * @param form_requestor_phoneHome the form_requestor_phoneHome to set
     */
    public void setForm_requestor_phoneHome(String form_requestor_phoneHome) {
        this.form_requestor_phoneHome = form_requestor_phoneHome;
    }

    /**
     * @return the form_requestor_phoneWork
     */
    public String getForm_requestor_phoneWork() {
        return form_requestor_phoneWork;
    }

    /**
     * @param form_requestor_phoneWork the form_requestor_phoneWork to set
     */
    public void setForm_requestor_phoneWork(String form_requestor_phoneWork) {
        this.form_requestor_phoneWork = form_requestor_phoneWork;
    }

    /**
     * @return the form_requestor_notes
     */
    public String getForm_requestor_notes() {
        return form_requestor_notes;
    }

    /**
     * @param form_requestor_notes the form_requestor_notes to set
     */
    public void setForm_requestor_notes(String form_requestor_notes) {
        this.form_requestor_notes = form_requestor_notes;
    }

    /**
     * @return the submittingPersonTypes
     */
    public PersonType[] getSubmittingPersonTypes() {
        return PersonType.values();
    }
}