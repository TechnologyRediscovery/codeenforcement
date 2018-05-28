/*
 * Copyright (C) 2018 Emily
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
import java.time.LocalDateTime;

import java.util.LinkedList;

/**
 *
 * @author Emily
 */
public class Inspection {
    private LinkedList<RoomTypeInspectableCodeElement> iceFails;
    private int inspectionId;
    private LocalDateTime inspecationDate;
    private String inspectionContact;
    private String inspectionCEofficer;

    /**
     * @return the iceFails
     */
    public LinkedList<RoomTypeInspectableCodeElement> getIceFails() {
        return iceFails;
    }

    /**
     * @param iceFails the iceFails to set
     */
    public void setIceFails(LinkedList<RoomTypeInspectableCodeElement> iceFails) {
        this.iceFails = iceFails;
    }

    /**
     * @return the inspectionId
     */
    public int getInspectionId() {
        return inspectionId;
    }

    /**
     * @param inspectionId the inspectionId to set
     */
    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    /**
     * @return the inspecationDate
     */
    public LocalDateTime getInspecationDate() {
        return inspecationDate;
    }

    /**
     * @param inspecationDate the inspecationDate to set
     */
    public void setInspecationDate(LocalDateTime inspecationDate) {
        this.inspecationDate = inspecationDate;
    }

    /**
     * @return the inspectionContact
     */
    public String getInspectionContact() {
        return inspectionContact;
    }

    /**
     * @param inspectionContact the inspectionContact to set
     */
    public void setInspectionContact(String inspectionContact) {
        this.inspectionContact = inspectionContact;
    }

    /**
     * @return the inspectionCEofficer
     */
    public String getInspectionCEofficer() {
        return inspectionCEofficer;
    }

    /**
     * @param inspectionCEofficer the inspectionCEofficer to set
     */
    public void setInspectionCEofficer(String inspectionCEofficer) {
        this.inspectionCEofficer = inspectionCEofficer;
    }
    
    
}
