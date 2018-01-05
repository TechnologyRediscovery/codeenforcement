/*
 * Copyright (C) 2017 Turtle Creek Valley
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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.entities.Event;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class EventIntegrator {

    /**
     * Creates a new instance of EventIntegrator
     */
    public EventIntegrator() {
    }
    
    public int insertEvent(Event event){
        
        return 0;
    }
    
    public void updateEvent(Event event){
        
        
    }
    
    public void deleteEvent(Event event){
        
        
    }
    
    public Event getEventByEventID(int eventID){
        Event e = new Event();
        
        return e;
        
    }
    
    public LinkedList getEventsByCaseID(int caseID){
        LinkedList eventList = null;
        
        return eventList;
        
    }
    
}
