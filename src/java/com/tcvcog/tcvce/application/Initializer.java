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
import com.tcvcog.tcvce.coordinators.UserCoordinator;
import com.tcvcog.tcvce.coordinators.ViolationCoordinator;
import com.tcvcog.tcvce.integration.CEActionRequestIntegrator;
import com.tcvcog.tcvce.integration.CaseIntegrator;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.integration.CodeViolationIntegrator;
import com.tcvcog.tcvce.integration.EventIntegrator;
import com.tcvcog.tcvce.integration.ExerciseIntegrator;
import com.tcvcog.tcvce.integration.InspectableCodeElementIntegrator;
import com.tcvcog.tcvce.integration.MunicipalityIntegrator;
import com.tcvcog.tcvce.integration.OccupancyInspectionIntegrator;
import com.tcvcog.tcvce.integration.PersonIntegrator;
import com.tcvcog.tcvce.util.Constants;
import com.tcvcog.tcvce.integration.PostgresConnectionFactory;
import com.tcvcog.tcvce.integration.PropertyIntegrator;
import com.tcvcog.tcvce.integration.SpaceTypeIntegrator;
import com.tcvcog.tcvce.integration.UserIntegrator;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener; 

import javax.servlet.annotation.WebListener;



/**
 *
 * @author cedba
 */
@WebListener
public class Initializer implements ServletContextListener{

    /**
     * Creates a new instance of Initializer
     */
    public Initializer() {
        System.out.println("Creating Initializer Bean");
        
    
    }
    
   @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Intilizer.contextInitialized -- start");
        
        ServletContext servletContext = event.getServletContext();
        UserCoordinator userCoordinator = new UserCoordinator();
        
        System.out.println("Intilizer.contextInitialized -- creating DB Connection");
        PostgresConnectionFactory con = new PostgresConnectionFactory();
        servletContext.setAttribute("dBConnection", con);
        // this setAttribute system is not working as planned.
        
        //servletContext.setAttribute(Constants.USER_COORDINATOR_SCOPE, userCoordinator);
        servletContext.setAttribute(Constants.USER_COORDINATOR_KEY, userCoordinator);
        
        
        CodeCoordinator codeCoordinator = new CodeCoordinator();
        servletContext.setAttribute("codeCoordinator", codeCoordinator);
        
        UserCoordinator uc = new UserCoordinator();
        servletContext.setAttribute("userCoordinator", uc);
        
        CaseCoordinator cc = new CaseCoordinator();
        servletContext.setAttribute("caseCoordinator", cc);
        
        EventCoordinator ec = new EventCoordinator();
        servletContext.setAttribute("eventCoordinator", ec);
        
        //adding integrator build for exercise
        ExerciseIntegrator exerciseIntegrator = new ExerciseIntegrator();
        servletContext.setAttribute("exerciseIntegrator", exerciseIntegrator);
        
        //adding integrator for occupancy inspection
        OccupancyInspectionIntegrator occupancyInspectionIntegrator = new OccupancyInspectionIntegrator();
        servletContext.setAttribute("occupancyInspectionIntegrator", occupancyInspectionIntegrator);
        
        //add integrator build for inspectable code element
        InspectableCodeElementIntegrator inspectableCEIntegrator = new InspectableCodeElementIntegrator();
        servletContext.setAttribute("inspectableCodeElementIntegrator", inspectableCEIntegrator);
       
        
        CodeIntegrator codeIntegrator = new CodeIntegrator();
        servletContext.setAttribute("codeIntegrator", codeIntegrator);
        
        MunicipalityIntegrator munigrator = new MunicipalityIntegrator();
        servletContext.setAttribute("municipalitygrator", munigrator);
        
        PersonIntegrator personIntegrator = new PersonIntegrator();
        servletContext.setAttribute("personIntegrator", personIntegrator);
        
        PropertyIntegrator pi = new PropertyIntegrator();
        servletContext.setAttribute("propertyIntegrator", pi);
        
        CEActionRequestIntegrator ceActionRI = new CEActionRequestIntegrator();
        servletContext.setAttribute("cEActionRequestIntegrator", ceActionRI);
        
        UserIntegrator ui = new UserIntegrator();
        servletContext.setAttribute("userIntegrator", ui);
        
        CaseIntegrator ci = new CaseIntegrator();
        servletContext.setAttribute("caseIntegrator", ci);
        
        EventIntegrator ei = new EventIntegrator();
        servletContext.setAttribute("eventIntegrator", ei);
        
        SpaceTypeIntegrator spaceTypeIntegrator = new SpaceTypeIntegrator();
        servletContext.setAttribute("spaceTypeIntegrator", spaceTypeIntegrator);
        
        CEActionRequestIntegrator ari = new CEActionRequestIntegrator();
        servletContext.setAttribute("ceActionRequestIntegrator", ari);
        
        CodeViolationIntegrator cvi = new CodeViolationIntegrator();
        servletContext.setAttribute("codeViolationIntegrator", cvi);
        
        ViolationCoordinator vc = new ViolationCoordinator();
        servletContext.setAttribute("violationCoordinator", vc);
        
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event){
        
    }
    
    
    
}
