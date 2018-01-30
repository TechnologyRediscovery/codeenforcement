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
package com.tcvcog.tcvce.coordinators;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.application.SessionManager;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CasePhase;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import java.io.Serializable;

/**
 *
 * @author sylvia
 */
public class CaseCoordinator extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CaseCoordinator
     */
    public CaseCoordinator() {
    
    }
    
    public void createNewCECase(CECase newCase) throws IntegrationException{
        
        CaseIntegrator ci = getCaseIntegrator();
        
        CECase newlyAddedCase = ci.insertNewCECase(newCase);
        SessionManager sm = getSessionManager();
        sm.getVisit().setActiveCase(newlyAddedCase);
        
        
    }
    
    public CasePhase getNextCasePhase(CECase c){
        
        return CasePhase.PrelimInvestigationPending;
    }
    
    public void advanceToNextCasePhase(CECase c){
        
        
        
    }
    
    
}
