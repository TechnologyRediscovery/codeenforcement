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

import com.tcvcog.tcvce.coordinators.CaseCoordinator;
import com.tcvcog.tcvce.coordinators.CodeCoordinator;
import com.tcvcog.tcvce.coordinators.EventCoordinator;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import java.sql.Connection;
import com.tcvcog.tcvce.integration.PostgresConnectionFactory;
import com.tcvcog.tcvce.coordinators.UserCoordinator;
import com.tcvcog.tcvce.coordinators.ViolationCoordinator;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import com.tcvcog.tcvce.integration.CitationIntegrator;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.CodeViolationIntegrator;
import com.tcvcog.tcvce.integration.CourtEntityIntegrator;
import com.tcvcog.tcvce.integration.EventIntegrator;
import com.tcvcog.tcvce.integration.ExerciseIntegrator;
import com.tcvcog.tcvce.integration.InspectableCodeElementIntegrator;
import com.tcvcog.tcvce.integration.MunicipalityIntegrator;
import com.tcvcog.tcvce.integration.OccupancyInspectionFeeIntegrator;
import com.tcvcog.tcvce.integration.OccupancyInspectionIntegrator;
import com.tcvcog.tcvce.integration.OccupancyPermitTypeIntegrator;
import com.tcvcog.tcvce.integration.PaymentIntegrator;
import com.tcvcog.tcvce.integration.PaymentTypeIntegrator;
import com.tcvcog.tcvce.integration.PersonIntegrator;
import com.tcvcog.tcvce.integration.PropertyIntegrator;
import com.tcvcog.tcvce.integration.SpaceTypeIntegrator;
import com.tcvcog.tcvce.integration.UserIntegrator;
import com.tcvcog.tcvce.integration.WeatherTestIntegrator;
import com.tcvcog.tcvce.occupancy.InspectableCodeElement;
import com.tcvcog.tcvce.util.Constants;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
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
    
    private CaseCoordinator caseCoordinator;
    private CaseIntegrator caseIntegrator;
    
    //testing weather test integrator
    private WeatherTestIntegrator weatherTestIntegrator;
    
    private EventCoordinator eventCoordinator;
    private EventIntegrator eventIntegrator;
    
    private CodeViolationIntegrator codeViolationIntegrator;
    private ViolationCoordinator violationCoordinator;
    
    private MunicipalityIntegrator municipalityIntegrator;
    
