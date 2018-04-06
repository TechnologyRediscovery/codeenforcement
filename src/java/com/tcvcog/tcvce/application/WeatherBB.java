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
import com.tcvcog.tcvce.entities.Weather;
import com.tcvcog.tcvce.integration.WeatherIntegrator;
import java.io.Serializable;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.*;
//imported when adding @ManagedBean and @ViewScoped
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.*;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Adam Gutonski
 */
//plugging edits found in Overstack
//https://stackoverflow.com/questions/10526857/facesexception-datamodel-must-implement-org-primefaces-model-selectabledatamode#10527008
@ManagedBean
@ViewScoped
public class WeatherBB extends BackingBeanUtils implements Serializable {

    private String formCityName;
    //create a List container (Overstack)
    private LinkedList<Weather> weatherList;
    private int formTempHi;
    private int formTempLo;
    private double formPrcp;
    private LocalDateTime formDate;
    
    
            
            
    public WeatherBB() {
    }
    
    /*creating action listener method to test knowledge based on pg 75 of JSF in Actino
    public void sendMessage(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        getFacesContext().getViewRoot().findComponent("weatherBB.sendMessage");
        
        
    }
    */
    
    /**
     * @return the formCityName
     */
    public String insertCity(){
        WeatherIntegrator wi = new WeatherIntegrator();
        Weather weather = new Weather();
        weather.setName(formCityName);
        try {
            wi.insertWeather(weather);
        } catch (IntegrationException ex) {
            Logger.getLogger(WeatherBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String getFormCityName() {
        return formCityName;
    }

    /**
     * @param formCityName the formCityName to set
     */
    public void setFormCityName(String formCityName) {
        this.formCityName = formCityName;
    }
    
    //attempting to create method in WeatherBB for inserting TempHI values
    //basing method construct off of insertCity() method above
    public int insertTempHi(){
        WeatherIntegrator wi = new WeatherIntegrator();
        Weather weather = new Weather();
        weather.setTempHi(formTempHi);
        try {
            wi.insertWeather(weather);
        } catch (IntegrationException ex) {
            Logger.getLogger(WeatherBB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formTempHi;
    }
    
    //trying to wrap head around actionevents and actionlisteners in context of JSF...
    //left off here
    public void tempHiAction(ActionEvent event){
    //Get submit button id
        int i;
        formCityName = event.getComponent().getClientId();
  }

    /**
     * @return the formTempHi
     */
    public int getFormTempHi() {
        return formTempHi;
    }

    /**
     * @param formTempHi the formTempHi to set
     */
    public void setFormTempHi(int formTempHi) {
        this.formTempHi = formTempHi;
    }

    /**
     * @return the formTempLo
     */
    public int getFormTempLo() {
        return formTempLo;
    }

    /**
     * @param formTempLo the formTempLo to set
     */
    public void setFormTempLo(int formTempLo) {
        this.formTempLo = formTempLo;
    }

    /**
     * @return the formPrcp
     */
    public double getFormPrcp() {
        return formPrcp;
    }

    /**
     * @param formPrcp the formPrcp to set
     */
    public void setFormPrcp(double formPrcp) {
        this.formPrcp = formPrcp;
    }

    /**
     * @return the formDate
     */
    public LocalDateTime getFormDate() {
        return formDate;
    }

    /**
     * @param formDate the formDate to set
     */
    public void setFormDate(LocalDateTime formDate) {
        this.formDate = formDate;
    }
    
    public void setWeatherList(LinkedList<Weather> weatherList) {
        this.weatherList = weatherList;
    }
      
    public LinkedList<Weather> getWeatherList() {
        return weatherList;
    }  
    
    
}
