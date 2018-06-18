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

import java.util.ArrayList;

/**
 *
 * @author Eric Darsow echocharliedelta@protonmail.com
 */
public class ImplementedChecklist {
    
    private int id;
    private ArrayList<InspectedSpace> inspectedSpaceList;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the inspectedSpaceList
     */
    public ArrayList<InspectedSpace> getInspectedSpaceList() {
        return inspectedSpaceList;
    }

    /**
     * @param inspectedSpaceList the inspectedSpaceList to set
     */
    public void setInspectedSpaceList(ArrayList<InspectedSpace> inspectedSpaceList) {
        this.inspectedSpaceList = inspectedSpaceList;
    }

}
