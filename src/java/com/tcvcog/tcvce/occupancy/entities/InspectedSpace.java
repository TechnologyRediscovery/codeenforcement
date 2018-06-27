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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Subclass of Space: stores inspection-specific data about each space element
 * that is part of the super class. When a space is inspected, the ArrayList of CodeElements
 * in the Space are wrapped in an inspection blanket and added to the 
 * inspectedElementList which captures the compliance status, comp date, and notes.
 * 
 * @author Eric C. Darsow, Technology Rediscovery LLC 
 */
public class InspectedSpace extends Space implements Serializable{
    
    private ArrayList<InspectedElement> inspectedElementList;
    private LocationDescriptor location;
    
    public InspectedSpace(){
        inspectedElementList = new ArrayList<>();
    }

    /**
     * @return the inspectedElementList
     */
    public ArrayList<InspectedElement> getInspectedElementList() {
        return inspectedElementList;
    }

    /**
     * @param inspectedElementList the inspectedElementList to set
     */
    public void setInspectedElementList(ArrayList<InspectedElement> inspectedElementList) {
        this.inspectedElementList = inspectedElementList;
    }

    /**
     * @return the location
     */
    public LocationDescriptor getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(LocationDescriptor location) {
        this.location = location;
    }
    
}
