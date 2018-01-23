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

import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.Person;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class CaseIntegrator {

    /**
     * Creates a new instance of CaseIntegrator
     */
    public CaseIntegrator() {
    }
    
    public LinkedList getCECases(int propertyID){
        LinkedList caseList = null;
        return caseList;
    }
    
    public LinkedList getCECasesByMuni(int muniCode){
        LinkedList caseList = null;
        return caseList;
    }
    
    public CECase getCECase(int ceCaseID){
        CECase ceCase = null;
        
        return ceCase;
    }
    

    public int insertNewCECase(CECase cecase){
        
        return 0;
        
    }
    
    /**
     * Updates the values in the CECase in the DB but does NOT
     * edit the data in connected tables, namely CodeViolation, Event, and Person
     * Use calls to other add methods in this class for adding additional
     * violatoins, events, and people to a CE case.
     * 
     * @param cecase the case to updated, with updated member variables
     */
    public void updateCECase(CECase cecase){
        
    }
    
    public void deleteCECase(CECase cecase){
        
        
    }
    
    public void addCodeViolationToCECase(CECase cecase, CodeViolation violation){
        
    }
    
    public void addEventToCECase(CECase cecase, Event event){
        
        
    }
    
    public void addPersonToCECase(CECase cecase, Person p){
        
        
    }
    
    public void changeCECasePhase(CECase ceCase, CasePhase oldPhase, CasePhase newPhase){
        
        
    }
    
    
    
}