//    @ManagedProperty("#{sessionManager}")
    private SessionManager sessionManager;
    
    private PropertyIntegrator propertyIntegrator;
    private CEActionRequestIntegrator cEActionRequestIntegrator;
    
    private UserIntegrator userIntegrator;
    private SpaceTypeIntegrator spaceTypeIntegrator;
    
    private OccupancyPermitTypeIntegrator occupancyPermitTypeIntegrator;
    private OccupancyInspectionFeeIntegrator occupancyInspectionFeeIntegrator;
    
    
    
 
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
    
    public ResourceBundle getResourceBundle(String bundleName){
        FacesContext context = getFacesContext();
        Application app = getApplication();
        ResourceBundle bundle = app.getResourceBundle(context, bundleName );
        return bundle;
    }
    
    public Visit getVisit(){
        return visit;
    }
    
    public void setVisit(Visit visit){
        this.visit = visit;
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
    
    public ExerciseIntegrator getExerciseIntegrator(){
        FacesContext context = getFacesContext();
        ExerciseIntegrator exerciseIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{exerciseIntegrator}", 
                        ExerciseIntegrator.class);
        
        return exerciseIntegrator;
        
    }
    //adams creation of getInspectableCodeElementIntegrator()
     public InspectableCodeElementIntegrator getInspectableCodeElementIntegrator(){
        FacesContext context = getFacesContext();
        InspectableCodeElementIntegrator inspectableCodeElementIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{inspectableCodeElementIntegrator}", 
                        InspectableCodeElementIntegrator.class);
        
        return inspectableCodeElementIntegrator;
        
    }
     
     public SpaceTypeIntegrator getSpaceTypeIntegrator(){
        FacesContext context = getFacesContext();
        SpaceTypeIntegrator spaceTypeIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{spaceTypeIntegrator}", 
                        SpaceTypeIntegrator.class);
        
        return spaceTypeIntegrator;
     }
     
     public OccupancyPermitTypeIntegrator getOccupancyPermitTypeIntegrator(){
         FacesContext context = getFacesContext();
         OccupancyPermitTypeIntegrator occupancyPermitTypeIntegrator = context.getApplication()
            .evaluateExpressionGet(
                    context,
                    "#{occupancyPermitTypeIntegrator}",
                    OccupancyPermitTypeIntegrator.class);
         
         return occupancyPermitTypeIntegrator;
     }
     
     public OccupancyInspectionFeeIntegrator getOccupancyInspectionFeeIntegrator(){
         FacesContext context = getFacesContext();
         OccupancyInspectionFeeIntegrator occupancyInspectionFeeIntegrator = context.getApplication()
            .evaluateExpressionGet(
                    context,
                    "#{occupancyInspectionFeeIntegrator}",
                    OccupancyInspectionFeeIntegrator.class);
         
         return occupancyInspectionFeeIntegrator;
     }
     
     public CitationIntegrator getCitationIntegrator(){
         FacesContext context = getFacesContext();
         CitationIntegrator citationIntegrator = context.getApplication()
            .evaluateExpressionGet(
                    context,
                    "#{citationIntegrator}",
                    CitationIntegrator.class);
         
         return citationIntegrator;
     }
    
    //adam creation of occupancy inspeciton integrator
    public OccupancyInspectionIntegrator getOccupancyInspectionIntegrator(){
        FacesContext context = getFacesContext();
        OccupancyInspectionIntegrator occupancyInspectionIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{occupancyInspectionIntegrator}", 
                        OccupancyInspectionIntegrator.class);
        
        return occupancyInspectionIntegrator;
    }
    
    //adam creation of PaymentTypeIntegrator
    public PaymentTypeIntegrator getPaymentTypeIntegrator(){
        FacesContext context = getFacesContext();
        PaymentTypeIntegrator paymentTypeIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{paymentTypeIntegrator}", 
                        PaymentTypeIntegrator.class);
        
        return paymentTypeIntegrator;
    }
    
    //adam creation of CourtEntityIntegrator
    public CourtEntityIntegrator getCourtEntityIntegrator(){
         FacesContext context = getFacesContext();
         CourtEntityIntegrator courtEntityIntegrator = context.getApplication()
            .evaluateExpressionGet(
                    context,
                    "#{courtEntityIntegrator}",
                    CourtEntityIntegrator.class);
         return courtEntityIntegrator;
     }
    
    //adams creation of a weathertestintegrator in the backingbeanutils
    public WeatherTestIntegrator getWeatherTestIntegrator(){
        FacesContext context = getFacesContext();
        WeatherTestIntegrator weatherIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{personIntegrator}", 
                        WeatherTestIntegrator.class);
        
        return weatherIntegrator;
        
    }
    
    //adams creation of a payment integrator
    public PaymentIntegrator getPaymentIntegrator(){
        FacesContext context = getFacesContext();
        PaymentIntegrator paymentIntegrator = context.getApplication()
                .evaluateExpressionGet(
                        context, 
                        "#{paymentIntegrator}", 
                        PaymentIntegrator.class);
        
        return paymentIntegrator;
        
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
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(),
                "#{userCoordinator}", UserCoordinator.class);
       userCoordinator = (UserCoordinator) ve.getValue(context.getELContext());
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
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), 
                        "#{sessionManager}", SessionManager.class);
        sessionManager = (SessionManager) ve.getValue(context.getELContext());
   
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

    /**
     * @return the caseCoordinator
     */
    public CaseCoordinator getCaseCoordinator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{caseCoordinator}", CaseCoordinator.class);
        caseCoordinator = (CaseCoordinator) ve.getValue(context.getELContext());
        return caseCoordinator;
    }

    /**
     * @param caseCoordinator the caseCoordinator to set
     */
    public void setCaseCoordinator(CaseCoordinator caseCoordinator) {
        this.caseCoordinator = caseCoordinator;
    }

    /**
     * @return the eventCoordinator
     */
    public EventCoordinator getEventCoordinator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{eventCoordinator}", EventCoordinator.class);
        eventCoordinator = (EventCoordinator) ve.getValue(context.getELContext());
        return eventCoordinator;
    }

    /**
     * @param eventCoordinator the eventCoordinator to set
     */
    public void setEventCoordinator(EventCoordinator eventCoordinator) {
        this.eventCoordinator = eventCoordinator;
    }

    /**
     * @return the eventIntegrator
     */
    public EventIntegrator getEventIntegrator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{eventIntegrator}", EventIntegrator.class);
        eventIntegrator = (EventIntegrator) ve.getValue(context.getELContext());
        
        return eventIntegrator;
    }

    /**
     * @param eventIntegrator the eventIntegrator to set
     */
    public void setEventIntegrator(EventIntegrator eventIntegrator) {
        this.eventIntegrator = eventIntegrator;
    }

    /**
     * @return the caseIntegrator
     */
    public CaseIntegrator getCaseIntegrator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{caseIntegrator}", CaseIntegrator.class);
        caseIntegrator = (CaseIntegrator) ve.getValue(context.getELContext());
        
        return caseIntegrator;
        
        
        
        
    }

    /**
     * @param caseIntegrator the caseIntegrator to set
     */
    public void setCaseIntegrator(CaseIntegrator caseIntegrator) {
        this.caseIntegrator = caseIntegrator;
    }
    
    /**
     * Calculates the number of days since a given date and the current system 
     * date.
     * @param pastDate
     * @return the number of calendar days since a given date 
     */
    public long getDaysSince(LocalDateTime pastDate){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Long daysBetween = pastDate.atZone(ZoneId.systemDefault())
                .until(currentDateTime.atZone(ZoneId.systemDefault()), java.time.temporal.ChronoUnit.DAYS);
        return daysBetween;
        
        
    }
    
    public String getPrettyDate(LocalDateTime d){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMM yyyy, HH:mm");
        String formattedDateTime = d.format(formatter); 
        return formattedDateTime;
    }

    /**
     * @return the codeViolationIntegrator
     */
    public CodeViolationIntegrator getCodeViolationIntegrator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{codeViolationIntegrator}", CodeViolationIntegrator.class);
        codeViolationIntegrator = (CodeViolationIntegrator) ve.getValue(context.getELContext());
        
        return codeViolationIntegrator;
    }

    /**
     * @param codeViolationIntegrator the codeViolationIntegrator to set
     */
    public void setCodeViolationIntegrator(CodeViolationIntegrator codeViolationIntegrator) {
        this.codeViolationIntegrator = codeViolationIntegrator;
    }

    /**
     * @return the violationCoordinator
     */
    public ViolationCoordinator getViolationCoordinator() {
        FacesContext context = getFacesContext();
        ValueExpression ve = context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{violationCoordinator}", ViolationCoordinator.class);
        violationCoordinator = (ViolationCoordinator) ve.getValue(context.getELContext());
        return violationCoordinator;
    }

    /**
     * @param violationCoordinator the violationCoordinator to set
     */
    public void setViolationCoordinator(ViolationCoordinator violationCoordinator) {
        this.violationCoordinator = violationCoordinator;
    }

}