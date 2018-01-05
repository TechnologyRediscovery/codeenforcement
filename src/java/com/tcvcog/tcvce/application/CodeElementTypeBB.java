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
import com.tcvcog.tcvce.entities.CodeElementType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sylvia
 */
public class CodeElementTypeBB extends BackingBeanUtils implements Serializable {

    
    private int codeElementTypeID;
    
    private CodeElementType elementTypeToUpdate;
    
    private String codeElementTypeName;
    private String codeElementTypeDescription;
    private HashMap codeElementTypesMap;
    private int selectedType;
    
    private int formCodeElementTypeID;
    private String formCodeElementTypeName;
    private String formCodeElementTypeDescription;
    
    /**
     * Creates a new instance of CodeElementTypeBB
     */
    public CodeElementTypeBB() {
    }
    
    public void editCodeElement(ActionEvent event){
        System.out.println("CodeElementTypeBB.editCodeElement - TypeID: " + selectedType);
        try {
            elementTypeToUpdate = getCodeIntegrator().getCodeElementTypeByID(selectedType);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to retrieve code elment type, sorry.", 
                        "This must be corrected by the System Administrator"));
        }
        
        System.out.println("CodeElementTypeBB.editCodeElement - Retrieved Name: " + elementTypeToUpdate.getName());
        
        formCodeElementTypeID = elementTypeToUpdate.getCdelTypeID();
        formCodeElementTypeName = elementTypeToUpdate.getName();
        formCodeElementTypeDescription = elementTypeToUpdate.getDescription();
    }
    
    public void updateCodeElementType(ActionEvent event){
        System.out.println("CodeElementTypeBB.updateCodeElement");
        
        elementTypeToUpdate.setCdelTypeID(formCodeElementTypeID);
        elementTypeToUpdate.setName(formCodeElementTypeName);
        elementTypeToUpdate.setDescription(formCodeElementTypeDescription);
        
        try {
            System.out.println("ElementTypeToUpdate: " + elementTypeToUpdate.getName());
            getCodeIntegrator().updateCodeElementType(elementTypeToUpdate);
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to update code elment type, sorry.", 
                        "This must be corrected by the System Administrator"));

        }
        
        
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Updated Code Source Type", ""));
        
        
    }
    

    /**
     * @return the codeElementTypeID
     */
    public int getCodeElementTypeID() {
        return codeElementTypeID;
    }

    /**
     * @param codeElementTypeID the codeElementTypeID to set
     */
    public void setCodeElementTypeID(int codeElementTypeID) {
        this.codeElementTypeID = codeElementTypeID;
    }

    /**
     * @return the codeElementTypeName
     */
    public String getCodeElementTypeName() {
        return codeElementTypeName;
    }

    /**
     * @param codeElementTypeName the codeElementTypeName to set
     */
    public void setCodeElementTypeName(String codeElementTypeName) {
        this.codeElementTypeName = codeElementTypeName;
    }

    /**
     * @return the codeElementTypeDescription
     */
    public String getCodeElementTypeDescription() {
        return codeElementTypeDescription;
    }

    /**
     * @param codeElementTypeDescription the codeElementTypeDescription to set
     */
    public void setCodeElementTypeDescription(String codeElementTypeDescription) {
        this.codeElementTypeDescription = codeElementTypeDescription;
    }

    /**
     * @return the codeElementTypesMap
     */
    public HashMap getCodeElementTypesMap() {
        
        try {
            codeElementTypesMap = getCodeIntegrator().getCodeElementTypesMap();
        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
            getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Unable to load code element types", 
                    "Apologies! This error will require administrator attention"));
        }
        return codeElementTypesMap;
    }

    /**
     * @param codeElementTypesMap the codeElementTypesMap to set
     */
    public void setCodeElementTypesMap(HashMap codeElementTypesMap) {
        this.codeElementTypesMap = codeElementTypesMap;
    }

    /**
     * @return the selectedType
     */
    public int getSelectedType() {
        return selectedType;
    }

    /**
     * @param selectedType the selectedType to set
     */
    public void setSelectedType(int selectedType) {
        this.selectedType = selectedType;
    }

    /**
     * @return the formCodeElementTypeName
     */
    public String getFormCodeElementTypeName() {
        return formCodeElementTypeName;
    }

    /**
     * @param formCodeElementTypeName the formCodeElementTypeName to set
     */
    public void setFormCodeElementTypeName(String formCodeElementTypeName) {
        this.formCodeElementTypeName = formCodeElementTypeName;
    }

    /**
     * @return the formCodeElementTypeDescription
     */
    public String getFormCodeElementTypeDescription() {
        return formCodeElementTypeDescription;
    }

    /**
     * @param formCodeElementTypeDescription the formCodeElementTypeDescription to set
     */
    public void setFormCodeElementTypeDescription(String formCodeElementTypeDescription) {
        this.formCodeElementTypeDescription = formCodeElementTypeDescription;
    }

    /**
     * @return the elementTypeToUpdate
     */
    public CodeElementType getElementTypeToUpdate() {
        return elementTypeToUpdate;
    }

    /**
     * @param elementTypeToUpdate the elementTypeToUpdate to set
     */
    public void setElementTypeToUpdate(CodeElementType elementTypeToUpdate) {
        this.elementTypeToUpdate = elementTypeToUpdate;
    }

    /**
     * @return the formCodeElementTypeID
     */
    public int getFormCodeElementTypeID() {
        return formCodeElementTypeID;
    }

    /**
     * @param formCodeElementTypeID the formCodeElementTypeID to set
     */
    public void setFormCodeElementTypeID(int formCodeElementTypeID) {
        this.formCodeElementTypeID = formCodeElementTypeID;
    }
    
}
