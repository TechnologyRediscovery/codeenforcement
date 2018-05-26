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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Citation;
import com.tcvcog.tcvce.entities.CitationStatus;
import com.tcvcog.tcvce.entities.Property;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sylvia
 */
public class CitationIntegratorIT {
    
    public CitationIntegratorIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("Inside Setup!");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertCitation method, of class CitationIntegrator.
     */
    @Test
    public void testInsertCitation() throws Exception {
        System.out.println("insertCitation");
        Citation citation = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.insertCitation(citation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
    }

    /**
     * Test of getCitationByID method, of class CitationIntegrator.
     */
    @Test
    public void testGetCitationByID() throws Exception {
        System.out.println("getCitationByID");
        int id = 0;
        CitationIntegrator instance = new CitationIntegrator();
        Citation expResult = null;
        Citation result = instance.getCitationByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCitationsByProperty method, of class CitationIntegrator.
     */
    @Test
    public void testGetCitationsByProperty() throws Exception {
        System.out.println("getCitationsByProperty");
        Property prop = null;
        CitationIntegrator instance = new CitationIntegrator();
        LinkedList<Citation> expResult = null;
        LinkedList<Citation> result = instance.getCitationsByProperty(prop);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCitationsByCase method, of class CitationIntegrator.
     */
    @Test
    public void testGetCitationsByCase() throws Exception {
        System.out.println("getCitationsByCase");
        CECase ceCase = null;
        CitationIntegrator instance = new CitationIntegrator();
        LinkedList<Citation> expResult = null;
        ArrayList<Citation> result = instance.getCitationsByCase(ceCase);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateCitation method, of class CitationIntegrator.
     */
    @Test
    public void testUpdateCitation() throws Exception {
        System.out.println("updateCitation");
        Citation citation = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.updateCitation(citation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCitation method, of class CitationIntegrator.
     */
    @Test
    public void testDeleteCitation() throws Exception {
        System.out.println("deleteCitation");
        Citation citation = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.deleteCitation(citation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCitationStatus method, of class CitationIntegrator.
     */
    @Test
    public void testGetCitationStatus() throws Exception {
        System.out.println("getCitationStatus");
        int statusID = 0;
        CitationIntegrator instance = new CitationIntegrator();
        CitationStatus expResult = null;
        CitationStatus result = instance.getCitationStatus(statusID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFullCitationStatusList method, of class CitationIntegrator.
     */
    @Test
    public void testGetFullCitationStatusList() throws Exception {
        System.out.println("getFullCitationStatusList");
        CitationIntegrator instance = new CitationIntegrator();
        LinkedList<CitationStatus> expResult = null;
        LinkedList<CitationStatus> result = instance.getFullCitationStatusList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCitationStatus method, of class CitationIntegrator.
     */
    @Test
    public void testGenerateCitationStatus() throws Exception {
        System.out.println("generateCitationStatus");
        ResultSet rs = null;
        CitationIntegrator instance = new CitationIntegrator();
        CitationStatus expResult = null;
        CitationStatus result = instance.generateCitationStatus(rs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertCitationStatus method, of class CitationIntegrator.
     */
    @Test
    public void testInsertCitationStatus() throws Exception {
        System.out.println("insertCitationStatus");
        CitationStatus cs = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.insertCitationStatus(cs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCitationStatus method, of class CitationIntegrator.
     */
    @Test
    public void testDeleteCitationStatus() throws Exception {
        System.out.println("deleteCitationStatus");
        CitationStatus cs = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.deleteCitationStatus(cs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateCitationStatus method, of class CitationIntegrator.
     */
    @Test
    public void testUpdateCitationStatus() throws Exception {
        System.out.println("updateCitationStatus");
        CitationStatus cs = null;
        CitationIntegrator instance = new CitationIntegrator();
        instance.updateCitationStatus(cs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
