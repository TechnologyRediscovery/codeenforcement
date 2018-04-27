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
package com.tcvcog.tcvce.entities;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class Citation {
    private int citationID;
    private String citationNo;
    private int origin_courtentity_entityID;
    private CECase ceCase;
    private User userOwner;
    private LocalDateTime dateOfRecord;
    private LocalDateTime timeStamp;
    private boolean isActive;
    private String notes;
    private LinkedList<CodeViolation> violationList;

    /**
     * @return the citationID
     */
    public int getCitationID() {
        return citationID;
    }

    /**
     * @return the citationNo
     */
    public String getCitationNo() {
        return citationNo;
    }

    /**
     * @return the origin_courtentity_entityID
     */
    public int getOrigin_courtentity_entityID() {
        return origin_courtentity_entityID;
    }

    /**
     * @return the ceCase
     */
    public CECase getCeCase() {
        return ceCase;
    }

    /**
     * @return the userOwner
     */
    public User getUserOwner() {
        return userOwner;
    }

    /**
     * @return the dateOfRecord
     */
    public LocalDateTime getDateOfRecord() {
        return dateOfRecord;
    }

    /**
     * @return the timeStamp
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param citationID the citationID to set
     */
    public void setCitationID(int citationID) {
        this.citationID = citationID;
    }

    /**
     * @param citationNo the citationNo to set
     */
    public void setCitationNo(String citationNo) {
        this.citationNo = citationNo;
    }

    /**
     * @param origin_courtentity_entityID the origin_courtentity_entityID to set
     */
    public void setOrigin_courtentity_entityID(int origin_courtentity_entityID) {
        this.origin_courtentity_entityID = origin_courtentity_entityID;
    }

    /**
     * @param ceCase the ceCase to set
     */
    public void setCeCase(CECase ceCase) {
        this.ceCase = ceCase;
    }

    /**
     * @param userOwner the userOwner to set
     */
    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    /**
     * @param dateOfRecord the dateOfRecord to set
     */
    public void setDateOfRecord(LocalDateTime dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the violationList
     */
    public LinkedList<CodeViolation> getViolationList() {
        return violationList;
    }

    /**
     * @param violationList the violationList to set
     */
    public void setViolationList(LinkedList<CodeViolation> violationList) {
        this.violationList = violationList;
    }
    
    
    
    
}


/*


CREATE TABLE citation
(
    citationID                      INTEGER DEFAULT nextval('citation_citationID_seq') NOT NULL, 
    citationNo                      text, --collaboratively created with munis
    origin_courtentity_entityID     INTEGER NOT NULL, --fk
    cecase_caseID                   INTEGER NOT NULL, --fk
    login_userID                    INTEGER NOT NULL, --fk
    dateOfRecord                    TIMESTAMP WITH TIME ZONE NOT NULL,
    transTimeStamp                  TIMESTAMP WITH TIME ZONE NOT NULL,
    isActive                        boolean DEFAULT TRUE,
    notes                           text
    -- this is just a skeleton for a citation: more fields likely as system develops
) ;
*/
