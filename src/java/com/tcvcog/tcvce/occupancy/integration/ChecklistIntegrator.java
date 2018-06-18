/*
 * Copyright (C) 2018 Turtle Creek Valley
 * Council of Governments, PA
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
package com.tcvcog.tcvce.occupancy.integration;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.Municipality;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import com.tcvcog.tcvce.occupancy.entities.ChecklistBlueprint;
import com.tcvcog.tcvce.occupancy.entities.ImplementedChecklist;
import com.tcvcog.tcvce.occupancy.entities.InspectedSpace;
import com.tcvcog.tcvce.occupancy.entities.OccInspec;
import com.tcvcog.tcvce.occupancy.entities.Space;
import com.tcvcog.tcvce.occupancy.entities.SpaceType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Eric C. Darsow
 */
public class ChecklistIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of InspectionChecklistIntegrator
     */
    public ChecklistIntegrator() {
    }
    
    public void createChecklistBlueprintListing(ChecklistBlueprint bpMetaData) throws IntegrationException{
        
        String query = "INSERT INTO public.inspectionchecklist(\n" +
                        " checklistid, title, description, muni_municode, active)\n" +
                        " VALUES (DEFAULT, ?, ?, ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, bpMetaData.getTitle());
            stmt.setString(2, bpMetaData.getDescription());
            stmt.setInt(3, bpMetaData.getMuni().getMuniCode());
            stmt.setBoolean(4, bpMetaData.isActive());
            System.out.println("ChecklistIntegrator.createChecklistBlueprintListing "
                    + "| stmt: " + stmt.toString());
            stmt.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Sylvia, the CogBot, is unable to insert Checklist Blueprint listing, sorry!", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void updateChecklistBlueprintMetadata(ChecklistBlueprint blueprint) throws IntegrationException{
        
        String query = "UPDATE public.inspectionchecklist\n" +
                        " SET title=?, description=?, muni_municode=?, active=?\n" +
                        " WHERE checklistid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, blueprint.getTitle());
            stmt.setString(2, blueprint.getDescription());
            stmt.setInt(3, blueprint.getMuni().getMuniCode());
            stmt.setBoolean(4, blueprint.isActive());
            stmt.setInt(5, blueprint.getInspectionChecklistID());
            System.out.println("ChecklistIntegrator.updateChecklistBlueprintListing "
                    + "| stmt: " + stmt.toString());
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Sylvia, the CogBot, is unable to update checklist blueprint metadata, sorry!", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    // maybe don't need this one
    public void insertChecklistBlueprint(ChecklistBlueprint blueprint) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    /**
     * Extracts the spaces and their CodeElements and creates an ImplementedChecklist object
     * for use during an actual inspection of a dwelling unit
     * 
     * @param bp
     * @return the generated ImplementedChecklist in which CodeElements within each Space object
     * can be marked pass/fail with notes, etc.
     */
    public ImplementedChecklist createImplementedChecklist(ChecklistBlueprint bp) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        
    }
    
    
    
    public void insertSpaceMetatData(Space s) throws IntegrationException{
        
        String query = "INSERT INTO public.space(\n" +
                        " spaceid, name, spacetype)\n" +
                        " VALUES (DEFAULT, ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getSpaceType().getSpaceTypeID());
            System.out.println("ChecklistIntegrator.insertSpace | stmt: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void updateSpaceMetatData(Space s) throws IntegrationException{
        
        String query =  "UPDATE public.space\n" +
                        " SET name=?, spacetype=?\n" +
                        " WHERE spaceid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getSpaceType().getSpaceTypeID());
            System.out.println("ChecklistIntegrator.updateSpace | stmt: " + stmt.toString());
            stmt.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public Space getSpaceWithElements(int spaceID) throws IntegrationException{
        
        String query =  "SELECT name, spacetype\n" +
                        "  FROM public.space WHERE spaceid = ?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Space s = new Space();
        
        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, spaceID);
            rs = stmt.executeQuery();

            while(rs.next()){
                s.setSpaceid(spaceID);
                s.setName(rs.getString("name"));
                s.setSpaceType(getSpaceType(rs.getInt("spacetype")));
            }
            
            s = populateSpaceWithCodeElements(s);

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return s;
    }
    
    private Space populateSpaceWithCodeElements(Space s) throws IntegrationException{
        CodeIntegrator ci = getCodeIntegrator();
        String query =  "SELECT spaceelementid, spaceid, codeelement_eleid\n" +
                        " FROM public.spaceelement WHERE spaceid = ?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<CodeElement> eleList = new ArrayList();
        
        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, s.getSpaceid());
            rs = stmt.executeQuery();
            while(rs.next()){
                eleList.add(ci.getCodeElement(rs.getInt("codeelement_eleid")));
            }
            
            s.setElementList(eleList);

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        return s;
    }
    
    public void attachCodeElementsToSpace(Space s, ArrayList<CodeElement> elementsToAttach) throws IntegrationException{
        
        String query =  "INSERT INTO public.spaceelement(\n" +
                        " spaceelementid, spaceid, codeelement_eleid)\n" +
                        " VALUES (DEFAULT, ?, ?);";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ListIterator li = elementsToAttach.listIterator();
        CodeElement ce;
        try {
            // for each CodeElement in the list passed into the method, make an entry in the spaceelement table
            while(li.hasNext()){
                ce = (CodeElement) li.next();
                stmt = con.prepareStatement(query);
                stmt.setInt(1, s.getSpaceid());
                stmt.setInt(2, ce.getElementID());
                System.out.println("ChecklistIntegrator.attachCodeElementsToSpace | stmt: " + stmt.toString());
                stmt.execute();
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
    }
    
    public void detachCodeElementFromSpace(Space s, CodeElement elementToDetach) throws IntegrationException{
        
        String query = "DELETE FROM public.spaceelement\n" +
                        " WHERE spaceid = ? AND spaceelementid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, s.getSpaceid());
            stmt.setInt(2, elementToDetach.getElementID());
            System.out.println("ChecklistIntegrator.dettachCodeElementsFromSpace | stmt: " + stmt.toString());
            stmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    
    /**
     * Generates a complete list of spaces. We'll probably want to segment these
     * my municipality as the system develops so that the list length stays
     * manageable.
     * 
     * @return a fully-baked space list, meaning each space has its elements intact
     * @throws IntegrationException 
     */
    public ArrayList<Space> getSpaceList() throws IntegrationException{
        
        String query = "SELECT spaceid FROM public.space;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<Space> spaceAL = new ArrayList();

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){
                spaceAL.add(getSpaceWithElements(rs.getInt("spaceid")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        return spaceAL;
        
    }
    
    /**
     * Deletes both the linked spaceelements and the space entry itself
     * @param s
     * @throws IntegrationException 
     */
    public void deleteSpace(Space s) throws IntegrationException{
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            // start by removing space elements
            String query =  "DELETE FROM public.spaceelement\n" +
                        " WHERE spaceelementid = ?;";

            stmt = con.prepareStatement(query);
            stmt.setInt(1, s.getSpaceid());
            stmt.executeQuery();
            
            // now remove the space itself
            query = "DELETE FROM public.space WHERE spaceid = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, s.getSpaceid());
            stmt.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to delete the space and its associated"
                    + "elements: Probably because this space has been used in one or more"
                    + "occupancy inspectsion. It's here to stay!", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }    
    
    
    public ChecklistBlueprint getChecklistBlueprint(int blueprintID) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        return generateChecklistBlueprint(rs);
        
    }
    
    private ChecklistBlueprint generateChecklistBlueprint(ResultSet rs){
        ChecklistBlueprint blueprint = new ChecklistBlueprint();
        return blueprint;
    }
    
    public ArrayList<ChecklistBlueprint> getChecklistBlueprints(Municipality muni) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        return new ArrayList<>();
    }
    
    public void updateChecklistBlueprintSpaceList(ChecklistBlueprint blueprint) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    public void deleteChecklistBlueprint(ChecklistBlueprint blueprint) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    public ImplementedChecklist getImplementedChecklist(int implementedChecklistID) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        
        
    }
    
    private ArrayList<InspectedSpace> getInspectedSpaceList(ImplementedChecklist impChecklist) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    public void insertInspectedSpace(OccInspec oi, InspectedSpace is) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    public void updateInspectedSpace(OccInspec oi, InspectedSpace is) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

    }
    
    public void deleteInspectedSpace(InspectedSpace is) throws IntegrationException{
        
        String query = "";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
    }
    
    public SpaceType getSpaceType(int spacetypeid) throws IntegrationException{
        String query =  "SELECT spacetypeid, spacetitle, description\n" +
                        " FROM public.spacetype WHERE spacetypeid = ?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        SpaceType st = null;

        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, spacetypeid);
            rs = stmt.executeQuery();

            while(rs.next()){
                st = generateSpaceType(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return st;
        
    }
    
    public ArrayList<SpaceType> getSpaceTypeList() throws IntegrationException{

        String query = "SELECT spacetypeid, spacetitle, description" +
                "  FROM public.spacetype;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<SpaceType> spaceTypeList = new ArrayList();

        try {

            stmt = con.prepareStatement(query);
            //stmt.setInt(1, catID);
            rs = stmt.executeQuery();
            System.out.println("SpaceTypeIntegrator.getSpaceTypeList| SQL: " + stmt.toString());

            while(rs.next()){
                //inspectableCodeElementList.add(getInspectionGuidelines(rs.getString("inspection_guidelines")));
                /* see project notes, I will have to figure out how to align the CodeElement
                property of the InspectableCodeElement to have it fit in to the rs (ResultSet)
                and get returned based on foreign key alignment between inspectablecodeleement
                and codeelements...
                */
                spaceTypeList.add(generateSpaceType(rs));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot get space type", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally

        return spaceTypeList;
    }
    
    private SpaceType generateSpaceType(ResultSet rs) throws IntegrationException{
        SpaceType newSpaceType = new SpaceType();


        try {
            newSpaceType.setSpaceTypeID(rs.getInt("spacetypeid"));
            newSpaceType.setSpaceTypeTitle(rs.getString("spacetitle"));
            newSpaceType.setSpaceTypeDescription(rs.getString("description"));
            //newIce.setHighImportance(rs.getBoolean("high_importance"));
            //newIce.setInspectableCodeElementId(rs.getInt("inspectablecodeelementid"));
            //java.sql.Timestamp s = rs.getTimestamp("date");
            //if(s != null){
            //    newIce.setIceDate(s.toLocalDateTime());
            //} else {
            //    newIce.setIceDate(null);
            //}
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating space type from ResultSet", ex);
        }
         return newSpaceType;   

    }
    
    public void insertSpaceType(SpaceType spaceType) throws IntegrationException{
        String query = "INSERT INTO public.spacetype(\n" +
                "         spacetypeid, spacetitle, description) \n" +
                "    VALUES (DEFAULT, ?, ?)";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, inspectableCodeElement.getInspectableCodeElementId());
            //stmt.setInt(1, spaceType.getSpaceTypeID());
            stmt.setString(1, spaceType.getSpaceTypeTitle());
            stmt.setString(2, spaceType.getSpaceTypeDescription());
            /*Previously had date as an attribute of ICE entity... 
            note that the timestamp is set by a call to postgres's now()
            //stmt.setInt(4, exercise.getLift_id());
            if(inspectableCodeElement.getIceDate() != null){
                //keep in  mind that we note place holder "4"' because it  points to the
                //fourth "?" symbol in our query...skips DEFAULT 
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(inspectableCodeElement.getIceDate()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            */
            
            System.out.println("SpaceTypeIntegrator.insertSpaceType| sql: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert SpaceType", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
      public void deleteSpaceType(SpaceType spaceType) throws IntegrationException {
         String query = "DELETE FROM public.spacetype\n" +
                        " WHERE spacetypeid= ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, spaceType.getSpaceTypeID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete space type--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
      
    public void updateSpaceType(SpaceType spaceType) throws IntegrationException {
        String query = "UPDATE public.spacetype\n" +
                    "   SET spacetitle=?, description=?\n" +
                    " WHERE spacetypeid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setString(1, spaceType.getSpaceTypeTitle());
            stmt.setString(2, spaceType.getSpaceTypeDescription());
            stmt.setInt(3, spaceType.getSpaceTypeID());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update space type", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
    }
}
