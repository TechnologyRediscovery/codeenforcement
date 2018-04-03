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
public class Weather {
    //create private class vars.
    private String cityName;
    private LocalDateTime date;
    private String notes;
    
    private int observation_id;
    private int tempHi;
    private int tempLo;
    private double prcp;
    

    /**
     *CTRL + SHIFT + ALT + E encapsulates and
     * creates getter/setters
     * @return 
     */
    public int getObservation_id() {
        return observation_id;
    }

    /**
     * @param observation_id
     */
    public void setObservation_id(int observation_id) {
        this.observation_id = observation_id;
    }
    
    
    public String getName() {
        return cityName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.cityName = name;
    }

    /**
     * @return the tempHi
     */
    public int getTempHi() {
        return tempHi;
    }

    /**
     * @param tempHi the tempHi to set
     */
    public void setTempHi(int tempHi) {
        this.tempHi = tempHi;
    }

    /**
     * @return the tempLo
     */
    public int getTempLo() {
        return tempLo;
    }

    /**
     * @param tempLo the tempLo to set
     */
    public void setTempLo(int tempLo) {
        this.tempLo = tempLo;
    }

    /**
     * @return the prcp
     */
    public double getPrcp() {
        return prcp;
    }

    /**
     * @param prcp the prcp to set
     */
    public void setPrcp(double prcp) {
        this.prcp = prcp;
    }

    /**
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
 
    
}
