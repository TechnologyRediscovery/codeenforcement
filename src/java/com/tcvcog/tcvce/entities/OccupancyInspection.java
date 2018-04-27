/*
 * Copyright (C) 2018 Adam Gutonski 
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

import com.tcvcog.tcvce.occupancy.*;
import java.time.LocalDateTime;

/**
 *
 * @author Adam Gutonski
 */
public class OccupancyInspection {
    
    private int inspectionID;
    private int propertyUnitID;
    private int loginUserID;
    private LocalDateTime firstInspectionDate;
    private boolean firstInspectionPass;
    private LocalDateTime secondInspectionDate;
    private boolean SecondInspectionPass;
    private boolean resolved;
    private boolean totalFeePaid;
    private String occupancyInspectionNotes;

    /**
     * @return the inspectionID
     */
    public int getInspectionID() {
        return inspectionID;
    }

    /**
     * @param inspectionID the inspectionID to set
     */
    public void setInspectionID(int inspectionID) {
        this.inspectionID = inspectionID;
    }

    /**
     * @return the propertyUnitID
     */
    public int getPropertyUnitID() {
        return propertyUnitID;
    }

    /**
     * @param propertyUnitID the propertyUnitID to set
     */
    public void setPropertyUnitID(int propertyUnitID) {
        this.propertyUnitID = propertyUnitID;
    }

    /**
     * @return the loginUserID
     */
    public int getLoginUserID() {
        return loginUserID;
    }

    /**
     * @param loginUserID the loginUserID to set
     */
    public void setLoginUserID(int loginUserID) {
        this.loginUserID = loginUserID;
    }

    /**
     * @return the firstInspectionDate
     */
    public LocalDateTime getFirstInspectionDate() {
        return firstInspectionDate;
    }

    /**
     * @param firstInspectionDate the firstInspectionDate to set
     */
    public void setFirstInspectionDate(LocalDateTime firstInspectionDate) {
        this.firstInspectionDate = firstInspectionDate;
    }

    /**
     * @return the firstInspectionPass
     */
    public boolean isFirstInspectionPass() {
        return firstInspectionPass;
    }

    /**
     * @param firstInspectionPass the firstInspectionPass to set
     */
    public void setFirstInspectionPass(boolean firstInspectionPass) {
        this.firstInspectionPass = firstInspectionPass;
    }

    /**
     * @return the secondInspectionDate
     */
    public LocalDateTime getSecondInspectionDate() {
        return secondInspectionDate;
    }

    /**
     * @param secondInspectionDate the secondInspectionDate to set
     */
    public void setSecondInspectionDate(LocalDateTime secondInspectionDate) {
        this.secondInspectionDate = secondInspectionDate;
    }

    /**
     * @return the SecondInspectionPass
     */
    public boolean isSecondInspectionPass() {
        return SecondInspectionPass;
    }

    /**
     * @param SecondInspectionPass the SecondInspectionPass to set
     */
    public void setSecondInspectionPass(boolean SecondInspectionPass) {
        this.SecondInspectionPass = SecondInspectionPass;
    }

    /**
     * @return the resolved
     */
    public boolean isResolved() {
        return resolved;
    }

    /**
     * @param resolved the resolved to set
     */
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    /**
     * @return the totalFeePaid
     */
    public boolean isTotalFeePaid() {
        return totalFeePaid;
    }

    /**
     * @param totalFeePaid the totalFeePaid to set
     */
    public void setTotalFeePaid(boolean totalFeePaid) {
        this.totalFeePaid = totalFeePaid;
    }

    /**
     * @return the occupancyInspectionNotes
     */
    public String getOccupancyInspectionNotes() {
        return occupancyInspectionNotes;
    }

    /**
     * @param occupancyInspectionNotes the occupancyInspectionNotes to set
     */
    public void setOccupancyInspectionNotes(String occupancyInspectionNotes) {
        this.occupancyInspectionNotes = occupancyInspectionNotes;
    }
    
    
}
