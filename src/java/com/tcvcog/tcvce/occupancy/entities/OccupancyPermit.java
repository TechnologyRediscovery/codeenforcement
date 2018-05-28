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
package com.tcvcog.tcvce.occupancy.entities;

import com.tcvcog.tcvce.entities.CodeSource;
import java.time.LocalDateTime;

/**
 *
 * @author sylvia
 */
public class OccupancyPermit {
    
    private int permitID;
    private String referenceNo;
    private OccupancyInspection inspection;
    private LocalDateTime dateIssued;
    private LocalDateTime dateExpires;
    private CodeSource issuingCodeSource;
    private String specialConditions;
    private String notes;

    /**
     * @return the permitID
     */
    public int getPermitID() {
        return permitID;
    }

    /**
     * @return the referenceNo
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * @return the inspection
     */
    public OccupancyInspection getInspection() {
        return inspection;
    }

    /**
     * @return the dateIssued
     */
    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    /**
     * @return the dateExpires
     */
    public LocalDateTime getDateExpires() {
        return dateExpires;
    }

    /**
     * @return the issuingCodeSource
     */
    public CodeSource getIssuingCodeSource() {
        return issuingCodeSource;
    }

    /**
     * @return the specialConditions
     */
    public String getSpecialConditions() {
        return specialConditions;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param permitID the permitID to set
     */
    public void setPermitID(int permitID) {
        this.permitID = permitID;
    }

    /**
     * @param referenceNo the referenceNo to set
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /**
     * @param inspection the inspection to set
     */
    public void setInspection(OccupancyInspection inspection) {
        this.inspection = inspection;
    }

    /**
     * @param dateIssued the dateIssued to set
     */
    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    /**
     * @param dateExpires the dateExpires to set
     */
    public void setDateExpires(LocalDateTime dateExpires) {
        this.dateExpires = dateExpires;
    }

    /**
     * @param issuingCodeSource the issuingCodeSource to set
     */
    public void setIssuingCodeSource(CodeSource issuingCodeSource) {
        this.issuingCodeSource = issuingCodeSource;
    }

    /**
     * @param specialConditions the specialConditions to set
     */
    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    
}
