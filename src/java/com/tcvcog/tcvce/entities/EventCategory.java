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

import java.util.Objects;

/**
 *
 * @author sylvia
 */
public class EventCategory {
    
    private EventType eventType;
    private int categoryID;
    private String eventCategoryTitle;
    private String eventCategoryDesc;
    

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
     * @return the eventCategoryTitle
     */
    public String getEventCategoryTitle() {
        return eventCategoryTitle;
    }

    /**
     * @return the eventCategoryDesc
     */
    public String getEventCategoryDesc() {
        return eventCategoryDesc;
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
     * @param eventCategoryTitle the eventCategoryTitle to set
     */
    public void setEventCategoryTitle(String eventCategoryTitle) {
        this.eventCategoryTitle = eventCategoryTitle;
    }

    /**
     * @param eventCategoryDesc the eventCategoryDesc to set
     */
    public void setEventCategoryDesc(String eventCategoryDesc) {
        this.eventCategoryDesc = eventCategoryDesc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.eventType);
        hash = 59 * hash + this.categoryID;
        hash = 59 * hash + Objects.hashCode(this.eventCategoryTitle);
        hash = 59 * hash + Objects.hashCode(this.eventCategoryDesc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventCategory other = (EventCategory) obj;
        if (this.categoryID != other.categoryID) {
            return false;
        }
        if (!Objects.equals(this.eventCategoryTitle, other.eventCategoryTitle)) {
            return false;
        }
        if (!Objects.equals(this.eventCategoryDesc, other.eventCategoryDesc)) {
            return false;
        }
        if (this.eventType != other.eventType) {
            return false;
        }
        return true;
    }
    
    
    
}
