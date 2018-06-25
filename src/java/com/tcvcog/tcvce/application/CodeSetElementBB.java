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


import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CodeSet;
import com.tcvcog.tcvce.entities.CodeElementEnforcable;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;

/** 
 *
 * @author Eric C. Darsow
 */

public class CodeSetElementBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeSetElementBB
     */
    public CodeSetElementBB() {
    }
   
    private CodeSet currentCodeSet;
    
    private CodeElementEnforcable selectedCEE;
    private ArrayList<CodeElementEnforcable> cEEList;

    /**
     * @return the selectedCEE
     */
    public CodeElementEnforcable getSelectedCEE() {
        return selectedCEE;
    }

    /**
     * @param selectedCEE the selectedCEE to set
     */
    public void setSelectedCEE(CodeElementEnforcable selectedCEE) {
        this.selectedCEE = selectedCEE;
    }

    /**
     * @return the cEEList
     */
    public ArrayList<CodeElementEnforcable> getcEEList() {
        
        CodeIntegrator integrator = getCodeIntegrator();
        CodeSet codeSet = getSessionBean().getActiveCodeSet();
        
        if(codeSet != null){
            System.out.println("CodeSetElementBB.getEceList | active code set name: " + codeSet.getCodeSetName());
            int setID = codeSet.getCodeSetID();
            try {
                cEEList =  integrator.getEnforcableCodeElementList(setID);
//                getFacesContext().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
//                                "Loaded Enforcable Code Elements for set named: " +codeSet.getCodeSetName() , ""));
            } catch (IntegrationException ex) {
                System.out.println(ex.toString());
                getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "No Enforcable Code Elements were found with SetID: " + setID, ""));
            }
        } 
        return cEEList;
    }

    /**
     * @param cEEList the cEEList to set
     */
    public void setcEEList(ArrayList<CodeElementEnforcable> cEEList) {
        this.cEEList = cEEList;
    }

    /**
     * @return the currentCodeSet
     */
    public CodeSet getCurrentCodeSet() {
        currentCodeSet = getSessionBean().getActiveCodeSet();
        return currentCodeSet;
    }

    /**
     * @param currentCodeSet the currentCodeSet to set
     */
    public void setCurrentCodeSet(CodeSet currentCodeSet) {
        this.currentCodeSet = currentCodeSet;
    }
    
    
    
}
