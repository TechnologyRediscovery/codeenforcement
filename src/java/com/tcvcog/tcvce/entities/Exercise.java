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
package com.tcvcog.tcvce.entities;
import java.time.LocalDateTime;

/**
 *
 * @author Adam Gutonski
 */
public class Exercise {
    private String exerciseName;
    private double weight;
    private int maxReps;
    private int lift_id;
    private LocalDateTime exerciseDate;

    /**
     * @return the exerciseName
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * @param exerciseName the exerciseName to set
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the maxReps
     */
    public int getMaxReps() {
        return maxReps;
    }

    /**
     * @param maxReps the maxReps to set
     */
    public void setMaxReps(int maxReps) {
        this.maxReps = maxReps;
    }

    /**
     * @return the lift_id
     */
    public int getLift_id() {
        return lift_id;
    }

    /**
     * @param lift_id the lift_id to set
     */
    public void setLift_id(int lift_id) {
        this.lift_id = lift_id;
    }

    /**
     * @return the exerciseDate
     */
    public LocalDateTime getExerciseDate() {
        return exerciseDate;
    }

    /**
     * @param exerciseDate the exerciseDate to set
     */
    public void setExerciseDate(LocalDateTime exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
    
}
