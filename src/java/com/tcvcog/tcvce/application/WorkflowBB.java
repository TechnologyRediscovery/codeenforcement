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
package com.tcvcog.tcvce.application;

import com.tcvcog.tcvce.entities.CEActionRequest;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.Person;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class WorkflowBB extends BackingBeanUtils implements Serializable{

    
    private LinkedList<CEActionRequest> requestList;
    private LinkedList<CECase> CaseList;
    private LinkedList<Event> recentEventList;
    private LinkedList<Person> muniPeopleList;
    
    
    
    /**
     * Creates a new instance of WorkflowBB
     */
    public WorkflowBB() {
    }
    
}
