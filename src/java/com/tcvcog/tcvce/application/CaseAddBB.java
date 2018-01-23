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

import com.tcvcog.tcvce.entities.Property;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author sylvia
 */
public class CaseAddBB extends BackingBeanUtils implements Serializable{

    private Property caseProperty;
    private int formPropertyUnitID;
    private String formCaseName;
    private LocalDateTime formOriginationDate;
    private String formCaseNotes;
    private boolean isUnitAssociated;
    
    
    /**
     * Creates a new instance of CaseAddBB
     */
    public CaseAddBB() {
    }
    
    public String addNewCase(){
        
        return "";
    }

    /**
     * @return the formPropertyUnitID
     */
    public int getFormPropertyUnitID() {
        return formPropertyUnitID;
    }

    /**
     * @return the formCaseName
     */
    public String getFormCaseName() {
        return formCaseName;
    }

    /**
     * @return the formOriginationDate
     */
    public LocalDateTime getFormOriginationDate() {
        return formOriginationDate;
    }

    /**
     * @return the formCaseNotes
     */
    public String getFormCaseNotes() {
        return formCaseNotes;
    }

    /**
     * @param formPropertyUnitID the formPropertyUnitID to set
     */
    public void setFormPropertyUnitID(int formPropertyUnitID) {
        this.formPropertyUnitID = formPropertyUnitID;
    }

    /**
     * @param formCaseName the formCaseName to set
     */
    public void setFormCaseName(String formCaseName) {
        this.formCaseName = formCaseName;
    }

    /**
     * @param formOriginationDate the formOriginationDate to set
     */
    public void setFormOriginationDate(LocalDateTime formOriginationDate) {
        this.formOriginationDate = formOriginationDate;
    }

    /**
     * @param formCaseNotes the formCaseNotes to set
     */
    public void setFormCaseNotes(String formCaseNotes) {
        this.formCaseNotes = formCaseNotes;
    }

    /**
     * @return the caseProperty
     */
    public Property getCaseProperty() {
        SessionManager sm = getSessionManager();
        caseProperty = sm.getVisit().getActiveProp();
        return caseProperty;
    }

    /**
     * @param caseProperty the caseProperty to set
     */
    public void setCaseProperty(Property caseProperty) {
        this.caseProperty = caseProperty;
    }

    /**
     * @return the isUnitAssociated
     */
    public boolean isIsUnitAssociated() {
        return isUnitAssociated;
    }

    /**
     * @param isUnitAssociated the isUnitAssociated to set
     */
    public void setIsUnitAssociated(boolean isUnitAssociated) {
        this.isUnitAssociated = isUnitAssociated;
    }
    
}
