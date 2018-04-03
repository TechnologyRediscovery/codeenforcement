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
package com.tcvcog.tcvce.occupancy;

/**
 *
 * @author Emily
 */
public class RoomTypeInspectableCodeElement {
    private int roomTypeIceId;
    private InspectableCodeElement inspectableCodeElement;
    private RoomType roomType;

    /**
     * @return the roomTypeIceId
     */
    public int getRoomTypeIceId() {
        return roomTypeIceId;
    }

    /**
     * @param roomTypeIceId the roomTypeIceId to set
     */
    public void setRoomTypeIceId(int roomTypeIceId) {
        this.roomTypeIceId = roomTypeIceId;
    }

    /**
     * @return the inspectableCodeElement
     */
    public InspectableCodeElement getInspectableCodeElement() {
        return inspectableCodeElement;
    }

    /**
     * @param inspectableCodeElement the inspectableCodeElement to set
     */
    public void setInspectableCodeElement(InspectableCodeElement inspectableCodeElement) {
        this.inspectableCodeElement = inspectableCodeElement;
    }

    /**
     * @return the roomType
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
   

    
    
   
}
