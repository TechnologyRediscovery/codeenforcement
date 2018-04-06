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
import com.tcvcog.tcvce.entities.Exercise;

import com.tcvcog.tcvce.integration.ExerciseIntegrator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.*;
//imported when adding @ManagedBean and @ViewScoped
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.*;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Adam Gutonski
 */
@ManagedBean
@ViewScoped
public class ExerciseBB extends BackingBeanUtils implements Serializable{
    
    private String formExerciseName;
    private Exercise selectedExercise;
    private int formLiftId;
    private double formWeight;
    private int formMaxReps;
    private java.util.Date formExerciseDate;
    private LinkedList<Exercise> formExerciseList;
    
    /**
     * Creates a new instance of ExerciseBB
     */
    public ExerciseBB() {
    }
    
    public String addExercise(){
        Exercise e = new Exercise();
        ExerciseIntegrator ei = getExerciseIntegrator();
        
        e.setExerciseName(getFormExerciseName());
        e.setWeight(getFormWeight());
        e.setMaxReps(getFormMaxReps());
        //work around for adding a date 
        e.setExerciseDate(formExerciseDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        
        
        
        // placeholder for lastupdated
        
        
        try {
            ei.insertExercise(e);
            getFacesContext().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_INFO, 
                         "Successfully added " + e.getExerciseName() + " to the Database!", ""));

        } catch (IntegrationException ex) {
            System.out.println(ex.toString());
               getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to add exercise to the database, my apologies!", "Make sure your phone numbers have no separators"));
            return "";
        }
        
        return "exerciseManage";
    }

    /**
     * @return the formExerciseName
     */
    public String insertExerciseName(){
        ExerciseIntegrator ei = new ExerciseIntegrator();
        Exercise exercise = new Exercise();
        exercise.setExerciseName(getFormExerciseName());
        try {
            ei.insertExercise(exercise);
        } catch (IntegrationException ex) {
            Logger.getLogger(ExerciseBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public LinkedList<Exercise> getFormExerciseList() {
        try {
            ExerciseIntegrator ec = getExerciseIntegrator();
            formExerciseList = ec.getExerciseList();
            //return eventCategoryList;
        } catch (IntegrationException ex) {
             getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Unable to load exercise list", 
                        "This must be corrected by the System Administrator"));
        }
        if(formExerciseList != null){
            return formExerciseList;
            
        } else {
            formExerciseList = new LinkedList();
            return formExerciseList;
        }
        
    }
    
    public String getFormExerciseName() {
        return formExerciseName;
    }

    /**
     * @param formExerciseName the formExerciseName to set
     */
    public void setFormExerciseName(String formExerciseName) {
        this.formExerciseName = formExerciseName;
    }

    /**
     * @return the formWeight
     */
    public double getFormWeight() {
        return formWeight;
    }

    /**
     * @param formWeight the formWeight to set
     */
    public void setFormWeight(double formWeight) {
        this.formWeight = formWeight;
    }

    /**
     * @return the maxReps
     */
    public int getFormMaxReps() {
        return formMaxReps;
    }

    /**
     * @param maxReps the maxReps to set
     */
    public void setFormMaxReps(int formMaxReps) {
        this.formMaxReps = formMaxReps;
    }

    /**
     * @return the formExerciseDate
     */
    public java.util.Date getFormExerciseDate() {
        return formExerciseDate;
    }

    /**
     * @param formExerciseDate the formExerciseDate to set
     */
    public void setFormExerciseDate(java.util.Date formExerciseDate) {
        this.formExerciseDate = formExerciseDate;
    }

    /**
     * @return the selectedExercise
     */
    public Exercise getSelectedExercise() {
        return selectedExercise;
    }

    /**
     * @param selectedExercise the selectedExercise to set
     */
    public void setSelectedExercise(Exercise selectedExercise) {
        this.selectedExercise = selectedExercise;
    }

  

    /**
     * @param exerciseList the exerciseList to set
     */
    public void setFormExerciseList(LinkedList<Exercise> exerciseList) {
        this.formExerciseList = exerciseList;
    }

    /**
     * @return the formLiftId
     */
    public int getFormLiftId() {
        return formLiftId;
    }

    /**
     * @param formLiftId the formLiftId to set
     */
    public void setFormLiftId(int formLiftId) {
        this.formLiftId = formLiftId;
    }
    
}
