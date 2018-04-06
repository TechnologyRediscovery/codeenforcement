/*
 * Copyright (C) 2018 Adam Gutonski 
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
import com.tcvcog.tcvce.integration.OccupancyInspectionIntegrator;
import com.tcvcog.tcvce.occupancy.OccupancyInspection;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.LinkedList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Adam Gutonski
 */
@ManagedBean
@ViewScoped
public class OccupancyInspectionBB extends BackingBeanUtils implements Serializable {
    
    private LinkedList<OccupancyInspection> occupancyInspectionList;
    private int formInspectionID;
    private int formPropertyUnitID;
    private int formLoginUserID;
    private java.util.Date formFirstInspectionDate;
    private boolean formFirstInspectionPass;
    private java.util.Date formSecondInspectionDate;
    private boolean formSecondInspectionPass;
    private boolean formResolved;
    private boolean formTotalFeePaid;
    private String formOccupancyInspectionNotes;
    
    /**
     * Creates a new instance of OccupancyInspectionBB
     */
    public OccupancyInspectionBB() {
    }
    
    public String addOccupancyInspection(){
        OccupancyInspection o = new OccupancyInspection();
        OccupancyInspectionIntegrator oi = new OccupancyInspectionIntegrator();
        
        o.setPropertyUnitID(formPropertyUnitID);
        o.setLoginUserID(formLoginUserID);
        o.setFirstInspectionDate(formFirstInspectionDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        o.setFirstInspectionPass(formFirstInspectionPass);
        o.setSecondInspectionDate(formSecondInspectionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        o.setSecondInspectionPass(formSecondInspectionPass);
        o.setResolved(formResolved);
        o.setTotalFeePaid(formTotalFeePaid);
        o.setOccupancyInspectionNotes(formOccupancyInspectionNotes);
    
    try{
        oi.insertOccupanyInspection(o);
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succcessfully added occupancy inspection to the database!", ""));
    }catch (IntegrationException ex) {
            System.out.println(ex.toString());
               getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to add occupancy inspection to the database, my apologies!", "Check again..."));
            return "";
        }
    return "occupancyInspectionManage";
    }

    /**
     * @return the occupancyInspectionList
     */
    public LinkedList<OccupancyInspection> getOccupancyInspectionList() {
        return occupancyInspectionList;
    }

    /**
     * @param occupancyInspectionList the occupancyInspectionList to set
     */
    public void setOccupancyInspectionList(LinkedList<OccupancyInspection> occupancyInspectionList) {
        this.occupancyInspectionList = occupancyInspectionList;
    }

    /**
     * @return the formInspectionID
     */
    public int getFormInspectionID() {
        return formInspectionID;
    }

    /**
     * @param formInspectionID the formInspectionID to set
     */
    public void setFormInspectionID(int formInspectionID) {
        this.formInspectionID = formInspectionID;
    }

    /**
     * @return the formLoginUserID
     */
    public int getFormLoginUserID() {
        return formLoginUserID;
    }

    /**
     * @param formLoginUserID the formLoginUserID to set
     */
    public void setFormLoginUserID(int formLoginUserID) {
        this.formLoginUserID = formLoginUserID;
    }

    /**
     * @return the formFirstInspectionDate
     */
    public java.util.Date getFormFirstInspectionDate() {
        return formFirstInspectionDate;
    }

    /**
     * @param formFirstInspectionDate the formFirstInspectionDate to set
     */
    public void setFormFirstInspectionDate(java.util.Date formFirstInspectionDate) {
        this.formFirstInspectionDate = formFirstInspectionDate;
    }

    /**
     * @return the formFirstInspectionPass
     */
    public boolean isFormFirstInspectionPass() {
        return formFirstInspectionPass;
    }

    /**
     * @param formFirstInspectionPass the formFirstInspectionPass to set
     */
    public void setFormFirstInspectionPass(boolean formFirstInspectionPass) {
        this.formFirstInspectionPass = formFirstInspectionPass;
    }

    /**
     * @return the formSecondInspectionDate
     */
    public java.util.Date getFormSecondInspectionDate() {
        return formSecondInspectionDate;
    }

    /**
     * @param formSecondInspectionDate the formSecondInspectionDate to set
     */
    public void setFormSecondInspectionDate(java.util.Date formSecondInspectionDate) {
        this.formSecondInspectionDate = formSecondInspectionDate;
    }

    /**
     * @return the formSecondInspectionPass
     */
    public boolean isFormSecondInspectionPass() {
        return formSecondInspectionPass;
    }

    /**
     * @param formSecondInspectionPass the formSecondInspectionPass to set
     */
    public void setFormSecondInspectionPass(boolean formSecondInspectionPass) {
        this.formSecondInspectionPass = formSecondInspectionPass;
    }

    /**
     * @return the formResolved
     */
    public boolean isFormResolved() {
        return formResolved;
    }

    /**
     * @param formResolved the formResolved to set
     */
    public void setFormResolved(boolean formResolved) {
        this.formResolved = formResolved;
    }

    /**
     * @return the formTotalFeePaid
     */
    public boolean isFormTotalFeePaid() {
        return formTotalFeePaid;
    }

    /**
     * @param formTotalFeePaid the formTotalFeePaid to set
     */
    public void setFormTotalFeePaid(boolean formTotalFeePaid) {
        this.formTotalFeePaid = formTotalFeePaid;
    }

    /**
     * @return the formOccupancyInspectionNotes
     */
    public String getFormOccupancyInspectionNotes() {
        return formOccupancyInspectionNotes;
    }

    /**
     * @param formOccupancyInspectionNotes the formOccupancyInspectionNotes to set
     */
    public void setFormOccupancyInspectionNotes(String formOccupancyInspectionNotes) {
        this.formOccupancyInspectionNotes = formOccupancyInspectionNotes;
    }

    /**
     * @return the formPropertyUnitID
     */
    public int getFormPropertyUnitID() {
        return formPropertyUnitID;
    }

    /**
     * @param formPropertyUnitID the formPropertyUnitID to set
     */
    public void setFormPropertyUnitID(int formPropertyUnitID) {
        this.formPropertyUnitID = formPropertyUnitID;
    }
    
}
