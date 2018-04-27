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

/**
 *
 * @author sylvia
 */
public class CitationStatus {
    private int citationStatusID;
    private String statusTitle;
    private String description;

    /**
     * @return the citationStatusID
     */
    public int getCitationStatusID() {
        return citationStatusID;
    }

    /**
     * @return the statusTitle
     */
    public String getStatusTitle() {
        return statusTitle;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param citationStatusID the citationStatusID to set
     */
    public void setCitationStatusID(int citationStatusID) {
        this.citationStatusID = citationStatusID;
    }

    /**
     * @param statusTitle the statusTitle to set
     */
    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
