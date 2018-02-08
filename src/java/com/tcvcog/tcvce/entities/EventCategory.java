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
public class EventCategory {
    
    private EventType eventType;
    private int categoryID;
    private String eventTypeTitle;
    private String eventTypeDesc;

    /**
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @return the eventTypeTitle
     */
    public String getEventTypeTitle() {
        return eventTypeTitle;
    }

    /**
     * @return the eventTypeDesc
     */
    public String getEventTypeDesc() {
        return eventTypeDesc;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @param eventTypeTitle the eventTypeTitle to set
     */
    public void setEventTypeTitle(String eventTypeTitle) {
        this.eventTypeTitle = eventTypeTitle;
    }

    /**
     * @param eventTypeDesc the eventTypeDesc to set
     */
    public void setEventTypeDesc(String eventTypeDesc) {
        this.eventTypeDesc = eventTypeDesc;
    }
    
}
