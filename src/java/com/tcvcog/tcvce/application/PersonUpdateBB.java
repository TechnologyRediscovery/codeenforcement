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

import com.tcvcog.tcvce.entities.Municipality;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.entities.PersonType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author sylvia
 */
public class PersonUpdateBB extends BackingBeanUtils implements Serializable{

    private Person person;
    
    private int personid;
    private PersonType formPpersonType;
    private Municipality formMuni;
    private String formFirstName;
    private String formLastName;
    private String formJobTitle;
    private String formPhoneCell;
    private String formPhoneHome;
    private String formPhoneWork;
    private String formEmail;
    private String formAddress_street;
    private String formAddress_city;
    private String formAddress_state;
    private String formAddress_zip;
    private String formNotes;
    private LocalDateTime lastUpdated;
    private LocalDateTime formExpieryDate;
    private boolean formIsActive;
    private boolean formIsUnder18;
    
    
    /**
     * Creates a new instance of PersonUpdateBB
     */
    public PersonUpdateBB() {
    }

    /**
     * @return the personid
     */
    public int getPersonid() {
        return personid;
    }

    /**
     * @return the formPpersonType
     */
    public PersonType getFormPpersonType() {
        return formPpersonType;
    }

    /**
     * @return the formMuni
     */
    public Municipality getFormMuni() {
        return formMuni;
    }

    /**
     * @return the formFirstName
     */
    public String getFormFirstName() {
        return formFirstName;
    }

    /**
     * @return the formLastName
     */
    public String getFormLastName() {
        return formLastName;
    }

    /**
     * @return the formJobTitle
     */
    public String getFormJobTitle() {
        return formJobTitle;
    }

    /**
     * @return the formPhoneCell
     */
    public String getFormPhoneCell() {
        return formPhoneCell;
    }

    /**
     * @return the formPhoneHome
     */
    public String getFormPhoneHome() {
        return formPhoneHome;
    }

    /**
     * @return the formPhoneWork
     */
    public String getFormPhoneWork() {
        return formPhoneWork;
    }

    /**
     * @return the formEmail
     */
    public String getFormEmail() {
        return formEmail;
    }

    /**
     * @return the formAddress_street
     */
    public String getFormAddress_street() {
        return formAddress_street;
    }

    /**
     * @return the formAddress_city
     */
    public String getFormAddress_city() {
        return formAddress_city;
    }

    /**
     * @return the formAddress_state
     */
    public String getFormAddress_state() {
        return formAddress_state;
    }

    /**
     * @return the formAddress_zip
     */
    public String getFormAddress_zip() {
        return formAddress_zip;
    }

    /**
     * @return the formNotes
     */
    public String getFormNotes() {
        return formNotes;
    }

    /**
     * @return the lastUpdated
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @return the formExpieryDate
     */
    public LocalDateTime getFormExpieryDate() {
        return formExpieryDate;
    }

    /**
     * @return the formIsActive
     */
    public boolean isFormIsActive() {
        return formIsActive;
    }

    /**
     * @return the formIsUnder18
     */
    public boolean isFormIsUnder18() {
        return formIsUnder18;
    }

    /**
     * @param personid the personid to set
     */
    public void setPersonid(int personid) {
        this.personid = personid;
    }

    /**
     * @param formPpersonType the formPpersonType to set
     */
    public void setFormPpersonType(PersonType formPpersonType) {
        this.formPpersonType = formPpersonType;
    }

    /**
     * @param formMuni the formMuni to set
     */
    public void setFormMuni(Municipality formMuni) {
        this.formMuni = formMuni;
    }

    /**
     * @param formFirstName the formFirstName to set
     */
    public void setFormFirstName(String formFirstName) {
        this.formFirstName = formFirstName;
    }

    /**
     * @param formLastName the formLastName to set
     */
    public void setFormLastName(String formLastName) {
        this.formLastName = formLastName;
    }

    /**
     * @param formJobTitle the formJobTitle to set
     */
    public void setFormJobTitle(String formJobTitle) {
        this.formJobTitle = formJobTitle;
    }

    /**
     * @param formPhoneCell the formPhoneCell to set
     */
    public void setFormPhoneCell(String formPhoneCell) {
        this.formPhoneCell = formPhoneCell;
    }

    /**
     * @param formPhoneHome the formPhoneHome to set
     */
    public void setFormPhoneHome(String formPhoneHome) {
        this.formPhoneHome = formPhoneHome;
    }

    /**
     * @param formPhoneWork the formPhoneWork to set
     */
    public void setFormPhoneWork(String formPhoneWork) {
        this.formPhoneWork = formPhoneWork;
    }

    /**
     * @param formEmail the formEmail to set
     */
    public void setFormEmail(String formEmail) {
        this.formEmail = formEmail;
    }

    /**
     * @param formAddress_street the formAddress_street to set
     */
    public void setFormAddress_street(String formAddress_street) {
        this.formAddress_street = formAddress_street;
    }

    /**
     * @param formAddress_city the formAddress_city to set
     */
    public void setFormAddress_city(String formAddress_city) {
        this.formAddress_city = formAddress_city;
    }

    /**
     * @param formAddress_state the formAddress_state to set
     */
    public void setFormAddress_state(String formAddress_state) {
        this.formAddress_state = formAddress_state;
    }

    /**
     * @param formAddress_zip the formAddress_zip to set
     */
    public void setFormAddress_zip(String formAddress_zip) {
        this.formAddress_zip = formAddress_zip;
    }

    /**
     * @param formNotes the formNotes to set
     */
    public void setFormNotes(String formNotes) {
        this.formNotes = formNotes;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @param formExpieryDate the formExpieryDate to set
     */
    public void setFormExpieryDate(LocalDateTime formExpieryDate) {
        this.formExpieryDate = formExpieryDate;
    }

    /**
     * @param formIsActive the formIsActive to set
     */
    public void setFormIsActive(boolean formIsActive) {
        this.formIsActive = formIsActive;
    }

    /**
     * @param formIsUnder18 the formIsUnder18 to set
     */
    public void setFormIsUnder18(boolean formIsUnder18) {
        this.formIsUnder18 = formIsUnder18;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        SessionManager sm = getSessionManager();
        person = sm.getVisit().getActivePerson();
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
}
