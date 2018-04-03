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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//imported when adding @ManagedBean and @ViewScoped
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 *
 * @author Adam Gutonski
 */
//plugging edits found in Overstack
//https://stackoverflow.com/questions/10526857/facesexception-datamodel-must-implement-org-primefaces-model-selectabledatamode#10527008
@ManagedBean
@ViewScoped
public class WeatherTestBB extends BackingBeanUtils implements Serializable {

    private String formCityName;
    //create a List container (Overstack)
    private List<Weather> weatherData;
    private Weather weather;
    private int formTempHi;
    private int formTempLo;
    private double formPrcp;
    private LocalDateTime formDate;
    
    
            
    /*more logic needed here...
    https://stackoverflow.com/questions/10526857/facesexception-datamodel-must-implement-org-primefaces-model-selectabledatamode#10527008        
    public WeatherTestBB() {
        weather = new ArrayList<Weather>();
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
    
}
