/*
 * Copyright (C) 2017 cedba
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

import com.tcvcog.tcvce.coordinators.CodeCoordinator;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import java.sql.Connection;
import com.tcvcog.tcvce.integration.PostgresConnectionFactory;
import com.tcvcog.tcvce.coordinators.UserCoordinator;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.MunicipalityIntegrator;
import com.tcvcog.tcvce.integration.PersonIntegrator;
import com.tcvcog.tcvce.integration.PropertyIntegrator;
import com.tcvcog.tcvce.integration.UserIntegrator;
import java.util.Date;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author cedba
 */
//@ApplicationScoped
@ManagedBean
@SessionScoped
public class BackingBeanUtils implements Serializable{
    
    //@ManagedProperty(value="#{visit}")
    private Visit visit;
    private UserCoordinator userCoordinator;
    private MunicipalityIntegrator municipalityIntegrator;
    
    @ManagedProperty("#{sessionManager}")
    private SessionManager sessionManager;
    
    private PropertyIntegrator propertyIntegrator;
    private CEActionRequestIntegrator cEActionRequestIntegrator;
    private UserIntegrator userIntegrator;
 
    // private Connection postgresCon;
    
    /**
     * Creates a new instance of BackingBeanUtils
     */
    public BackingBeanUtils() {
        
        // this creation of the usercorodinator should be managed by the 
        // MBCF but doesn't seem to be--this is not a solid object-oriented
        // design concept that works well with the bean model
        // it should be made by the MBCF
        //System.out.println("Constructing BackingBean Utils");
        //userCoordinator = new UserCoordinator();
        
       
        
    }
    
    public static java.sql.Timestamp getCurrentTimeStamp(){
        java.util.Date date = new java.util.Date();
        return new java.sql.Timestamp(date.getTime());
        
    }
    
    public FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }
    
    public Application getApplication(){
        return getFacesContext().getApplication();
    }
    
    public Visit getVisit(){
        System.out.println("BackingBeanUtils.getVisit | gotten visit: " + visit.getEceList().peek().getOrdsecTitle());
        return visit;
    }
    
    public void setVisit(Visit visit){
        this.visit = visit;
    }
    
    public UserCoordinator getUserCorodinator(){
        System.out.println("Inside call to getUserCoordinator of class BackingBeanUtils");
        // this is a debugging object creation -- troubleshoot where to actually construct this
        UserCoordinator user = new UserCoordinator();
        return user;
    }
    
    public void setUserCoordinator(UserCoordinator userCoordinator){
        this.userCoordinator = userCoordinator;
    }

    /**
     * creates a PostgresConnectionFactory factory and calls getCon to get a handle on the
 database connection
     * @return the postgresCon
     */
    public Connection getPostgresCon() {

        // We definitely do not want to be creating a connection 
        // factory in this location-- go get a bean!
       // PostgresConnectionFactory factory = new PostgresConnectionFactory();
       // return factory.getCon();
       
             
         //System.out.println("BackingBeanUtils.getPostgresCon- Getting con through backing bean");
         FacesContext context = getFacesContext();
         PostgresConnectionFactory dbCon = context.getApplication()
                 .evaluateExpressionGet(
                         context, 
                         "#{dBConnection}", 
                         PostgresConnectionFactory.class);
         return dbCon.getCon();
         

    }

    // deleted setter
    
    public int getControlCodeFromTime(){
         long dateInMs = new Date().getTime();
         
         String numAsString = String.valueOf(dateInMs);
         String reducedNum = numAsString.substring(7);
         int controlCode = Integer.parseInt(reducedNum);
         
            
         return controlCode;
    }
    
    public CodeCoordinator getCodeCoordinator() {
        FacesContext context = getFacesContext();
        CodeCoordinator coord = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{codeCoordinator}", 
                        CodeCoordinator.class);
    return coord;
        
    }
    
    public CodeIntegrator getCodeIntegrator(){
        FacesContext context = getFacesContext();
        CodeIntegrator codeInt = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{codeIntegrator}", 
                        CodeIntegrator.class);
        
        return codeInt;
        
    }
    
    public PersonIntegrator getPersonIntegrator(){
        FacesContext context = getFacesContext();
        PersonIntegrator personIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{personIntegrator}", 
                        PersonIntegrator.class);
        
        return personIntegrator;
        
    }
    
    public MunicipalityIntegrator getMunicipalityIntegrator(){
        FacesContext context = getFacesContext();
        municipalityIntegrator = context. getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{municipalityIntegrator}", 
                        MunicipalityIntegrator.class);
        return municipalityIntegrator;
    }
    
    

    /**
     * @return the userCoordinator
     */
    public UserCoordinator getUserCoordinator() {
        return userCoordinator;
    }



    /**
     * @param muniIntegrator the muniIntegrator to set
     */
    public void setMunicipalityIntegrator(MunicipalityIntegrator muniIntegrator) {
        this.municipalityIntegrator = muniIntegrator;
    }

    /**
     * @return the sessionManager
     */
    public SessionManager getSessionManager() {
//        FacesContext context = getFacesContext();
//        ValueExpression ve = context.getApplication().getExpressionFactory()
//                .createValueExpression(context.getELContext(), 
//                        "#{sessionManager}", SessionManager.class);
//        sessionManager = (SessionManager) ve.getValue(context.getELContext());
   
        return sessionManager;
    }

    /**
     * @param sessionManager the sessionManager to set
     */
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * @return the propertyIntegrator
     */
    public PropertyIntegrator getPropertyIntegrator() {
        
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), 
                        "#{propertyIntegrator}", PropertyIntegrator.class);
        propertyIntegrator = (PropertyIntegrator) ve.getValue(context.getELContext());
   
        return propertyIntegrator;
    }

    /**
     * @param propertyIntegrator the propertyIntegrator to set
     */
    public void setPropertyIntegrator(PropertyIntegrator propertyIntegrator) {
        this.propertyIntegrator = propertyIntegrator;
    }

    /**
     * @return the cEActionRequestIntegrator
     */
    public CEActionRequestIntegrator getcEActionRequestIntegrator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), 
                        "#{cEActionRequestIntegrator}", CEActionRequestIntegrator.class);
        cEActionRequestIntegrator = (CEActionRequestIntegrator) ve.getValue(context.getELContext());
        return cEActionRequestIntegrator;
    }

    /**
     * @param cEActionRequestIntegrator the cEActionRequestIntegrator to set
     */
    public void setcEActionRequestIntegrator(CEActionRequestIntegrator cEActionRequestIntegrator) {
        this.cEActionRequestIntegrator = cEActionRequestIntegrator;
    }

    /**
     * @return the userIntegrator
     */
    public UserIntegrator getUserIntegrator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{userIntegrator}", UserIntegrator.class);
        userIntegrator = (UserIntegrator) ve.getValue(context.getELContext());
        
        
        return userIntegrator;
    }

    /**
     * @param userIntegrator the userIntegrator to set
     */
    public void setUserIntegrator(UserIntegrator userIntegrator) {
        this.userIntegrator = userIntegrator;
    }

}